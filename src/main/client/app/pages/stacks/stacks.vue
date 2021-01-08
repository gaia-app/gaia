<template>
  <div>
    <div class="page_controls">
      <b-form-checkbox
        v-model="showArchived"
        switch
      >
        Show archived stacks
      </b-form-checkbox>
    </div>
    <b-card-group columns>
      <b-card
        v-for="stack in visibleStacks"
        :key="stack.id"
        :sub-title="stack.description"
        :header-class="stack.module.mainProvider || 'unknown'"
        no-body
      >
        <template v-slot:header>
          <h1>{{ stack.name }}</h1>
          <app-provider-logo
            :provider="stack.module.mainProvider || 'unknown'"
            size="40px"
          />
        </template>

        <b-card-body>
          <p>{{ stack.description }}</p>
        </b-card-body>

        <b-card-footer>
          <b-badge
            :id="'badge-' + stack.id"
            pill
            :variant="states[stack.state].variant"
          >
            <font-awesome-icon :icon="states[stack.state].icon" />&nbsp;{{ states[stack.state].text }}
          </b-badge>
          <b-tooltip :target="'badge-' + stack.id">
            {{ states[stack.state].tooltip }}
          </b-tooltip>
        </b-card-footer>

        <b-card-footer>
          <b-button
            :to="{ name: 'stack_edition', params: { stackId: stack.id }}"
            title="Edit this stack"
            variant="primary"
            class="mr-1"
          >
            <font-awesome-icon icon="edit" />
          </b-button>
        </b-card-footer>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
  import { getStacks } from '@/shared/api/stacks-api';
  import { AppProviderLogo } from '@/shared/components';

  export default {
    name: 'AppStacks',
    components: {
      AppProviderLogo,
    },
    data: () => ({
      stacks: [],
      showArchived: false,
      states: {
        NEW: {
          variant: 'success',
          tooltip: 'Your stack is new and has not been started yet.',
          icon: 'star-of-life',
          text: 'new',
        },
        RUNNING: {
          variant: 'primary',
          tooltip: 'Your stack is up and running !',
          icon: ['far', 'check-square'],
          text: 'running',
        },
        TO_UPDATE: {
          variant: 'warning',
          tooltip: 'Your stack needs an update !',
          icon: 'upload',
          text: 'to update',
        },
        STOPPED: {
          variant: 'danger',
          tooltip: 'Your stack has been stopped.',
          icon: 'stop-circle',
          text: 'stopped',
        },
        ARCHIVED: {
          variant: 'danger',
          tooltip: 'Your stack is archived.',
          icon: 'archive',
          text: 'archived',
        },
      },
    }),
    computed: {
      visibleStacks() {
        if (this.showArchived) {
          return this.stacks;
        }
        return this.stacks.filter((stack) => stack.state !== 'ARCHIVED');
      },
    },
    async created() {
      this.stacks = await getStacks();
    },
  };
</script>

<style scoped>
p {
  margin-bottom: 0;
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
</style>
