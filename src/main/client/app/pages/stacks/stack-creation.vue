<template>
  <div
    v-if="module"
    class="block"
  >
    <form-wizard
      title="Run a new stack"
      subtitle="Follow the steps to start a new stack"
      color="#00ab94"
      error-color="#dc3545"
    >
      <tab-content
        title="Stack"
        :before-change="checkStackNameValidation"
      >
        <h4>{{ module.name }}</h4>
        <p>{{ module.description }}</p>

        <hr>

        <div class="form-group">
          <b-form-group
            label="Name"
            description="The name of your stack"
          >
            <b-input
              v-model="stack.name"
              :state="stackNameValid"
              autofocus
            />
            <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
          </b-form-group>
          <b-form-group
            label="Description"
            description="The description of your stack"
          >
            <b-input v-model="stack.description" />
          </b-form-group>
        </div>
      </tab-content>
      <tab-content
        title="Variables"
        :before-change="checkStackVariablesValidation"
      >
        <h2>Input variables</h2>
        <app-stack-variable
          v-for="variable in stack.variables"
          :key="variable.name"
          v-model="variable.value"
          v-bind="variable"
          @valid="(isValid) => variable.isValid = isValid"
        />
      </tab-content>
      <tab-content title="Credentials">
        <h2>Select credentials for your stack</h2>
        <b-form-group>
          <b-select
            v-if="credentials"
            v-model="stack.credentialsId"
            :options="credentials"
          />
          <p v-if="! credentials">
            No credentials found for a module with provider {{ module.mainProvider }}
          </p>
        </b-form-group>
      </tab-content>
      <tab-content title="Start">
        <h2>Run</h2>
        <p>Save your stack or run it !</p>
      </tab-content>

      <!-- customizing save button -->
      <template
        slot="finish"
        slot-scope="props"
      >
        <wizard-button
          v-if="props.isLastStep"
          :style="props.fillButtonStyle"
          class="wizard-footer-right finish-button"
          @click.native="save"
        >
          <font-awesome-icon icon="save" /> Save
        </wizard-button>
      </template>

      <!-- add run button -->
      <template
        slot="custom-buttons-right"
        slot-scope="props"
      >
        <wizard-button
          v-if="props.isLastStep"
          :style="props.fillButtonStyle"
          class="wizard-footer-right finish-button ml-3"
          @click.native="saveAndRun"
        >
          <font-awesome-icon icon="rocket" /> Save and run
        </wizard-button>
      </template>
    </form-wizard>
  </div>
</template>

<script>
  import { getModule } from '@/shared/api/modules-api';
  import { createStack, runStack } from '@/shared/api/stacks-api';
  import { getCredentialsList } from '@/shared/api/credentials-api';

  import AppStackVariable from './stack-variable.vue';

  export default {
    name: 'AppStackCreation',

    components: {
      AppStackVariable,
    },

    props: {
      moduleId: {
        type: String,
        required: true,
      },
    },

    data() {
      return {
        module: null,
        stack: null,
        stacksVariablesValidated: false,
        credentials: null,
      };
    },

    computed: {
      stackNameValid() {
        return typeof this.stack.name !== 'undefined' && this.stack.name !== '';
      },
    },

    async created() {
      this.stack = {
        module: {
          id: this.moduleId,
        },
      };

      this.module = await getModule(this.moduleId);
      const credentialsList = await getCredentialsList();
      this.credentials = credentialsList
        .filter((cred) => cred.provider.includes(this.module.mainProvider))
        .map((cred) => ({ value: cred.id, text: cred.name }));

      this.stack.variableValues = {};
      this.stack.variables = this.module.variables.map((variable) => ({
        ...variable,
        value: variable.defaultValue || '',
        isValid: true,
      }));
    },

    methods: {
      checkStackNameValidation() {
        return this.stackNameValid;
      },
      checkStackVariablesValidation() {
        return this.stack.variables.every((variable) => variable.isValid);
      },
      stackVariablesMgmt() {
        this.stack.variableValues = {};
        this.stack.variables.forEach((variable) => {
          this.stack.variableValues[variable.name] = variable.value;
        });
      },
      async saveStack() {
        this.stackVariablesMgmt();
        return createStack(this.stack);
      },
      async save() {
        const { id: stackId } = await this.saveStack();
        this.$router.push({ name: 'stack_edition', params: { stackId } });
      },
      async saveAndRun() {
        const { id: stackId } = await this.saveStack();
        const { jobId } = await runStack(stackId);
        this.$router.push({ name: 'job', params: { stackId, jobId } });
      },
    },
  };
</script>

<style scoped>

</style>
