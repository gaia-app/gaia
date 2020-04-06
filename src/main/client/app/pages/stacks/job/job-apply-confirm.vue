<template>
  <div class="job-apply-confirm-container">
    <div class="arrow">
      <span />
      <span />
      <span />
    </div>
    <b-button
      variant="primary"
      @click="$emit('apply')"
    >
      {{ title }}
    </b-button>
  </div>
</template>

<script>
  export default {
    name: 'AppJobApplyConfirm',
    props: {
      title: { type: String, required: true },
    },
  };
</script>

<style scoped>
  .job-apply-confirm-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .job-apply-confirm-container .btn {
    position: absolute;
    z-index: 1;
    text-transform: capitalize;
  }

  .job-apply-confirm-container .arrow {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    height: 85px;
  }

  .job-apply-confirm-container .arrow span {
    position: absolute;
    top: -5px;
    left: -10px;
    opacity: 0;
    width: 20px;
    height: 20px;
    border-bottom: 5px solid #2196f3;
    border-right: 5px solid #2196f3;
    animation: animate 2s infinite;
  }

  .job-apply-confirm-container .arrow span:nth-child(2) {
    animation-delay: 0.5s;
  }

  .job-apply-confirm-container .arrow span:nth-child(3) {
    animation-delay: 1s;
  }

  @keyframes animate {
    0% {
      transform: rotate(45deg);
    }
    50% {
      opacity: 1;
    }
    100% {
      opacity: 0;
      transform: rotate(45deg) translate(45px, 45px);
    }
  }
</style>
