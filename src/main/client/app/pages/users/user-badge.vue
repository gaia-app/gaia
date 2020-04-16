<template>
  <span class="user_badge">
    <a href="#">{{ user.username }}</a>&nbsp;
    <font-awesome-icon
      v-if="user.admin"
      icon="user-shield"
      class="icon"
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

  .user_badge .icon {
    color: #ff9900;
  }
</style>
