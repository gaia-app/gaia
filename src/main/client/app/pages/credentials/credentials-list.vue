<template>
  <div>
    <b-button
      :to="{ name: 'new_credentials' }"
      title="Create new credentials"
      variant="success"
      class="mb-4"
    >
      <font-awesome-icon icon="plus" /> Create new credentials
    </b-button>

    <b-card-group deck>
      <b-card
        v-for="credentials in credentialsList"
        :key="credentials.id"
        :title="credentials.name"
        style="max-width: 20rem;"
        :header-class="credentials.provider"
      >
        <template v-slot:header>
          <b-img
            :src="imageUrl(credentials)"
            class="rounded-logo"
            rounded="circle"
            width="80px"
            height="80px"
            left
          />
          <p class="providerName">
            {{ providerName(credentials) }}
          </p>
        </template>

        <b-button
          :to="{ name: 'credentials', params: { credentialsId: credentials.id } }"
          title="Edit this credentials"
          variant="primary"
          class="mr-1"
        >
          <font-awesome-icon icon="edit" />
        </b-button>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
  import { getCredentialsList } from '@/shared/api/credentials-api';

  export default {
    name: 'Credentials',

    data: function data() {
      return {
        credentialsList: [],
      };
    },

    async created() {
      this.credentialsList = await getCredentialsList();
    },

    methods: {
      imageUrl(credentials) {
        // eslint-disable-next-line global-require, import/no-dynamic-require
        return require(`@/assets/images/providers/logos/${credentials.provider}.png`);
      },

      providerName(credentials) {
        return {
          aws: 'AWS',
          google: 'GCP',
          azurerm: 'Azure',
        }[credentials.provider];
      },
    },

  };
</script>

<style scoped>
  .card-header {
    display: flex;
    align-items: center;
  }

  .card-header img {
    margin-right: 1rem;
  }

  .card-header.google {
    background-color: #2f6fd8;
  }

  .card-header.aws {
    background-color: #ea8c00;
  }

  .card-header.azurerm {
    background-color: #007cc1;
  }

  .rounded-logo {
    background-color: white;
  }

  .providerName {
    color: white;
    font-weight: bold;
    font-size: x-large;
  }
</style>
