<template>
  <div>
    <b-button
      :to="'/modules/import'"
      title="Import Module"
      variant="success"
      class="mb-4"
    >
      <font-awesome-icon icon="save" /> Import Module
    </b-button>

    <b-card-group columns>
      <b-card
        v-for="module in modules"
        :key="module.id"
        :title="module.name"
      >
        <b-card-text>
          <app-cli-badge
            :cli="module.terraformImage"
            badge-style="flat-square"
            style="margin-bottom: .75rem"
          />
          <p>{{ module.description }}</p>

          <p v-if="module.estimatedMonthlyCost">
            Estimated monthly cost :
            <b-badge variant="info">
              {{ module.estimatedMonthlyCost }} $
            </b-badge>
          </p>
        </b-card-text>

        <b-button
          :to="'/modules/'+module.id"
          title="Edit this module"
          variant="primary"
          class="mr-1"
        >
          <font-awesome-icon icon="edit" />
        </b-button>

        <b-button
          :to="'/modules/'+module.id+'/description'"
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
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
  import axios from 'axios';
  import { AppCliBadge } from '@/shared/components';

  import { v4 as uuidv4 } from 'uuid';

  export default {
    name: 'AppModules',

    components: {
      AppCliBadge,
    },

    data: function data() {
      return {
        modules: [],
      };
    },

    async created() {
      const response = await axios.get('/api/modules');
      this.modules = response.data;
    },

    methods: {
      async createStack(moduleId) {
        await this.$router.push({
          name: 'stack_creation',
          params: {
            stackId: uuidv4(),
            moduleId,
          },
        });
      },
    },

  };
</script>
