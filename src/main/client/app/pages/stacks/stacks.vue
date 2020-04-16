<template>
  <b-card-group columns>
    <b-card
      v-for="stack in stacks"
      :key="stack.id"
      :title="stack.name"
      :sub-title="stack.description"
    >
      <b-card-text>
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
      </b-card-text>

      <b-button
        :to="{ name: 'stack_edition', params: { stackId: stack.id }}"
        title="Edit this stack"
        variant="primary"
        class="mr-1"
      >
        <font-awesome-icon icon="edit" />
      </b-button>
    </b-card>
  </b-card-group>
</template>

<script>
  import { getStacks } from '@/shared/api/stacks-api';

  export default {
    name: 'AppStacks',
    data: () => ({
      stacks: [],
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
      },
    }),
    async created() {
      this.stacks = await getStacks();
    },
  };
</script>

<style scoped>

</style>
