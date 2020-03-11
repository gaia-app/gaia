<template id="readme">
  <b-container fluid>
    <b-spinner
      v-if="!loaded"
      label="Loading..."
      class="m-5"
      style="height: 2.5rem; width: 2.5rem"
    />
    <div
      v-if="loaded"
      class="markdown-body"
    >
      <!-- eslint-disable-next-line vue/no-v-html -->
      <div v-html="content" />
    </div>
  </b-container>
</template>

<script>
  import axios from 'axios';

  import marked from 'marked';

  export default {
    name: 'AppReadme',

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
      const result = await axios.get(`/api/modules/${this.moduleId}/readme`);
      this.content = marked(result.data);
      this.loaded = true;
    },
  };
</script>
