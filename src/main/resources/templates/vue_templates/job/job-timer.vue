<template id="job-timer-template">
    <span :class="cssClass">
        {{timer | format}}
    </span>
</template>

<script>
    Vue.component('job-timer', {
        template: '#job-timer-template',
        data: () => ({
            timer: 0,
            intervalId: null
        }),
        props: {
            startTime: {
                type: Date | moment | String,
                default: () => moment()
            },
            endTime: {
                type: Date | moment | String,
                default: () => null
            },
            cssClass: {
                type: String,
                default: ''
            }
        },
        created: function () {
            if (!!this.endTime) {
                this.interval(moment(this.endTime));
                return;
            }
            this.start();
        },
        watch: {
            endTime: function (newValue) {
                if (newValue == null) {
                    this.start();
                    return;
                }
                this.interval(moment(newValue));
                this.end();
            },
        },
        methods: {
            start: function () {
                this.intervalId = setInterval(() => this.interval(moment()), 1000);
            },
            end: function () {
                clearInterval(this.intervalId);
            },
            interval: function (endTime) {
                this.timer = moment.duration(endTime.diff(this.startTime));
            }
        },
        filters: {
            format: function (value) {
                if (!moment.isDuration(value)) return '';
                return value.format("h [hr] m [min] s [sec]");
            }
        }
    });
</script>
