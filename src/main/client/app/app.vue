<template>
  <div id="app">
    <component :is="layout">
      <router-view />
    </component>
  </div>
</template>

<script>
  export default {
    computed: {
      layout() {
        return `app-${this.$route.meta.layout || 'default'}-layout`;
      },
    },
  };
</script>

<style>
  /* vendor css */
  @import '~bootstrap/dist/css/bootstrap.min.css';
  @import '~bootstrap-vue/dist/bootstrap-vue.min.css';
  @import '~vue-multiselect/dist/vue-multiselect.min.css';
  @import '~vue-form-wizard/dist/vue-form-wizard.min.css';
  /* application css */
  @import "~@/assets/css/style.css";
  @import "~@/assets/css/responsive.css";
</style>
