<template id="console-template">
    <div class="console">
        <div class="console_actions">
            <a href="#" class="btn" @click="copyToClipboard"><i class="far fa-copy"></i> copy raw</a>
            <a href="#" class="btn" @click="goToTop"><i class="fas fa-angle-double-up"></i> top</a>
            <a href="#" class="btn" @click="goToBottom"><i class="fas fa-angle-double-down"></i> bottom</a>
        </div>
        <div :id="id" class="console_body" :style="cssStyle">
            <pre><code v-html="content"></code></pre>
        </div>
    </div>
</template>

<script>
    Vue.component('console', {
        template: '#console-template',
        data: () => ({
            content: '',
        }),
        props: ['id', 'cssStyle', 'logs'],
        watch: {
            logs: {
                immediate: true,
                handler(newValue) {
                    // applying html colors instead of ANSI streams
                    this.content = new Filter().toHtml(newValue);
                }
            }
        },
        mounted: function () {
            this.goToBottom();
        },
        updated: function () {
            this.goToBottom();
        },
        methods: {
            goToTop: function () {
                $(`#${this.id}`).animate({scrollTop: 0}, 500);
            },
            goToBottom: function () {
                const consoleElt = $(`#${this.id}`);
                consoleElt.animate({scrollTop: consoleElt[0].scrollHeight}, 500);
            },
            copyToClipboard: function () {
                const temp = $("<textarea>");
                $("body").append(temp);
                temp.val(this.logs).select();
                document.execCommand("copy");
                temp.remove();

                Messenger().post({
                    id: `message-${this.id}`,
                    hideAfter: 2,
                    type: "info",
                    message: "Logs copied on clipboard."
                });
            },
        }
    });
</script>

<style scoped>
    .console_actions {
        background: #444444;
        display: flex;
        justify-content: flex-end;
        padding: 7px 15px;
    }

    .console_actions .btn {
        border: 1px solid #f1f1f1;
        color: #f1f1f1;
        font-size: 12px;
        margin-left: 10px;
    }

    .console_actions .btn:hover {
        background-color: #999a98;
    }

    .console_body {
        overflow-y: auto;
        padding: 1rem;
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