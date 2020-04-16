<template>
  <div class="error_page">
    <div class="center">
      <img
        alt="#"
        class="img-responsive"
        src="@/assets/images/crying-face.png"
      >
    </div>
    <br>
    <h3>PAGE NOT FOUND !</h3>
    <p>YOU SEEM TO BE TRYING TO FIND YOUR WAY HOME</p>
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
    name: 'AppPageNotFound',
  };
</script>
