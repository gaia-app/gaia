<template>
  <div
    :class="provider"
    style="width: 100%; padding: 1.25rem;"
  >
    <app-provider-logo
      :provider="provider"
      size="80px"
      style="margin-right: 1rem"
    />
    <p class="providerName mb-0">
      {{ providerName }}
    </p>
  </div>
</template>

<script>
  import { AppProviderLogo } from '@/shared/components';

  export default {
    name: 'ProviderHeader',
    components: {
      AppProviderLogo,
    },
    props: {
      provider: {
        type: String,
        required: true,
      },
    },
    computed: {
      providerName() {
        return {
          aws: 'AWS',
          google: 'GCP',
          azurerm: 'Azure',
          'vault-aws': 'Vault for AWS',
        }[this.provider];
      },
    },
  };
</script>

<style scoped>
  div {
    display: flex;
    align-items: center;
  }

  .aws {
    background-color: #ea8c00;
  }

  .azurerm {
    background-color: #007cc1;
  }

  .google {
    background-color: #2f6fd8;
  }

  .vault-aws {
    background-color: #000000;
  }

  .providerName {
    color: white;
    font-weight: bold;
    font-size: x-large;
  }
</style>
