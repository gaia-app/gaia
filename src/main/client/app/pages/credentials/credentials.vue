<template>
  <div>
    <b-card
      v-if="credentials"
      :header-class="credentials.provider"
    >
      <b-card-title>
        Credentials : {{ credentials.name }}
      </b-card-title>

      <template v-slot:header>
        <b-img
          :src="imageUrl"
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

      <b-button
        title="Save"
        variant="success"
        class="mb-4"
        @click="save"
      >
        <font-awesome-icon icon="save" /> Save
      </b-button>
    </b-card>
  </div>
</template>

<script>
  import { getCredentials, updateCredentials } from '@/shared/api/credentials-api';
  import { displayNotification } from '@/shared/services/modal-service';

  import AppCredentialsAws from '@/pages/credentials/providers/credentials-aws.vue';
  import AppCredentialsAzurerm from '@/pages/credentials/providers/credentials-azurerm.vue';
  import AppCredentialsGoogle from '@/pages/credentials/providers/credentials-google.vue';

  export default {
    name: 'Credentials',

    components: {
      AppCredentialsAws,
      AppCredentialsAzurerm,
      AppCredentialsGoogle,
    },

    props: {
      credentialsId: {
        type: String,
        required: true,
      },
    },

    data: function data() {
      return {
        credentials: null,
      };
    },

    computed: {
      imageUrl() {
        // eslint-disable-next-line global-require, import/no-dynamic-require
        return require(`@/assets/images/providers/logos/${this.credentials.provider}.png`);
      },
    },

    async created() {
      this.credentials = await getCredentials(this.credentialsId);
    },

    methods: {
      providerName(credentials) {
        return {
          aws: 'AWS',
          google: 'GCP',
          azurerm: 'Azure',
        }[credentials.provider];
      },

      notEmpty(field) {
        return typeof field !== 'undefined' && field !== null && field.trim() !== '';
      },

      async save() {
        await updateCredentials(this.credentials)
          .then(() => displayNotification(this, { message: 'Credentials saved', variant: 'success' }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
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
