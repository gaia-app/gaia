<template>
  <span :class="cssClass">
    {{ timer | format }}
  </span>
</template>

<script>
  export default {
    name: 'AppJobTimer',
    filters: {
      format(value) {
        const hours = Math.floor(value / 3600) % 24;
        const minutes = Math.floor(value / 60) % 60;
        const seconds = (`0${value % 60}`).slice(-2);
        return `${hours > 0 ? `${hours} hr` : ''}
                    ${minutes > 0 ? `${minutes} min` : ''}
                    ${seconds} sec`;
      },
    },
    props: {
      startTime: { type: [Date, String], default: null },
      endTime: { type: [Date, String], default: null },
      cssClass: { type: String, default: null },
    },
    data: () => ({
      timer: 0,
      intervalId: null,
    }),
    watch: {
      startTime(newValue) {
        if (newValue != null) this.reset();
      },
      endTime(newValue) {
        if (newValue == null) {
          this.start();
          return;
        }
        this.interval(newValue);
        this.end();
      },
    },
    created() {
      if (!this.startTime) return;
      if (this.endTime) {
        this.interval(this.endTime);
        return;
      }
      this.start();
    },
    destroyed() {
      this.end();
    },
    methods: {
      start() {
        this.intervalId = setInterval(() => this.interval(new Date()), 1000);
      },
      end() {
        clearInterval(this.intervalId);
      },
      interval(endTime) {
        this.timer = Math.floor((Date.parse(endTime) - Date.parse(this.startTime)) / 1000);
      },
      reset() {
        this.timer = 0;
        this.end();
        this.start();
      },
    },
  };
</script>
