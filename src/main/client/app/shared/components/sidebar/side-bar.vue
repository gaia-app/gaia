<template>
  <div :class="collapsed ? 'sidebar collapsed' : 'sidebar'">
    <div class="sidebar-header">
      <app-side-bar-toggle
        v-model="collapsed"
        @input="toggleSidebar"
      />
    </div>
    <div class="sidebar-body">
      <app-side-bar-links :collapse="collapsed" />
    </div>
    <div
      v-if="buildInfo"
      class="sidebar-footer"
    >
      <app-side-bar-infos :build-info="buildInfo" />
    </div>
  </div>
</template>

<script>
  import AppSideBarToggle from '@/shared/components/sidebar/side-bar-toggle.vue';
  import AppSideBarLinks from '@/shared/components/sidebar/side-bar-links.vue';
  import AppSideBarInfos from '@/shared/components/sidebar/side-bar-infos.vue';
  import {
    getCookie,
    setCookie,
  } from '@/shared/services/cookie-service';
  import { getBuildInfo } from '@/shared/api/build-infos-api';

  const SIDEBAR_COOKIE = 'sidebar_collapsed';
  const SIDEBAR_MAX_AGE = 60 * 60 * 24 * 365 * 10;
  const WINDOW_MIN_SIZE = 1199;

  export default {
    name: 'AppSideBar',
    components: {
      AppSideBarToggle,
      AppSideBarLinks,
      AppSideBarInfos,
    },
    data: () => ({
      collapsed: false,
      buildInfo: {},
    }),
    async created() {
      this.buildInfo = await getBuildInfo();
      this.manageSidebarWithWindowSize();
      // listener on window resize
      window.addEventListener('resize', this.manageSidebarWithWindowSize);
    },
    destroyed() {
      window.removeEventListener('resize', this.manageSidebarWithWindowSize);
    },
    methods: {
      manageSidebarWithWindowSize() {
        if (window.innerWidth <= WINDOW_MIN_SIZE) {
          this.collapsed = true;
        } else {
          this.collapsed = getCookie(SIDEBAR_COOKIE) === 'true';
        }
        this.$emit('collapse', this.collapsed);
      },
      toggleSidebar(value) {
        setCookie(SIDEBAR_COOKIE, value, SIDEBAR_MAX_AGE);
        this.$emit('collapse', value);
      },
    },
  };
</script>

<style scoped>
  .sidebar {
    background-color: #fff;
    box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.4);
    min-width: 240px;
    max-width: 240px;
    width: 100%;
    height: calc(100% - 60px);
    transition: all 0.3s;
    z-index: 11;
    float: left;
    position: fixed;
    left: 0;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .sidebar.collapsed {
    min-width: 70px;
    max-width: 70px;
  }

  .sidebar-body {
    flex-grow: 1;
  }

  .sidebar.collapsed .sidebar-footer {
    display: none;
  }
</style>
