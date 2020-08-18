<template>
  <div>
    <b-form-group
      label="Service account key file"
      description="Your service account key file contents (GOOGLE_CREDENTIALS)"
    >
      <b-input-group>
        <b-input-group-text slot="append">
          {json}
        </b-input-group-text>
        <b-form-textarea
          v-model="credentials.serviceAccountJSONContents"
          :state="notEmpty(credentials.serviceAccountJSONContents)"
          rows="10"
        />
      </b-input-group>
      <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
    </b-form-group>

    <b-form-group
      label="Project"
      description="Your GCP Project id (GOOGLE_PROJECT)"
    >
      <b-input
        id="credentials.projectId"
        v-model="credentials.projectId"
      />
    </b-form-group>
  </div>
</template>

<script>
  export default {
    name: 'CredentialsGoogle',

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
