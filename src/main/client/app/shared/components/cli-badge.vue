<template>
  <img
    :src="'https://img.shields.io/badge/terraform-' + version + '-blue.svg?style=' + badgeStyle"
    :alt="version"
    :title="version"
  >
</template>

<script>
  export default {
    name: 'AppCliBadge',
    props: {
      cli: {
        type: Object,
        required: true,
      },
      badgeStyle: {
        type: String,
        required: true,
      },
    },
    computed: {
      version() {
        return this.cli.repository === 'hashicorp/terraform' ? this.cli.tag : 'custom';
      },
    },
  };
</script>
