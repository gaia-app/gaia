<template>
  <span>
    {{ user.username }}

    <b-badge
      v-if="user.admin"
      variant="primary"
      class="mr-1"
    >
      <font-awesome-icon icon="user-shield" /> Administrator
    </b-badge>

    <b-badge
      v-if="user.oauth2User != null && user.oauth2User.provider === 'github'"
      variant="dark"
    >
      <font-awesome-icon :icon="['fab','github']" /> Github login
    </b-badge>

    <b-badge
      v-if="user.oauth2User != null && user.oauth2User.provider === 'gitlab'"
      variant="warning"
    >
      <font-awesome-icon :icon="['fab','gitlab']" /> Gitlab login
    </b-badge>

  </span>
</template>

<script>
  export default {
    name: 'AppUserName',

    props: {
      user: {
        type: Object,
        required: true,
      },
    },
  };
</script>
