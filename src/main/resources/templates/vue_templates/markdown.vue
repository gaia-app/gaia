<template id="markdown">
    <div class="markdown-body">
        <div v-html="convertedContent"></div>
    </div>
</template>

<script>
    Vue.component('markdown', {
        template: '#markdown',
        props: ['content'],
        computed: {
            convertedContent: function() {
                if(typeof this.content === "undefined"){
                    return "";
                }
                return marked(this.content);
            }
        }
    });
</script>