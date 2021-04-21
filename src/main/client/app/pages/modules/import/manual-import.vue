<template>
  <div class="col-md-4">
    <b-card no-body>
      <b-button
        size="lg"
        variant="outline-danger"
        squared
        @click="toggle"
      >
        <font-awesome-icon :icon="['fab', 'superpowers']" /> manually
      </b-button>

      <b-card v-if="selected === 'manual'">
        <b-card-text>
          <b-form-group
            label="Enter the module name"
            label-for="module-name"
          >
            <b-form-input
              id="module-name"
              v-model="moduleName"
              trim
            />
          </b-form-group>
        </b-card-text>

        <b-button
          :disabled="! moduleName"
          href="#"
          variant="primary"
          @click="createModule"
        >
          Import manually
        </b-button>
      </b-card>

      <template v-slot:footer>
        <em>Import a module manually (only for users with superpowers)</em>
      </template>
    </b-card>
  </div>
</template>

<script>
  import { createModule } from '@/shared/api/modules-api';

  export default {
    name: 'ManualImport',

    props: {
      selected: {
        type: String,
        required: true,
      },
    },
    data: () => ({ moduleName: null }),
    methods: {
      toggle() {
        this.$emit('toggle');
      },
      async createModule() {
        let module = {
          name: this.moduleName,
        };
        module = await createModule(module);
        this.$router.push({ name: 'module', params: { moduleId: module.id } });
      },
    },
  };
</script>
