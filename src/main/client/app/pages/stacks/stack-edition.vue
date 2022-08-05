<template>
  <div v-if="stack">
    <div class="page_controls">
      <b-button
        :disabled="!formValid"
        variant="primary"
        class="mr-1"
        @click="saveStack"
      >
        <font-awesome-icon icon="save" /> Save
      </b-button>
      <b-button
        v-if="canRunStack"
        :disabled="!formValid"
        variant="primary"
        class="mr-1"
        @click="runStack"
      >
        <font-awesome-icon icon="rocket" /> Run
      </b-button>
      <b-button
        v-if="canUpdateStack"
        :disabled="!formValid"
        variant="warning"
        class="mr-1"
        @click="runStack"
      >
        <font-awesome-icon icon="upload" /> Update
      </b-button>
      <b-button
        v-if="canStopStack"
        :disabled="!formValid"
        variant="danger"
        class="mr-1"
        @click="stopStack"
      >
        <font-awesome-icon icon="stop-circle" /> Destroy
      </b-button>
      <b-button
        v-if="canArchiveStack"
        variant="danger"
        class="float-right"
        @click="archiveStack"
      >
        <font-awesome-icon icon="archive" /> Archive
      </b-button>
      <b-button
        v-if="canUnarchiveStack"
        variant="danger"
        class="float-right"
        @click="unarchiveStack"
      >
        <font-awesome-icon icon="archive" /> Un-Archive
      </b-button>
      <b-button
        v-if="canDeleteStack"
        variant="danger"
        class="float-right mr-1"
        @click="deleteStack"
      >
        <font-awesome-icon :icon="['far', 'trash-alt']" /> Delete
      </b-button>
    </div>

    <div class="row margin_bottom_30">
      <div class="col-md-6">
        <div class="block">
          <div class="block_head">
            <h2>Stack {{ stack.name }}</h2>
            <small>{{ stack.description }}</small>
            <div class="metadata">
              <p>
                Published <b>{{ stack.createdAt | dateTimeLong }}</b> by <app-user-badge :user="stack.createdBy" />
              </p>
              <p v-if="stack.updatedBy">
                Last modified <b>{{ stack.updatedAt | dateTimeLong }}</b> by <app-user-badge :user="stack.updatedBy" />
              </p>
              <p v-if="stack.estimatedRunningCost">
                Estimated total running cost :
                <b-badge variant="info">
                  {{ stack.estimatedRunningCost }} $
                </b-badge>
              </p>
            </div>
            <h2>
              <b-badge
                v-if="stack.state === 'NEW'"
                variant="success"
                pill
                data-toggle="tooltip"
                title="Your stack is new and has not been started yet."
              >
                <i class="fas fa-star-of-life" /> new
              </b-badge>
              <b-badge
                v-if="stack.state === 'RUNNING'"
                variant="primary"
                pill
                data-toggle="tooltip"
                title="Your stack is up and running !"
              >
                <i class="far fa-check-square" /> running
              </b-badge>
              <b-badge
                v-if="stack.state === 'TO_UPDATE'"
                variant="warning"
                pill
                data-toggle="tooltip"
                title="Your stack needs an update !"
              >
                <i class="fas fa-upload" /> to update
              </b-badge>
              <b-badge
                v-if="stack.state === 'STOPPED'"
                variant="danger"
                pill
                data-toggle="tooltip"
                title="Your stack has been stopped."
              >
                <i class="fas fa-stop-circle" /> stopped
              </b-badge>

              <b-badge
                v-if="stack.state === 'ARCHIVED'"
                pill
                variant="danger"
                class="ml-1"
              >
                <font-awesome-icon icon="archive" />
                archived
              </b-badge>
            </h2>
          </div>
          <div class="block_content">
            <b-form-group label="Name">
              <b-input
                id="stack.name"
                v-model="stack.name"
                :state="stack.name !== ''"
                aria-describedby="input-live-help input-live-feedback"
                trim
              />
              <b-form-invalid-feedback id="input-live-feedback">
                This field is mandatory.
              </b-form-invalid-feedback>
              <b-form-text id="input-live-help">
                This is the name of your stack.
              </b-form-text>
            </b-form-group>
            <b-form-invalid-feedback id="input-live-feedback">
              This field is mandatory
            </b-form-invalid-feedback>
            <div class="form-group">
              <label for="description">Description</label>
              <b-input
                id="description"
                v-model="stack.description"
                type="text"
                class="form-control"
              />
            </div>
            <b-form-group label="Credentials">
              <b-select
                v-if="credentials"
                v-model="stack.credentialsId"
                :options="credentials"
              />
              <p v-if="! credentials">
                No credentials found for a module with provider {{ module.mainProvider }}
              </p>
            </b-form-group>
          </div>
        </div>
      </div>

      <div class="col-md-6">
        <app-stack-outputs
          :outputs="state.outputs || state.modules[0].outputs"
          :module-outputs="stack.module.outputs"
        />
      </div>
    </div>

    <div class="row margin_bottom_30">
      <div class="col-md-6">
        <div class="block">
          <div class="block_head">
            <h2>Module variables values</h2>
            <small>This is the configuration of your module's variables !</small>
          </div>
          <div
            v-if="stack.variables"
            class="block_content"
          >
            <app-stack-variable
              v-for="variable in editableVars"
              :key="variable.name"
              v-model="variable.value"
              v-bind="moduleVar(variable.name)"
              @valid="(isValid) => variable.isValid = isValid"
              @input="stack.state = 'TO_UPDATE'"
            />
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <app-job-history
          :jobs="jobs"
          @job-deleted="refreshJobs"
        />
      </div>
    </div>
  </div>
</template>

<script>
  import {
    destroyStack,
    getStack,
    runStack,
    saveStack,
    deleteStack,
  } from '@/shared/api/stacks-api';
  import { getState } from '@/shared/api/state-api';

  import AppStackVariable from '@/pages/stacks/stack-variable.vue';
  import AppStackOutputs from '@/pages/stacks/stack-outputs.vue';
  import AppUserBadge from '@/pages/users/user-badge.vue';
  import AppJobHistory from '@/pages/stacks/job/job-history.vue';
  import {
    displayConfirmDialog,
    displayNotification,
  } from '@/shared/services/modal-service';
  import { getJobs, planJob } from '@/shared/api/jobs-api';
  import { getCredentialsList } from '@/shared/api/credentials-api';

  export default {
    name: 'AppStackEdition',

    components: {
      AppJobHistory,
      AppStackVariable,
      AppStackOutputs,
      AppUserBadge,
    },

    props: {
      stackId: {
        type: String,
        required: true,
      },
    },

    data: () => ({
      stack: null,
      module: null,
      state: { outputs: {} },
      jobs: [],
      credentials: null,
    }),

    computed: {
      formValid() {
        return this.stack.variables.every((variable) => variable.isValid) && this.stack.name.trim() !== '';
      },
      editableVars() {
        return this.stack.variables.filter((variable) => variable.editable);
      },
      canRunStack() {
        return this.stack.state !== 'ARCHIVED' && (this.stack.state === 'NEW' || this.stack.state === 'STOPPED');
      },
      canStopStack() {
        return this.stack.state !== 'ARCHIVED' && (this.stack.state === 'RUNNING' || this.stack.state === 'TO_UPDATE');
      },
      canUpdateStack() {
        return this.canStopStack;
      },
      canArchiveStack() {
        return this.stack.state !== 'ARCHIVED';
      },
      canUnarchiveStack() {
        return this.stack.state === 'ARCHIVED';
      },
      canDeleteStack() {
        return this.stack.state === 'ARCHIVED';
      },
    },

    async created() {
      const stack = await getStack(this.stackId);

      const credentialsList = await getCredentialsList();
      this.credentials = credentialsList
        .filter((cred) => cred.provider.includes(stack.module.mainProvider))
        .map((cred) => ({ value: cred.id, text: cred.name }));

      try {
        this.state = await getState(this.stackId);
      } catch (e) {
        // unable to load state info, (stack never run), keeping default empty data
      }
      try {
        this.jobs = await getJobs(this.stackId);
      } catch (e) {
        // unable to load job info, (stack never run), keeping default empty data
      }
      stack.variables = stack.module.variables.map((variable) => ({
        ...variable,
        value: stack.variableValues[variable.name],
        isValid: true,
      }));

      this.stack = stack;
    },

    methods: {
      moduleVar(name) {
        return this.stack.module.variables.find((variable) => variable.name === name);
      },
      saveStack() {
        this.stack.variableValues = {};
        this.stack.variables.forEach((variable) => {
          this.stack.variableValues[variable.name] = variable.value;
        });
        saveStack(this.stack)
          .then(() => displayNotification(this, { variant: 'success', message: 'Stack saved.' }))
          .catch(({ message }) => {
            displayNotification(this, { variant: 'info', message: `Error saving stack: ${message}` });
          });
      },
      async runStack() {
        // ask for confirmation
        const message = 'Modifications must be saved before. Continue?';
        if (await displayConfirmDialog(this, { title: 'Run request', message })) {
          await this.saveStack();
          const { jobId } = await runStack(this.stack.id);
          await planJob(jobId);
          this.$router.push({ name: 'job', params: { jobId } });
        }
      },
      async stopStack() {
        // ask for confirmation
        const message = 'This will completely stop the stack, and destroy all created resources. Continue?';
        if (await displayConfirmDialog(this, { title: 'Stop request', message })) {
          const { jobId } = await destroyStack(this.stack.id);
          await planJob(jobId);
          this.$router.push({ name: 'job', params: { jobId } });
        }
      },
      async archiveStack() {
        // ask for confirmation
        const message = 'This will archive the stack. '
          + 'The stack will no longer be visible in the stacks list. '
          + 'This will not destroy any Terraform resources. Continue?';
        if (await displayConfirmDialog(this, { title: 'Archive Stack', message })) {
          this.stack.state = 'ARCHIVED';
          await saveStack(this.stack);
          this.$router.push({ name: 'stacks' });
        }
      },
      async unarchiveStack() {
        // ask for confirmation
        const message = 'This will un-archive the stack. '
          + 'The stack will be restored in the stacks list. '
          + 'Continue?';
        if (await displayConfirmDialog(this, { title: 'UnArchive Stack', message })) {
          this.stack.state = 'NEW';
          await saveStack(this.stack);
        }
      },
      async deleteStack() {
        // ask for confirmation
        const message = 'This will delete the stack. '
          + 'The stack and all related data will be deleted. '
          + 'This will not destroy any Terraform resources the stack may have instanciated. Continue?';
        if (await displayConfirmDialog(this, { title: 'Delete Stack', message })) {
          await deleteStack(this.stack.id);
          this.$router.push({ name: 'stacks' });
        }
      },
      async refreshJobs() {
        this.jobs = await getJobs(this.stackId);
      },
    },
  };
</script>

<style scoped>

</style>
