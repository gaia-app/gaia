<template>
  <div class="container-login">
    <div class="wrap-login">
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

        <app-login-oauth-signin />
      </b-form>
    </div>
  </div>
</template>

<script>
  import AppLoginOauthSignin from '@/pages/login/login-oauth-signin.vue';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppLoginForm',
    components: {
      AppLoginOauthSignin,
    },
    data: () => ({
      username: null,
      password: null,
      rememberMe: false,
    }),
    methods: {
      doLogin() {
        const formData = new FormData();
        formData.append('username', this.username);
        formData.append('password', this.password);
        formData.append('remember-me', this.rememberMe);

        this.$store.dispatch('session/login', formData)
          .then(() => this.$router.push({ name: 'home' }, null, () => {
          }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'warning' }));
      },
    },
  };
</script>

<style scoped>
  .container-login {
    width: 100%;
    min-height: 100vh;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    padding: 15px;
    background: white;
    background: linear-gradient(-135deg, #baeb6c, #fff);
  }

  .wrap-login {
    width: 960px;
    background: #fff;
    border-radius: 10px;
    overflow: hidden;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding: 50px 130px 30px 95px;
  }

  .login-pic {
    width: 316px;
  }

  .login-pic img {
    max-width: 100%;
  }

  .login-form {
    width: 290px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .login-form-title {
    font-size: 24px;
    color: #333333;
    line-height: 1.2;
    text-align: center;
    width: 100%;
    display: block;
    margin: 0;
  }

  .wrap-input {
    position: relative;
    width: 100%;
    z-index: 1;
    margin-bottom: 10px;
  }

  .input {
    font-size: 15px;
    line-height: 1.5;
    color: #666666;
    display: block;
    width: 100%;
    background: #e6e6e6;
    height: 50px;
    border-radius: .25rem;
    padding: 0 30px 0 68px;
    outline: none;
    border: none;
  }

  .focus-input {
    display: block;
    position: absolute;
    border-radius: .25rem;
    bottom: 0;
    left: 0;
    z-index: -1;
    width: 100%;
    height: 100%;
    box-shadow: 0 0 0 0;
    color: rgba(87, 184, 70, 0.8);
  }

  .symbol-input {
    font-size: 15px;
    display: flex;
    align-items: center;
    position: absolute;
    border-radius: .25rem;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    padding-left: 35px;
    pointer-events: none;
    color: #666666;
    transition: all 0.4s;
  }

  .input:focus + .focus-input + .symbol-input {
    color: #57b846;
    padding-left: 28px;
  }

  .container-login-form-btn {
    width: 100%;
    justify-content: center;
  }

  .login-form-btn {
    font-size: 15px;
    line-height: 1.5;
    color: #fff;
    text-transform: uppercase;
    width: 100%;
    height: 50px;
    border-radius: .25rem;
    background: #96D629;
    transition: all 0.4s;
    outline: none;
    border: none;
  }

  .login-form-btn:hover {
    background: #57b846;
  }

  @media (max-width: 992px) {
    .login-pic {
      width: 35%;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .login-form {
      width: 50%;
    }
  }

  @media (max-width: 768px) {
    .wrap-login {
      padding: 33px 80px 33px 80px;
    }

    .login-pic {
      display: none;
    }

    .login-form {
      width: 100%;
    }
  }

  @media (max-width: 576px) {
    .wrap-login {
      padding: 30px;
    }
  }
</style>
