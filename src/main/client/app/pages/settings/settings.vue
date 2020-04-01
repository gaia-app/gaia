<template>
  <div class="block">
    <div class="block_content">
      <b-form-group
        label="External url"
        label-for="externalUrl"
        description="This is gaia's external URL. This is needed to allow terraform backend support."
      >
        <b-form-input
          id="externalUrl"
          v-model="settings.externalUrl"
        />
      </b-form-group>

      <div class="heading1">
        <h2>
          Environment Variables
          <b-button
            variant="success"
            @click="addVariable()"
          >
            +
          </b-button>
        </h2>
        <small>This is the environment variables which are fed to the runner.</small>
      </div>
      <app-settings-variable
        v-for="(envVar, index) in settings.envVars"
        :id="index"
        :key="index"
        :variable="envVar"
        @delete="removeVariable(index)"
      />

      <b-button
        variant="primary"
        @click="save()"
      >
        <font-awesome-icon icon="save" />
        Save
      </b-button>
    </div>
  </div>
</template>

<script>
  import AppSettingsVariable from '@/pages/settings/settings-variable.vue';
  import {
    getSettings,
    saveSettings,
  } from '@/shared/api/settings-api';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppSettings',
    components: { AppSettingsVariable },
    data: () => ({
      settings: { externalUrl: '', envVars: [] },
    }),
    async created() {
      this.settings = await getSettings();
    },
    methods: {
      addVariable() {
        this.settings.envVars.push({});
      },
      removeVariable(index) {
        this.settings.envVars.splice(index, 1);
      },
      save() {
        saveSettings(this.settings)
          .then(() => displayNotification(this, { message: 'Settings saved', variant: 'success' }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
    },
  };
</script>

<style scoped>

</style>
