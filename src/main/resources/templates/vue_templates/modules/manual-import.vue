<template id="manual-import-template">
    <div class="col-md-4">
        <b-card no-body>
            <b-button size="lg" variant="outline-danger" squared @click="toggle">
                <i class="fab fa-superpowers"></i> manually
            </b-button>

            <b-card v-if="selected === 'manual'">
                <b-card-text>
                    <b-form-group
                            label="Enter the module name"
                            label-for="module-name">
                        <b-form-input id="module-name" v-model="moduleName" trim></b-form-input>
                    </b-form-group>
                </b-card-text>

                <b-button :disabled="! moduleName" href="#" variant="primary" @click="createModule">Import manually</b-button>
            </b-card>

            <template v-slot:footer>
                <em>Import a module manually (only for users with superpowers)</em>
            </template>
        </b-card>
    </div>
</template>

<script>
Vue.component('manual-import', {
    template: "#manual-import-template",
    props: ["selected"],
    data: () => ({
            moduleName: null,
        }
    ),
    methods: {
        toggle: function(){
            this.$emit("toggle");
        },
        createModule: function(){
            let module = {
                name: this.moduleName
            };
            $.ajax({
                url: `/api/modules`,
                data: JSON.stringify(module),
                contentType: "application/json",
                method: "POST"
            }).then(module => {
                window.location = `/modules/${module.id}`
            });
        }
    }
});
</script>