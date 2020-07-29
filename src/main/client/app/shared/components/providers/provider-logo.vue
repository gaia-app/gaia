<template>
  <div :class="provider || 'unknown'">
    <b-img
      :src="imageUrl"
      class="rounded-logo"
      rounded="circle"
      :width="size"
      :height="size"
    />
  </div>
</template>

<script>
  export default {
    name: 'ProviderLogo',

    props: {
      provider: {
        type: String,
        required: false,
        default: null,
      },
      size: {
        type: String,
        default: '80px',
      },
    },

    computed: {
      imageUrl() {
        // eslint-disable-next-line global-require, import/no-dynamic-require
        return require(`@/assets/images/providers/logos/${this.provider || 'unknown'}.png`);
      },
    },
  };
</script>

<style scoped>
  .rounded-logo {
    background-color: white;
  }

  .unknown {
    background-color: #4f55e3;
  }

  .google {
    background-color: #2f6fd8;
  }

  .docker {
    background-color: #2496ed;
  }

  .aws {
    background-color: #ea8c00;
  }

  .azurerm {
    background-color: #007cc1;
  }

  .vault-aws {
    background-color: #000000;
  }
</style>
