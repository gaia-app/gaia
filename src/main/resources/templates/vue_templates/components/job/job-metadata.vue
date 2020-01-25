<template id="job-metadata-template">
    <div class="job-metadata-container" :class="job.status">
        <div class="job-infos metadata">
            <span class="job-detail-title">Job&nbsp;{{job.id}}</span>
            <p>
                <i class="fas fa-folder"></i>
                Module&nbsp;<a :href="'/modules/' + stack.moduleId + '/description'">{{stack.moduleId}}</a>
            </p>
            <p>
                <i class="fas fa-file"></i>
                Stack&nbsp;<a :href="'/stacks/' + stack.id">{{stack.id}}</a>
            </p>
            <p>
                <i class="fas fa-user"></i>
                <user-badge :user="job.user"></user-badge>
            </p>
        </div>
        <div class="job-execution metadata">
            <span class="job-detail-title">Status&nbsp;{{job.status}}</span>
            <p>
                <i class="fas fa-play"></i>
                Started&nbsp;<b>{{job.startDateTime | dateTimeLong}}</b>
            </p>
            <p v-if="job.endDateTime">
                <i class="fas fa-stop"></i>
                Ended&nbsp;<b>{{job.endDateTime | dateTimeLong}}</b>
            </p>
            <p>
                <i class="fas fa-stopwatch"></i>
                Duration&nbsp;
                <b>
                    <job-timer
                            :start-time="job.startDateTime"
                            :end-time="job.endDateTime">
                    </job-timer>
                </b>
            </p>
        </div>
        <div class="job-actions" v-if="isRetryAvailable">
            <b-button variant="info" size="lg" @click="$emit('retry')"><i class="fas fa-redo"></i> Retry</b-button>
        </div>
    </div>
</template>

<script>
    Vue.component('job-metadata', {
        template: '#job-metadata-template',
        props: ['job', 'stack'],
        computed: {
            isRetryAvailable: function () {
                return this.job.status !== null &&
                    this.job.status.indexOf('FAILED') > 0;
            },
        }
    });
</script>

<style scoped>
    .job-metadata-container {
        display: flex;
        background: linear-gradient(to right, #eaeaea 0, #eaeaea 1rem, #fff 1rem, #fff 100%) no-repeat;
        border: 1px solid #eaeaea;
        padding: 1rem 1rem 1rem 2rem;
    }

    .job-metadata-container .job-infos, .job-metadata-container .job-execution {
        flex: 0 1 45%;
    }

    .job-metadata-container .job-execution, .job-metadata-container .job-actions {
        margin-left: 1rem;
    }

    .job-metadata-container .job-actions {
        display: flex;
        justify-content: flex-end;
        align-items: flex-start;
        flex: 1 1 auto;
    }

    .job-metadata-container .metadata p {
        font-size: 14px;
    }

    .job-metadata-container .fas {
        width: 1rem;
        margin-right: 1rem;
        text-align: center;
    }

    .job-metadata-container .job-detail-title {
        font-size: larger;
        margin-bottom: 1rem;
    }

    .job-metadata-container[class*=STARTED] {
        background: linear-gradient(to right, #2196f3 0, #2196f3 1rem, #fff 1rem, #fff 100%) no-repeat;
    }

    .job-metadata-container[class*=STARTED] .job-detail-title {
        color: #2196f3;
    }

    .job-metadata-container[class*=FINISHED] {
        background: linear-gradient(to right, #1ed085 0, #1ed085 1rem, #fff 1rem, #fff 100%) no-repeat;
    }

    .job-metadata-container[class*=FINISHED] .job-detail-title {
        color: #1ed085;
    }

    .job-metadata-container[class*=FAILED] {
        background: linear-gradient(to right, #dc3545 0, #dc3545 1rem, #fff 1rem, #fff 100%) no-repeat;
    }

    .job-metadata-container[class*=FAILED] .job-detail-title {
        color: #dc3545;
    }
</style>