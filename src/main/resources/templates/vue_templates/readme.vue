<template id="readme">
    <div>
        <b-spinner label="Loading..." class="m-5" style="height: 2.5rem; width: 2.5rem" v-if="!loaded"></b-spinner>
        <div class="markdown-body" v-if="loaded">
            <div v-html="content"></div>
        </div>
    </div>
</template>

<script>
    Vue.component('readme', {
        template: '#readme',
        data: () => ({
            content: '',
            loaded: false,
        }),
        props: ['moduleId'],
        created: function () {
            $.get(`/modules/${this.moduleId}/readme`)
                .then($.get)
                .then(data => {
                    this.content = marked(data);
                    this.loaded = true;
                })
        }
    });
</script>