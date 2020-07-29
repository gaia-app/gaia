<template>
  <div>
    <b-form-group
      label="Vault AWS Secret engine path"
      description="The path of your AWS secret engine in Vault"
    >
      <b-input
        id="credentials.awsSecretEnginePath"
        v-model="credentials.vaultAwsSecretEnginePath"
        :state="notEmpty(credentials.vaultAwsSecretEnginePath)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
    <b-form-group
      label="Vault Role"
      description="The Vault role to use to create credentials"
    >
      <b-input
        id="credentials.vaultAwsRole"
        v-model="credentials.vaultAwsRole"
        :state="notEmpty(credentials.vaultAwsRole)"
      />
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>
  </div>
</template>

<script>
  export default {
    name: 'CredentialsVaultAWS',

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
