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
      >
        <template v-slot:header>
          <app-provider-header :provider="credentials.provider" />
        </template>

        <template v-slot:footer>
          <b-button
            :to="{ name: 'credentials', params: { credentialsId: credentials.id } }"
            title="Edit this credentials"
            variant="primary"
            class="mr-1"
          >
            <font-awesome-icon icon="edit" />
          </b-button>
        </template>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
  import { getCredentialsList } from '@/shared/api/credentials-api';

  import { AppProviderHeader } from '@/shared/components';

  export default {
    name: 'Credentials',

    components: {
      AppProviderHeader,
    },

    data: function data() {
      return {
        credentialsList: [],
      };
    },

    async created() {
      this.credentialsList = await getCredentialsList();
    },
  };
</script>

<style scoped>
  .card-header {
    padding: 0;
  }

  .card-title {
    margin: 0;
  }
</style>
