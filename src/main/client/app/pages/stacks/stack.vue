<template>
  <div>
    <p>This is not the final stack {{ $route.params.id }} page xD</p>
    <router-view />
  </div>
</template>

<script>
  export default {
    name: 'AppStack',
  };
</script>

<style scoped>

</style>
