<template>
  <div class="full_container">
    <div class="inner_container">
      <app-top-bar />
      <div :class="expandContent ? 'content expand' : 'content'">
        <app-side-bar @collapse="expandContent = $event" />
        <div class="container-fluid">
          <div class="row column_title">
            <div class="col-md-12">
              <div class="page_title">
                <app-breadcrumb />
              </div>
            </div>
          </div>
          <slot />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    AppBreadcrumb,
    AppSideBar,
    AppTopBar,
  } from '@/shared/components';

  export default {
    name: 'AppDefaultLayout',
    components: {
      AppBreadcrumb,
      AppTopBar,
      AppSideBar,
    },
    data: () => ({
      expandContent: false,
    }),
    mounted() {
      document.body.classList.add('dashboard');
    },
    destroyed() {
      document.body.classList.remove('dashboard');
    },
  };
</script>

<style scoped>
  .inner_container {
    padding: 0;
    margin: 0;
    display: flex;
  }

  .content {
    width: 100%;
    min-height: 100vh;
    transition: ease all 0.3s;
    position: relative;
    padding: 60px 25px 25px 265px;
    background: #f8f8f8;
  }

  .content.expand {
    padding-left: 95px;
  }

  @media (max-width: 1199px) {
    .content {
      padding-left: 95px;
    }
  }
</style>
