<template id="job-step-template">
    <div class="job-step-container" :class="step.undefined ? 'disabled' : step.status">
        <!-- step undefined -->
        <div v-if="step.undefined" class="job-step-header">
            <div class="job-step-header-title">
                <span>{{headerTitle}}</span>
            </div>
        </div>

        <!-- step defined -->
        <div v-if="! step.undefined" class="job-step-header" v-b-toggle="'collapse-' + id">
            <div class="job-step-header-title">
                <span>{{headerTitle}}</span>
                <i v-if="step.status === 'STARTED'" class="fas fa-circle-notch fa-spin"></i>
            </div>
            <i v-if="bodyVisible" class="fas fa-chevron-up"></i>
            <i v-else class="fas fa-chevron-down"></i>
        </div>
        <b-collapse v-if="! step.undefined" v-model="bodyVisible" :id="'collapse-' + id" class="job-step-body">
            <div class="job-step-execution metadata">
                <span class="job-step-status">Status&nbsp;{{step.status}}</span>
                <p>
                    <i class="fas fa-play"></i>
                    Started&nbsp;<b>{{step.startDateTime | dateTime}}</b>
                </p>
                <p v-if="step.endDateTime">
                    <i class="fas fa-stop"></i>
                    Ended&nbsp;<b>{{step.endDateTime | dateTime}}</b>
                </p>
                <p>
                    <i class="fas fa-stopwatch"></i>
                    Duration&nbsp;
                    <b>
                        <job-timer
                                :start-time="step.startDateTime"
                                :end-time="step.endDateTime">
                        </job-timer>
                    </b>
                </p>
            </div>
            <console :id="'logs-' + id" :css-style="'max-height: 300px'" :logs="step.logs"></console>
        </b-collapse>
    </div>
</template>

<script>
    Vue.component('job-step', {
        template: '#job-step-template',
        data: () => ({
            bodyVisible: false,
        }),
        props: {
            id: String,
            headerTitle: String,
            step: {
                default: function () {
                    return {undefined: true};
                }
            }
        },
        watch: {
            step: function (newValue) {
                this.bodyVisible = newValue.status !== 'FINISHED';
            }
        },
        filters: {
            dateTime: function (value) {
                if (!value || !moment(value).isValid()) return '';
                return moment(value).format('LL LTS');
            }
        }
    });
</script>

<style scoped>
    .job-step-container {
        margin: 1rem 0;
    }

    .job-step-container .job-step-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border: 1px solid #eaeaea;
        padding: 1rem 1rem 1rem 2rem;
        font-size: larger;
        font-weight: bolder;
        cursor: pointer;
    }

    .job-step-container .job-step-header .job-step-header-title {
        display: flex;
        align-items: center;
        text-transform: uppercase;
    }

    .job-step-container .job-step-header .job-step-header-title .fas {
        margin-left: 0.5rem;
    }

    .job-step-container .job-step-header .fas {
        font-size: larger;
    }

    .job-step-container .job-step-body {
        border: 1px solid #eaeaea;
        border-top: none;
        padding: 1rem;
    }

    .job-step-container .job-step-body .metadata p {
        font-size: 14px;
    }

    .job-step-container .job-step-body .fas {
        width: 1rem;
        margin-right: 1rem;
        text-align: center;
    }

    .job-step-container .job-step-execution {
        margin-bottom: 1rem;
    }

    .job-step-container .job-step-status {
        font-size: larger;
        margin-bottom: 1rem;
    }

    .job-step-container.disabled .job-step-header {
        background: linear-gradient(to right, #eaeaea 0, #eaeaea 1rem, #fff 1rem, #fff 100%) no-repeat;
        color: #eaeaea;
        cursor: default;
    }

    .job-step-container[class*=STARTED] .job-step-header {
        background: linear-gradient(to right, #2196f3 0, #2196f3 1rem, #fff 1rem, #fff 100%) no-repeat;
        color: #2196f3;
    }

    .job-step-container[class*=FINISHED] .job-step-header {
        background: linear-gradient(to right, #1ed085 0, #1ed085 1rem, #fff 1rem, #fff 100%) no-repeat;
        color: #1ed085;
    }

    .job-step-container[class*=FAILED] .job-step-header {
        background: linear-gradient(to right, #dc3545 0, #dc3545 1rem, #fff 1rem, #fff 100%) no-repeat;
        color: #dc3545;
    }

    .job-step-container[class*=STARTED] .job-step-status {
        color: #2196f3;
    }

    .job-step-container[class*=FINISHED] .job-step-status {
        color: #1ed085;
    }

    .job-step-container[class*=FAILED] .job-step-status {
        color: #dc3545;
    }
</style>