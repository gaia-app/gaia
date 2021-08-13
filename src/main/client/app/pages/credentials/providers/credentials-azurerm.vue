<template>
  <div>
    <b-form-group
      label="Client Id"
      description="Your Azure Client Id (ARM_CLIENT_ID)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.clientId"
        :state="notEmpty(credentials.clientId)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
    <b-form-group
      label="Client Secret"
      description="Your Azure Client Secret (ARM_CLIENT_SECRET)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.clientSecret"
        :state="notEmpty(credentials.clientSecret)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
    <b-form-group
      label="Subcription Id"
      description="Your Azure Subscription Id (ARM_SUBSCRIPTION_ID)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.subscriptionId"
        :state="notEmpty(credentials.subscriptionId)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
    <b-form-group
      label="Tenant Id"
      description="Your Azure Tenant Id (ARM_TENANT_ID)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.tenantId"
        :state="notEmpty(credentials.tenantId)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
    <b-form-group
      label="Backend Access Key"
      description="Your Azure backend access key (ARM_ACCESS_KEY)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.backendAccessKey"
      />
    </b-form-group>
    <b-form-group
      label="Environment"
      description="Your Azure Environment (ARM_ENVIRONMENT). Defaults to public."
    >
      <b-input
        id="credentials.name"
        v-model="credentials.environment"
      />
    </b-form-group>
  </div>
</template>

<script>
  export default {
    name: 'CredentialsAWS',

    props: {
      credentials: {
        type: Object,
        required: true,
      },
    },

    methods: {
      notEmpty(field) {
        return typeof field !== 'undefined' && field !== null && field.trim() !== '';
      },
    },
  };
</script>
