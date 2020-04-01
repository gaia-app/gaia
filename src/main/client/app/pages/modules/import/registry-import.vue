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
  import {
    getRegistriesRepositories,
    importRegistryRepository,
  } from '@/shared/api/registries-api';
  import { displayNotification } from '@/shared/services/modal-service';

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
          this.repositories = await getRegistriesRepositories(this.registry);
          this.loaded = true;
          this.isLoading = false;
        }
      },
      async importRepository() {
        this.isImporting = true;
        displayNotification(this, { message: 'Importing module from repository', variant: 'info' });
        const id = this.selectedRepository.id ? this.selectedRepository.id : this.selectedRepository.fullName;
        const module = await importRegistryRepository(this.registry, id);
        this.$router.push({ name: 'module', params: { moduleId: module.id } });
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
