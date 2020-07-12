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
