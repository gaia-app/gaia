<template>
  <b-form class="form-row align-items-end">
    <b-form-group
      label="Name"
      :label-for="nameId"
      class="col-md-4"
    >
      <b-form-input
        :id="nameId"
        v-model="variable.name"
        name="name"
      />
    </b-form-group>

    <b-form-group
      label="Value"
      :label-for="valueId"
      class="col-md-4"
    >
      <b-form-input
        :id="valueId"
        v-model="variable.value"
        name="value"
      />
    </b-form-group>

    <b-form-group class="col-md-1">
      <b-button
        variant="danger"
        class="form-control"
        @click="deleteVariable"
      >
        -
      </b-button>
    </b-form-group>
  </b-form>
</template>

<script>
  export default {
    name: 'AppSettingsVariable',
    props: {
      id: { type: [String, Number], required: true },
      variable: { type: Object, required: true },
    },
    computed: {
      nameId() {
        return `variable-name-${this.id}`;
      },
      valueId() {
        return `variable-value-${this.id}`;
      },
    },
    methods: {
      deleteVariable() {
        this.$emit('delete');
      },
    },
  };
</script>
