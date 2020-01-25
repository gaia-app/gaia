<template id="user-badge-template">
    <span class="user_badge" :class="cssClass">
        <a href="#">{{user.username}}</a>
        <i v-if="user.admin" class="fas fa-user-shield"></i>
    </span>
</template>

<script>
    Vue.component('user-badge', {
        template: '#user-badge-template',
        props: ['user', 'cssClass'],
    });
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