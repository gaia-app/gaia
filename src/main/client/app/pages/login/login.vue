<template>
  <div class="limiter">
    <div class="container-login">
      <div class="wrap-login shadow bg-white rounded">
        <div class="login-pic">
          <img
            src="@/assets/images/gaia.png"
            alt="gaia-logo"
          >
        </div>

        <b-form
          role="form"
          class="login-form"
          @submit.prevent="doLogin"
        >
          <h1 class="login-form-title">
            Gaia - Login
          </h1>

          <div>
            <div class="wrap-input">
              <b-form-input
                id="username"
                v-model="username"
                class="input"
                type="text"
                name="username"
                placeholder="Username"
                autofocus
              />
              <span class="focus-input" />
              <span class="symbol-input"><font-awesome-icon icon="user" /></span>
            </div>
            <div class="wrap-input">
              <b-form-input
                id="password"
                v-model="password"
                class="input"
                type="password"
                name="password"
                placeholder="Password"
              />
              <span class="focus-input" />
              <span class="symbol-input"><font-awesome-icon icon="lock" /></span>
            </div>
            <div class="">
              <b-form-checkbox
                id="remember-me"
                v-model="rememberMe"
                name="remember-me"
              >
                Remember me
              </b-form-checkbox>
            </div>
          </div>

          <div class="container-login-form-btn">
            <button
              type="submit"
              class="login-form-btn"
            >
              Login
            </button>
          </div>

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
        </b-form>
      </div>
    </div>
  </div>
</template>

<script>
  import { listProviders } from '@/shared/api';

  export default {
    name: 'AppLoginForm',
    data: () => ({
      username: null,
      password: null,
      rememberMe: false,
      displayOauth2Clients: false,
      oauth2Clients: [],
    }),
    async created() {
      this.oauth2Clients = await listProviders();
      this.displayOauth2Clients = !!this.oauth2Clients && this.oauth2Clients.length > 0;
    },
    methods: {
      doLogin() {
        const formData = new FormData();
        formData.append('username', this.username);
        formData.append('password', this.password);
        formData.append('remember-me', this.rememberMe);

        this.$store.dispatch('session/login', formData)
          .then(() => this.$router.push({ name: 'home' }, null, () => {}))
          .catch((error) => this.displayErrorMessage(error.response.data));
      },
      displayErrorMessage(error) {
        this.$bvToast.toast(error.error, {
          solid: true,
          toaster: 'b-toaster-top-center',
          title: error.message,
          variant: 'warning',
        });
      },
    },
  };
</script>

<style scoped>
  @import "~@/assets/css/login.css";
</style>
