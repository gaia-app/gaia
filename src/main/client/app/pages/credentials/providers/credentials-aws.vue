<template>
  <div>
    <b-form-group
      label="Access Key Id"
      description="Your AWS Access Key Id (AWS_ACCESS_KEY_ID)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.accessKey"
        :state="notEmpty(credentials.accessKey)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
    <b-form-group
      label="Secret Access Key"
      description="Your AWS Secret Access Key (AWS_SECRET_ACCESS_KEY)"
    >
      <b-input
        id="credentials.name"
        v-model="credentials.secretKey"
        :state="notEmpty(credentials.secretKey)"
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
