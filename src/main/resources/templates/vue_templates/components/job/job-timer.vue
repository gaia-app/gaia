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
            intervalId: null,
        }),
        props: {
            startTime: {
                type: Date | String,
            },
            endTime: {
                type: Date | String,
            },
            cssClass: {
                type: String,
                default: ''
            }
        },
        created: function () {
            if (!!this.endTime) {
                this.interval(this.endTime);
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
                this.interval(newValue);
                this.end();
            },
        },
        methods: {
            start: function () {
                this.intervalId = setInterval(() => this.interval(new Date()), 1000);
            },
            end: function () {
                clearInterval(this.intervalId);
            },
            interval: function (endTime) {
                this.timer = Math.floor((Date.parse(endTime) - Date.parse(this.startTime)) / 1000);
            }
        },
        filters: {
            format: function (value) {
                const hours = Math.floor(value / 3600) % 24;
                const minutes = Math.floor(value / 60) % 60;
                const seconds = ('0' + value % 60).slice(-2);
                return `${hours > 0 ? `${hours} hr` : ''}
                    ${minutes > 0 ? `${minutes} min` : ''}
                    ${seconds} sec`;
            }
        }
    });
</script>
