<template>
  <div class="error_page">
    <div class="center">
      <img
        alt="#"
        class="img-responsive"
        src="@/assets/images/no-entry.png"
      >
    </div>
    <br>
    <h3>STOP HERE !</h3>
    <p>YOU'RE NOT ALLOWED TO SEE THIS PAGE !</p>
    <div class="center">
      <router-link
        class="btn btn-success"
        :to="{ name: 'home'}"
      >
        Go To Home Page
      </router-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'AppPageForbidden',
  };
</script>
