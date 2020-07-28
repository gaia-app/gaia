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

    <div class="card-layout">
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
          <b-button
            title="Delete this credentials"
            variant="danger"
            @click="deleteCredentials(credentials)"
          >
            <font-awesome-icon :icon="['far', 'trash-alt']" />
          </b-button>
        </template>
      </b-card>
    </div>
  </div>
</template>

<script>
  import { getCredentialsList, deleteCredentials } from '@/shared/api/credentials-api';

  import { AppProviderHeader } from '@/shared/components';
  import { displayConfirmDialog, displayNotification } from '@/shared/services/modal-service';

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

    methods: {
      async deleteCredentials(credentials) {
        const message = 'This will delete the credentials. Continue?';
        const confirm = await displayConfirmDialog(this, { title: 'Delete Request', message });
        if (confirm) {
          try {
            await deleteCredentials(credentials);
            displayNotification(this, { message: 'Credentials deleted.', variant: 'info' });

            const index = this.credentialsList.findIndex((cred) => cred.id === credentials.id);
            this.credentialsList.splice(index, 1);
          } catch (e) {
            displayNotification(this, { message: 'Unable to delete credentials.', variant: 'danger' });
          }
        }
      },
    },
  };
</script>

<style scoped>
  .card-layout {
    display: flex;
    flex-wrap: wrap;
  }

  .card {
    width: 350px;
    margin-right: 1rem;
    margin-bottom: 1rem;
  }

  .card-header {
    padding: 0;
  }

  .card-title {
    margin: 0;
  }

  .card-footer {
    display: flex;
    justify-content: space-between;

  }
</style>
