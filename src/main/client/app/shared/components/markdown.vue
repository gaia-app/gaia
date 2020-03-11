<template>
  <div class="markdown-body">
    <!-- eslint-disable-next-line vue/no-v-html -->
    <div v-html="convertedContent" />
  </div>
</template>

<script>
  import marked from 'marked';

  export default {
    name: 'Markdown',

    props: {
      content: {
        type: String,
        required: true,
      },
    },

    computed: {
      convertedContent() {
        if (typeof this.content === 'undefined') {
          return '';
        }
        return marked(this.content);
      },
    },
  };
</script>
