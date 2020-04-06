<template>
  <div class="console">
    <div class="console_actions">
      <b-button
        variant="outline-light"
        @click="copyToClipboard"
      >
        <font-awesome-icon
          :icon="['far', 'copy']"
          class="icon"
        />&nbsp;copy raw
      </b-button>
      <b-button
        variant="outline-light"
        @click="goToTop"
      >
        <font-awesome-icon
          icon="angle-double-up"
          class="icon"
        />&nbsp;top
      </b-button>
      <b-button
        variant="outline-light"
        @click="goToBottom"
      >
        <font-awesome-icon
          icon="angle-double-down"
          class="icon"
        />&nbsp;bottom
      </b-button>
    </div>
    <div
      :id="'console-' + id"
      class="console_body"
      :style="cssStyle"
    >
      <!-- eslint-disable-next-line vue/no-v-html -->
      <pre><code v-html="content" /></pre>
    </div>
  </div>
</template>

<script>
  import Convert from 'ansi-to-html';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppConsole',
    props: {
      id: { type: String, required: true },
      logs: { type: String, default: null },
      cssStyle: { type: String, default: null },
    },
    data: () => ({
      content: '',
    }),
    watch: {
      logs: {
        immediate: true,
        handler(newValue) {
          // applying html colors instead of ANSI streams
          this.content = new Convert().toHtml(newValue);
        },
      },
    },
    mounted() {
      this.goToBottom();
    },
    updated() {
      this.goToBottom();
    },
    methods: {
      goToTop() {
        document.getElementById(`console-${this.id}`).scrollTop = 0;
      },
      goToBottom() {
        const consoleElt = document.getElementById(`console-${this.id}`);
        consoleElt.scrollTop = consoleElt.scrollHeight;
      },
      async copyToClipboard() {
        await navigator.clipboard.writeText(this.logs);
        displayNotification(this, { message: 'Logs copied on clipboard.', variant: 'info' });
      },
    },
  };
</script>

<style scoped>
  .console_actions {
    background: #444444;
    display: flex;
    justify-content: flex-end;
    padding: 7px 15px;
  }

  .console_actions .btn {
    color: #f1f1f1;
    font-size: 12px;
    margin-left: 10px;
  }

  .console_actions .btn:hover {
    background-color: #999a98;
  }

  .console_actions .btn .icon {
    width: 1rem;
    margin-right: 1rem;
    text-align: center;
  }

  .console_body {
    overflow-y: auto;
    padding: 1rem;
    scroll-behavior: smooth;
  }

  /*for padding-bottom with overflow*/
  .console_body:after {
    content: '';
    display: block;
    height: 1rem;
    width: 100%;
  }

  .console_body, pre, code {
    color: #f1f1f1;
    background: #222222;
  }

  pre {
    margin-bottom: 0;
  }

  code b {
    font-weight: bold;
  }
</style>
