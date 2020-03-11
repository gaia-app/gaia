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
            <terraform-image-input
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

        <b-form-row
          v-for="(modVar,modVarIdx) in module.variables"
          :key="modVar.name"
          class="mb-3"
        >
          <b-col cols="3">
            <b-form-group label="Name">
              <b-input
                v-model="modVar.name"
                :state="notEmpty(modVar.name)"
              />
              <b-form-invalid-feedback class="position-absolute">
                This field is mandatory
              </b-form-invalid-feedback>
            </b-form-group>
          </b-col>
          <b-col cols="3">
            <b-form-group label="Description">
              <b-input v-model="modVar.description" />
            </b-form-group>
          </b-col>
          <b-col cols="2">
            <b-form-group label="Default value">
              <b-input v-model="modVar.defaultValue" />
            </b-form-group>
          </b-col>
          <b-col
            cols="1"
            align-self="end"
            class="d-flex flex-column"
          >
            <b-form-group>
              <b-form-checkbox
                v-model="modVar.editable"
                switch
                inline
              >
                Editable
              </b-form-checkbox>
              <b-form-checkbox
                v-model="modVar.mandatory"
                switch
                inline
              >
                Mandatory
              </b-form-checkbox>
            </b-form-group>
          </b-col>
          <b-col>
            <b-form-group label="Validation Regex">
              <b-input v-model="modVar.validationRegex" />
            </b-form-group>
          </b-col>
          <b-col
            align-self="end"
            class="d-flex flex-column"
          >
            <b-form-group>
              <b-button
                variant="danger"
                @click="removeVar(modVarIdx)"
              >
                <font-awesome-icon icon="minus" />
              </b-button>
            </b-form-group>
          </b-col>
        </b-form-row>

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
  import axios from 'axios';

  import TerraformImageInput from './terraform-image-input.vue';

  export default {
    name: 'AppModule',

    components: {
      TerraformImageInput,
    },

    data: function data() {
      return {
        module: null,
        moduleId: this.$route.params.moduleId,
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
      const url = `/api/modules/${this.moduleId}`;
      const response = await axios.get(url);
      this.module = response.data;

      const responseTeams = await axios.get('/api/teams');
      this.teams = responseTeams.data;
    },

    methods: {
      notEmpty(field) {
        return typeof field !== 'undefined' && field !== null && field.trim() !== '';
      },
      async save() {
        axios.put(
          `/api/modules/${this.moduleId}`,
          this.module,
        ).then(() => {
          this.$bvToast.toast('Module saved', {
            noCloseButton: true,
            solid: true,
            variant: 'success',
            toaster: 'b-toaster-top-center',
          });
        })
          .catch((error) => {
            this.$bvToast.toast(error.message, {
              title: 'Error when saving module',
              solid: true,
              variant: 'danger',
              toaster: 'b-toaster-top-center',
            });
          });
      },
      removeVar(varIdx) {
        this.module.variables.splice(varIdx, 1);
      },
      addVar() {
        this.module.variables.push({});
      },
    },
  };
</script>
