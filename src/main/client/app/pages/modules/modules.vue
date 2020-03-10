<template>
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
        :to="'/modules/'+module.id+'/run'"
        title="Run this module"
        variant="primary"
        class="mr-1"
      >
        <font-awesome-icon icon="rocket" />
      </b-button>
    </b-card>
  </b-card-group>
</template>

<script>
  import axios from 'axios';
  import { AppCliBadge } from '@/shared/components';

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

  };
</script>
