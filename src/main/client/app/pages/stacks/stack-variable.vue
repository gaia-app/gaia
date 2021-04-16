<template>
  <b-form-group
    :label="name"
    :description="description"
  >
    <b-textarea
      v-if="complexType"
      v-model="editableValue"
      :state="validAndEmit().result"
      trim
      max-rows="10"
      @input="$emit('input', editableValue)"
    />
    <b-select
      v-else-if="isListRegex"
      v-model="editableValue"
      :state="validAndEmit().result"
      :options="listOptions"
      @input="$emit('input', editableValue)"
    />
    <b-input
      v-else
      v-model="editableValue"
      :state="validAndEmit().result"
      trim
      @input="$emit('input', editableValue)"
    />
    <b-form-invalid-feedback>{{ valid().message }}</b-form-invalid-feedback>
  </b-form-group>
</template>

<script>
  export default {
    name: 'StackVariable',
    props: {
      name: {
        type: String,
        required: true,
      },
      type: {
        type: String,
        required: true,
      },
      description: {
        type: String,
        required: true,
      },
      value: {
        type: String,
        required: true,
      },
      validationRegex: {
        type: String,
        default: '',
      },
      mandatory: {
        type: Boolean,
        required: true,
      },
    },

    data() {
      return {
        editableValue: this.value,
      };
    },

    computed: {
      isListRegex() {
        const listRegex = /^\(([^|]*)(\|([^|]*))*\)$/;
        return listRegex.test(this.validationRegex);
      },
      listOptions() {
        const listExtractRegex = /[^()|]+/g;
        // extracting the values from the regex
        // also adding empty string to the result to be able to not select anything !
        return ['', ...this.validationRegex.match(listExtractRegex)];
      },
      complexType() {
        return this.type !== 'string' && this.type !== 'number';
      },
    },

    methods: {
      validAndEmit() {
        const validationResult = this.valid();
        this.$emit('valid', validationResult.result !== false);
        return validationResult;
      },
      valid() {
        // no validation needed if variable is not mandatory and if no validation regex
        if (!this.mandatory && !this.validationRegex) {
          return { result: null };
        }

        // checking for empty values
        if (this.editableValue === null
          || typeof this.editableValue === 'undefined'
          || this.editableValue.trim().length === 0) {
          // mandatory variables cannot be empty
          if (this.mandatory) {
            return { result: false, message: 'This field is mandatory' };
          }
          // stop checks is fields is empty
          return { result: null };
        }

        // check regex if defined
        if (this.validationRegex) {
          if (!new RegExp(this.validationRegex).test(this.editableValue)) {
            return { result: false, message: `This field must match the regex ${this.validationRegex}` };
          }
        }

        return { result: true };
      },
    },
  };
</script>
