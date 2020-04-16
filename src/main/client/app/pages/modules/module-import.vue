<template>
  <div class="row">
    <registry-import
      v-if="hasAccount('github')"
      registry="github"
      :selected="selected"
      @toggle="selected = 'github'"
    />
    <registry-import
      v-if="hasAccount('gitlab')"
      registry="gitlab"
      :selected="selected"
      @toggle="selected = 'gitlab'"
    />
    <manual-import
      :selected="selected"
      @toggle="selected = 'manual'"
    />
  </div>
</template>

<script>
  import { mapState } from 'vuex';

  import ManualImport from './import/manual-import.vue';
  import RegistryImport from './import/registry-import.vue';

  export default {
    name: 'AppModuleImport',
    components: {
      ManualImport,
      RegistryImport,
    },
    data: () => ({
      selected: 'none',
    }),
    computed: {
      ...mapState('session', {
        user: 'user',
      }),
    },
    methods: {
      hasAccount(registry) {
        return this.user.oauth2User && this.user.oauth2User.provider === registry;
      },
    },
  };
</script>

<style scoped>

</style>
