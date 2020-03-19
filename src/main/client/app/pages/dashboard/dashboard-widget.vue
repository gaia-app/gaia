<template>
  <router-link
    class="widget"
    :to="to"
  >
    <div class="widget-icon">
      <font-awesome-icon
        class="icon"
        :icon="icon"
      />
    </div>
    <div class="widget-detail">
      <p class="widget-value">
        {{ value }}
      </p>
      <p class="widget-text">
        {{ text }}
      </p>
    </div>
  </router-link>
</template>

<script>
  export default {
    name: 'AppDashboardWidget',
    props: {
      text: { type: String, default: '' },
      value: { type: Number, default: 0 },
      icon: { type: [String, Array, Object], default: null },
      to: { type: [String, Object], default: null },
    },
  };
</script>

<style scoped>
  .widget {
    border: 0 solid #e7e7e7;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
    padding: 30px 35px;
    border-radius: 3px;
    display: flex;
  }

  .widget-icon {
    display: flex;
    justify-content: center;
    align-items: center;
    border-right: solid rgba(255, 255, 255, 0.3) 1px;
    margin: 8px 0 8px;
    padding-right: 35px;
  }

  .widget-icon .icon {
    color: #fff;
    font-size: 50px;
    max-width: 50px;
  }

  .widget-detail {
    padding: 0 12px;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .widget-detail .widget-value {
    margin: 0;
    line-height: normal;
    text-align: right;
    font-size: 24px;
    font-weight: 400;
    color: #fff;
  }

  .widget-detail .widget-text {
    margin: 0;
    text-align: right;
    font-size: 20px;
    font-weight: 300;
    color: rgba(255, 255, 255, .7);
  }

  @media (min-width: 992px) and (max-width: 1600px) {
    .widget {
      flex-direction: column;
      padding: 30px 25px;
    }

    .widget-icon {
      border: none;
      padding: 0;
    }

    .widget-detail .widget-value {
      text-align: center;
    }

    .widget-detail .widget-text {
      text-align: center;
      font-size: 16px;
    }
  }

  @media (max-width: 991px) {
    .widget {
      padding: 30px 25px;
    }

    .widget-detail .widget-text {
      font-size: 16px;
    }
  }
</style>
