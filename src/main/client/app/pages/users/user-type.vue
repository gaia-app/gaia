<template>
  <span>
    <span v-if="user.oauth2User != null && user.oauth2User.provider === 'gitlab'">
      <font-awesome-icon
        :icon="['fab', 'gitlab']"
        class="icon"
      />
      Gitlab OAuth 2 User
    </span>
    <span v-else-if="user.oauth2User != null && user.oauth2User.provider === 'github'">
      <font-awesome-icon
        :icon="['fab','github']"
        class="icon"
      />
      Github OAuth 2 User
    </span>
    <span v-else>
      Local User
    </span>
  </span>
</template>

<script>
  export default {
    name: 'AppUserType',

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
