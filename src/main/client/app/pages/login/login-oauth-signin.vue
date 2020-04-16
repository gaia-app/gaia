<template>
  <div v-if="displayOauth2Clients">
    <h6 class="sign-in">
      or Sign In with
    </h6>
    <div class="login-oauth">
      <a
        v-for="client in oauth2Clients"
        :key="client"
        :href="'/auth/oauth2/' + client"
        :class="'btn btn-outline-secondary btn-sm btn-' + client"
      >
        <font-awesome-icon :icon="['fab', client]" />
        <span>{{ client }}</span>
      </a>
    </div>
  </div>
</template>

<script>
  import { listProviders } from '@/shared/api/authentication-api';

  export default {
    name: 'AppLoginOauthSignin',
    data: () => ({
      displayOauth2Clients: false,
      oauth2Clients: [],
    }),
    async created() {
      this.oauth2Clients = await listProviders();
      this.displayOauth2Clients = !!this.oauth2Clients && this.oauth2Clients.length > 0;
    },
  };
</script>

<style scoped>
  .sign-in {
    display: flex;
    align-items: center;
    color: #333333;
    font-size: smaller;
    margin: .5em 0;
    text-align: center;
    text-transform: uppercase;
  }

  .sign-in:before, .sign-in:after {
    content: "";
    border-top: .1em solid;
    flex: 1;
    margin: 0 .5em;
  }

  .login-oauth {
    display: flex;
    justify-content: space-evenly;
  }

  .login-oauth .btn {
    font-size: 1.05em;
    display: flex;
    align-items: center;
  }

  .login-oauth .btn span {
    margin-left: .5em;
  }

  .login-oauth .btn-gitlab {
    color: #fca326;
  }

  .login-oauth .btn-gitlab:hover {
    background-color: #fca326;
    border-color: #fca326;
    color: #fff;
  }

  .login-oauth .btn-github {
    color: #333;
  }

  .login-oauth .btn-github:hover {
    background-color: #333;
    border-color: #333;
    color: #fff;
  }
</style>
