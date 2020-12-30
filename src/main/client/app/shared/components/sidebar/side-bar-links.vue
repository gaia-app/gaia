<template>
  <b-nav>
    <b-nav-item
      v-for="entry in authorizedEntries"
      :key="entry.route"
      :to="{ name: entry.route }"
      active-class="active"
      link-classes="rippled"
    >
      <font-awesome-icon
        :icon="entry.icon"
        :class="entry.class + ' icon'"
      />
      <span v-if="!collapse">{{ entry.title }}</span>
    </b-nav-item>
  </b-nav>
</template>

<script>
  import { mapGetters } from 'vuex';

  export default {
    name: 'AppSideBarLinks',
    props: {
      collapse: { type: Boolean, required: true },
    },
    data: () => ({
      entries: [
        {
          route: 'dashboard', icon: 'tachometer-alt', class: 'yellow_color', title: 'Dashboard', roles: ['ROLE_USER'],
        },
        {
          route: 'modules', icon: 'object-group', class: 'blue1_color', title: 'Modules', roles: ['ROLE_USER'],
        },
        {
          route: 'stacks', icon: 'layer-group', class: 'blue2_color', title: 'Stacks', roles: ['ROLE_USER'],
        },
        {
          route: 'credentialsList', icon: 'lock', class: 'orange_color', title: 'Credentials', roles: ['ROLE_USER'],
        },
        {
          route: 'settings', icon: 'cog', class: 'yellow_color', title: 'Settings', roles: ['ROLE_ADMIN'],
        },
        {
          route: 'users', icon: 'user-friends', class: 'yellow_color', title: 'Users', roles: ['ROLE_ADMIN'],
        },
        {
          route: 'organizations', icon: 'sitemap', class: 'yellow_color', title: 'Organizations', roles: ['ROLE_ADMIN'],
        },
      ],
    }),
    computed: {
      ...mapGetters('session', ['hasAuthorities']),
      authorizedEntries() {
        return this.entries.filter((entry) => this.hasAuthorities(entry.roles));
      },
    },
  };
</script>

<style scoped>
  .nav {
    display: block;
  }

  .nav-link {
    color: #222;
    padding: 15px 25px;
    font-size: 14px;
    font-weight: 400;
    display: flex;
    align-items: center;
    line-height: 20px;
    white-space: nowrap;
  }

  .nav-link.active {
    box-shadow: inset 4px 0 0 #00ab94;
    background: rgba(0, 171, 148, 0.1);
    color: #00ab94;
  }

  .nav-link.active .icon {
    color: #00ab94 !important;
  }

  .nav-link:hover {
    background: rgba(9, 113, 184, 0.1);
  }

  .nav-link .icon {
    font-size: 20px;
    width: 20px;
  }

  .nav-link span {
    margin-left: 15px;
  }

  .rippled {
    position: relative;
    overflow: hidden;
  }

  .rippled:after {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    width: 5px;
    height: 5px;
    background: rgba(0, 171, 148, .5);
    opacity: 0;
    border-radius: 100%;
    transform: scale(1, 1) translate(-50%);
    transform-origin: 50% 50%;
  }

  @keyframes ripple {
    0% {
      transform: scale(0, 0);
      opacity: 1;
    }
    20% {
      transform: scale(50, 50);
      opacity: 1;
    }
    100% {
      opacity: 0;
      transform: scale(100, 100);
    }
  }

  .rippled:focus:not(:active)::after {
    animation: ripple 1s ease-out;
  }
</style>
