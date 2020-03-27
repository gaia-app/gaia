<template>
  <b-nav-item-dropdown>
    <template v-slot:button-content>
      <font-awesome-icon :icon="['far', 'user']" />
      <span id="user.name">{{ user.username }}</span>
    </template>
    <b-dropdown-item @click="doLogout">
      <span>Log Out</span>
      <font-awesome-icon icon="sign-out-alt" />
    </b-dropdown-item>
  </b-nav-item-dropdown>
</template>

<script>
  import { mapState } from 'vuex';

  export default {
    name: 'AppTopBarUser',
    computed: {
      ...mapState('session', {
        user: 'user',
      }),
    },
    methods: {
      doLogout() {
        this.$store.dispatch('session/logout').then(() => this.$router.push('login'));
      },
    },
  };
</script>

<style scoped>
  .dropdown {
    background: #0971b8;
    padding: 4px 0 4px 20px;
    line-height: 35px;
  }

  /deep/ .dropdown-toggle {
    cursor: pointer;
    font-size: 18px;
    font-weight: bold;
    color: #fff !important;
    padding: .5rem 1rem !important;
  }

  /deep/ .dropdown-toggle span {
    font-size: 13px;
    font-weight: 500;
    padding: 0 35px 0 10px;
  }

  /deep/ .dropdown-menu {
    border: none;
    border-radius: 0;
    box-shadow: -1px 1px 4px -2px rgba(0, 0, 0, 0.2);
    padding: 10px 6px;
    margin: 0;
    width: 100%;
  }

  /deep/ .dropdown-item {
    font-size: 15px;
    color: #15283c;
    padding: 4px 15px;
    border-bottom: none;
    line-height: normal;
  }

  /deep/ .dropdown-item span {
    font-size: 13px;
    margin-right: 5px;
  }

  /deep/ .dropdown-item:hover {
    background: #243147;
    color: #fff;
  }
</style>
