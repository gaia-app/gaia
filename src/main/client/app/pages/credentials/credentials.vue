<template>
  <div>
    <b-card
      v-if="credentials"
    >
      <b-card-title>
        Credentials : {{ credentials.name }}
      </b-card-title>

      <template v-slot:header>
        <app-provider-header :provider="credentials.provider" />
      </template>

      <b-form-group
        label="Name"
        description="The name of your credentials"
      >
        <b-input
          id="credentials.name"
          v-model="credentials.name"
          :state="notEmpty(credentials.name)"
        />
        <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
      </b-form-group>

      <app-credentials-aws
        v-if="credentials.provider === 'aws'"
        :credentials="credentials"
      />
      <app-credentials-azurerm
        v-if="credentials.provider === 'azurerm'"
        :credentials="credentials"
      />
      <app-credentials-google
        v-if="credentials.provider === 'google'"
        :credentials="credentials"
      />
      <app-credentials-vault-aws
        v-if="credentials.provider === 'vault-aws'"
        :credentials="credentials"
      />

      <template v-slot:footer>
        <b-button
          title="Save"
          variant="success"
          @click="save"
        >
          <font-awesome-icon icon="save" /> Save
        </b-button>
      </template>
    </b-card>
  </div>
</template>

<script>
  import { getCredentials, createCredentials, updateCredentials } from '@/shared/api/credentials-api';
  import { displayNotification } from '@/shared/services/modal-service';

  import AppProviderHeader from '@/shared/components/providers/provider-header.vue';
  import AppCredentialsAws from '@/pages/credentials/providers/credentials-aws.vue';
  import AppCredentialsAzurerm from '@/pages/credentials/providers/credentials-azurerm.vue';
  import AppCredentialsGoogle from '@/pages/credentials/providers/credentials-google.vue';
  import AppCredentialsVaultAws from '@/pages/credentials/providers/credentials-vault-aws.vue';

  export default {
    name: 'Credentials',

    components: {
      AppCredentialsAws,
      AppCredentialsAzurerm,
      AppCredentialsGoogle,
      AppCredentialsVaultAws,
      AppProviderHeader,
    },

    props: {
      credentialsId: {
        type: String,
        default: null,
      },
      credentials: {
        type: Object,
        default: () => null,
      },
    },

    async created() {
      if (this.credentialsId) {
        this.credentials = await getCredentials(this.credentialsId);
      }
    },

    methods: {
      notEmpty(field) {
        return typeof field !== 'undefined' && field !== null && field.trim() !== '';
      },

      async save() {
        await this.createOrUpdate()
          .then(() => displayNotification(this, { message: 'Credentials saved', variant: 'success' }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },

      async createOrUpdate() {
        if (!this.credentials.id) {
          return createCredentials(this.credentials);
        }
        return updateCredentials(this.credentials);
      },
    },

  };
</script>

<style scoped>
  .card-header {
    display: flex;
    align-items: center;
    padding: 0;
  }

  .card-header img {
    margin-right: 1rem;
  }
</style>
