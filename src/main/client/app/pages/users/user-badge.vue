<template>
  <span class="user_badge">
    <a href="#">{{ user.username }}</a>
    <font-awesome-icon
      v-if="user.admin"
      icon="user-shield"
    />
  </span>
</template>

<script>
  export default {
    name: 'AppUserBadge',

    props: {
      user: {
        type: Object,
        required: true,
      },
    },
  };
</script>

<style scoped>
  .user_badge a {
    color: #3273dc !important;
    text-decoration: none !important;
  }

  .user_badge .fa-user-shield {
    color: #ff9900;
  }
</style>
