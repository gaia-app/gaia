<template>
  <h2 class="navigation">
    <b-breadcrumb :items="items" />
  </h2>
</template>

<script>
  export default {
    name: 'AppBreadcrumb',
    data: () => ({
      items: [],
    }),
    watch: {
      '$route.meta.breadcrumb': {
        immediate: true,
        handler(items) {
          this.items = items;
        },
      },
    },
  };
</script>
