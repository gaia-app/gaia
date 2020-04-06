<template>
  <div
    v-if="module"
    class="block"
  >
    <div class="block_head">
      <h2>Module {{ module.name }}</h2>
    </div>

    <div class="block_content">
      <form>
        <b-form-row>
          <b-col>
            <b-form-group
              label="Name"
              description="The name of your module"
            >
              <b-input
                id="module.name"
                v-model="module.name"
                :state="notEmpty(module.name)"
              />
              <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
            </b-form-group>
          </b-col>
          <b-col :md="isTerraformImageOverride ? '5' : '3'">
            <app-terraform-image-input
              :image="module.terraformImage"
              @form-status="isTerraformImageValid = $event"
              @override-status="isTerraformImageOverride = $event"
            />
          </b-col>
        </b-form-row>

        <b-form-group
          label="Description"
          description="The description of your module"
        >
          <b-input
            id="module.description"
            v-model="module.description"
          />
        </b-form-group>

        <b-form-row>
          <b-col cols="3">
            <b-form-group label="Estimated monthly cost">
              <b-input-group append="$">
                <b-form-input v-model="module.estimatedMonthlyCost" />
              </b-input-group>
            </b-form-group>
          </b-col>
        </b-form-row>

        <b-form-group label="Description of estimated monthly cost">
          <b-input-group>
            <b-input-group-text slot="append">
              <font-awesome-icon :icon="['fab', 'markdown']" />
            </b-input-group-text>
            <b-form-textarea v-model="module.estimatedMonthlyCostDescription" />
          </b-input-group>
        </b-form-group>

        <b-form-row>
          <b-col>
            <b-form-group
              label="Git Repository URL"
              description="The URL of the module's git repository"
            >
              <b-input
                v-model="module.gitRepositoryUrl"
                :state="notEmpty(module.gitRepositoryUrl)"
              />
              <b-form-invalid-feedback>This field is mandatory</b-form-invalid-feedback>
            </b-form-group>
          </b-col>
          <b-col>
            <b-form-group
              label="Git repository directory"
              description="The sub-directory of the module's code inside the repository (leave empty if root)"
            >
              <b-input v-model="module.directory" />
            </b-form-group>
          </b-col>
        </b-form-row>

        <h2>Authorized Teams</h2>

        <b-form-row>
          <b-col cols="6">
            <b-form-group>
              <vue-multiselect
                v-model="module.authorizedTeams"
                :multiple="true"
                label="id"
                track-by="id"
                searchable
                placeholder="Select teams"
                :options="teams"
              />
            </b-form-group>
          </b-col>
        </b-form-row>

        <h2>
          Variables
          <b-button
            variant="success"
            @click="addVar()"
          >
            <font-awesome-icon icon="plus" />
          </b-button>
        </h2>

        <app-module-variable
          v-for="(modVar, idx) in module.variables"
          :key="idx"
          :variable="modVar"
          @removeVar="removeVar"
        />

        <b-button
          variant="primary"
          :disabled="!formValid"
          @click="save"
        >
          <font-awesome-icon icon="save" /> Save
        </b-button>
      </form>
    </div>
  </div>
</template>

<script>
  import AppModuleVariable from '@/pages/modules/module-variable.vue';
  import AppTerraformImageInput from '@/pages/modules/terraform-image-input.vue';
  import {
    getModule,
    updateModule,
  } from '@/shared/api/modules-api';
  import { getTeams } from '@/shared/api/teams-api';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppModule',

    components: {
      AppModuleVariable,
      AppTerraformImageInput,
    },

    props: {
      moduleId: {
        type: String,
        required: true,
      },
    },

    data: function data() {
      return {
        module: null,
        isTerraformImageValid: null,
        isTerraformImageOverride: null,
        teams: [],
      };
    },

    computed: {
      formValid() {
        return [this.module.name, this.module.gitRepositoryUrl].every(this.notEmpty)
          && this.module.variables.map((variable) => variable.name).every(this.notEmpty)
          && this.isTerraformImageValid;
      },
    },

    async created() {
      this.module = await getModule(this.moduleId);
      this.teams = await getTeams();
    },

    methods: {
      notEmpty(field) {
        return typeof field !== 'undefined' && field !== null && field.trim() !== '';
      },
      async save() {
        await updateModule(this.module)
          .then(() => displayNotification(this, { message: 'Module saved', variant: 'success' }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
      removeVar(variable) {
        this.module.variables.splice(this.module.variables.findIndex((v) => v.name === variable.name), 1);
      },
      addVar() {
        this.module.variables.push({});
      },
    },
  };
</script>
