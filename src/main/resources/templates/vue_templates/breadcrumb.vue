<template id="breadcrumb">
    <h2 class="navigation">
        <b-breadcrumb :items="items"></b-breadcrumb>
    </h2>
</template>

<script>
    let menu = function (stackId) {
        return {
            index: [{text: 'Dashboard'}],
            modules: [{text: 'Modules'}],
            new_stack: [{text: 'Modules', href: '/modules'}, {text: 'Stack creation'}],
            module: [{text: 'Modules', href: '/modules'}, {text: 'Module edition'}],
            module_description: [{text: 'Modules', href: '/modules'}, {text: 'Module description'},],
            module_import: [{text: 'Modules', href: '/modules'}, {text: 'Module import'},],
            stacks: [{text: 'Stacks'}],
            stack: [{text: 'Stacks', href: '/stacks'}, {text: 'Stack'}],
            users: [{text: 'Users'}],
            job: [{text: 'Stacks', href: '/stacks'}, {text: 'Stack', href: `/stacks/${stackId}`}, {text: 'Job'}],
            settings: [{text: 'Settings'}],
        }
    };

    Vue.component('breadcrumb', {
        template: '#breadcrumb',
        data: () => ({
            items: [],
        }),
        props: ['page', 'stackId'],
        created: function () {
            this.items = menu(this.stackId)[this.page];
        }
    });
</script>