<template>
  <b-container fluid>
    <b-spinner
      v-if="!loaded"
      label="Loading..."
      class="m-5"
      style="height: 2.5rem; width: 2.5rem"
    />
    <app-markdown
      v-if="loaded"
      :content="content"
    />
  </b-container>
</template>

<script>
  import { AppMarkdown } from '@/shared/components';

  import { getModuleReadme } from '@/shared/api/modules-api';

  export default {
    name: 'AppReadme',

    components: {
      AppMarkdown,
    },

    props: {
      moduleId: {
        type: String,
        required: true,
      },
    },

    data: () => ({
      content: '',
      loaded: false,
    }),

    async created() {
      try {
        this.content = await getModuleReadme(this.moduleId);
      } catch (e) {
        this.content = '# No readme file for this module';
      }
      this.loaded = true;
    },
  };
</script>
