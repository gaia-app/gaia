<template>
  <div class="col-md-4">
    <b-card no-body>
      <b-button
        size="lg"
        variant="outline-secondary"
        :class="'btn-' + registry"
        squared
        @click="toggle"
      >
        <font-awesome-icon :icon="['fab', registry]" /> from {{ registry }}
      </b-button>

      <b-card v-if="selected === registry">
        <b-card-text>
          <label for="repoSelect">Select your {{ registry }} repository</label>
          <vue-multiselect
            id="repoSelect"
            v-model="selectedRepository"
            placeholder="Type to search"
            label="fullName"
            :options="repositories"
            :loading="isLoading"
            @open="search"
          >
            <span slot="noResult">Oops! No repository found. </span>
          </vue-multiselect>
        </b-card-text>

        <b-button
          :disabled="! selectedRepository"
          variant="primary"
          @click="importRepository"
        >
          Import this repository !
        </b-button>
      </b-card>

      <template v-slot:footer>
        <em>Import an existing module code from existing {{ registry }} repository</em>
      </template>
    </b-card>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: 'RegistryImport',
    props: {
      registry: {
        type: String,
        required: true,
      },
      selected: {
        type: String,
        required: true,
      },
    },
    data: () => (
      {
        selectedRepository: null,
        repositories: [],
        isLoading: false,
        loaded: false,
        isImporting: false,
      }
    ),
    methods: {
      toggle() {
        this.$emit('toggle');
      },
      async search() {
        if (!this.loaded) {
          this.isLoading = true;
          const response = await axios.get(`/api/registries/${this.registry}/repositories`);
          this.repositories = response.data;
          this.loaded = true;
          this.isLoading = false;
        }
      },
      async importRepository() {
        this.isImporting = true;

        this.$bvToast.toast('Importing module from repository', {
          noCloseButton: true,
          solid: true,
          variant: 'info',
          toaster: 'b-toaster-top-center',
        });

        const id = this.selectedRepository.id ? this.selectedRepository.id : this.selectedRepository.fullName;
        const response = await axios.get(`/api/registries/${this.registry}/repositories/${id}/import`);
        const module = response.data;
        await this.$router.push({ name: 'module', params: { id: module.id } });
      },
    },
  };
</script>

<style>
  .btn-gitlab {
    color: #fca326;
  }

  .btn-gitlab:hover {
    background-color: #fca326;
    border-color: #fca326;
    color: #fff;
  }

  .btn-github {
    color: #333;
  }

  .btn-github:hover {
    background-color: #333;
    border-color: #333;
    color: #fff;
  }
</style>
