<template>
  <div>
    <b-button
      :to="{ name: 'module_import' }"
      title="Import Module"
      variant="success"
      class="mb-4"
    >
      <font-awesome-icon icon="save" /> Import Module
    </b-button>

    <div class="card-layout">
      <b-card
        v-for="module in modules"
        :key="module.id"
        :header-class="module.mainProvider || 'unknown'"
        class="moduleCard"
        no-body
      >
        <template v-slot:header>
          <h1>{{ module.name }}</h1>
          <app-provider-logo
            :provider="module.mainProvider"
            size="40px"
          />
        </template>

        <b-card-body>
          <p>{{ module.description }}</p>
        </b-card-body>
        <b-card-footer class="metadata">
          <app-cli-badge
            class="app-cli-badge"
            :cli="module.terraformImage"
            badge-style="flat-square"
          />
          <p v-if="module.estimatedMonthlyCost">
            <font-awesome-icon icon="dollar-sign" /> {{ module.estimatedMonthlyCost }} / month
          </p>
          <p>
            <font-awesome-icon icon="user" /> {{ module.moduleMetadata.createdBy.username }}
          </p>
        </b-card-footer>

        <b-card-footer>
          <b-button
            :to="{ name: 'module', params: { moduleId: module.id }}"
            title="Edit this module"
            variant="primary"
            class="mr-1"
          >
            <font-awesome-icon icon="edit" />
          </b-button>

          <b-button
            :to="{ name: 'module_description', params: { moduleId: module.id }}"
            title="Detail of this module"
            variant="primary"
            class="mr-1"
          >
            <font-awesome-icon icon="info" />
          </b-button>

          <b-button
            title="Run this module"
            variant="primary"
            class="mr-1"
            @click="createStack(module.id)"
          >
            <font-awesome-icon icon="rocket" />
          </b-button>
        </b-card-footer>
      </b-card>
    </div>
  </div>
</template>

<script>
  import { getModules } from '@/shared/api/modules-api';

  import { AppCliBadge, AppProviderLogo } from '@/shared/components';

  export default {
    name: 'AppModules',

    components: {
      AppCliBadge,
      AppProviderLogo,
    },

    data: function data() {
      return {
        modules: [],
      };
    },

    async created() {
      this.modules = await getModules();
    },

    methods: {
      createStack(moduleId) {
        this.$router.push({
          name: 'stack_creation',
          params: {
            moduleId,
          },
        });
      },
    },

  };
</script>

<style scoped>
  p {
    margin-bottom: 0;
  }

  .card-layout {
    display: flex;
    flex-wrap: wrap;
  }

  .card {
    width: 30%;
    margin-right: 1rem;
    margin-bottom: 1rem;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: white;
  }

  .card-header h1 {
    color: white;
  }

  .card-header.unknown {
    background-color: #4f55e3;
  }

  .card-header.google {
    background-color: #2f6fd8;
  }

  .card-header.docker {
    background-color: #2496ed;
  }

  .card-header.aws {
    background-color: #ea8c00;
  }

  .card-header.azurerm {
    background-color: #007cc1;
  }

  .metadata {
    display: flex;
    justify-content: space-between;
  }

  .app-cli-badge {
    height: 100%;
    margin-right: 0.75rem;
  }

</style>
