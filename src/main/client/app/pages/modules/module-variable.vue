<template>
  <b-form-row class="mb-3">
    <b-col cols="3">
      <b-form-group label="Name">
        <b-input
          v-model="variable.name"
          :state="notEmpty(variable.name)"
        />
        <b-form-invalid-feedback class="position-absolute">
          This field is mandatory
        </b-form-invalid-feedback>
      </b-form-group>
    </b-col>
    <b-col cols="3">
      <b-form-group label="Description">
        <b-input v-model="variable.description" />
      </b-form-group>
    </b-col>
    <b-col cols="2">
      <b-form-group label="Default value">
        <b-input v-model="variable.defaultValue" />
      </b-form-group>
    </b-col>
    <b-col
      cols="1"
      align-self="end"
      class="d-flex flex-column"
    >
      <b-form-group>
        <b-form-checkbox
          v-model="variable.editable"
          switch
          inline
        >
          Editable
        </b-form-checkbox>
        <b-form-checkbox
          v-model="variable.mandatory"
          switch
          inline
        >
          Mandatory
        </b-form-checkbox>
      </b-form-group>
    </b-col>
    <b-col>
      <b-form-group label="Validation Regex">
        <b-input v-model="variable.validationRegex" />
      </b-form-group>
    </b-col>
    <b-col
      align-self="end"
      class="d-flex flex-column"
    >
      <b-form-group>
        <b-button
          variant="danger"
          @click="$emit('removeVar', variable)"
        >
          <font-awesome-icon icon="minus" />
        </b-button>
      </b-form-group>
    </b-col>
  </b-form-row>
</template>

<script>
  export default {

    name: 'AppModuleVariable',

    props: {
      variable: {
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
