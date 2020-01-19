<template id="module-import-template">
    <div class="col-md-4">
        <b-card no-body>
            <b-button size="lg" variant="outline-secondary" :class="'btn-' + registry" squared @click="toggle">
                <i :class="'fab fa-' + registry"></i> from {{registry}}
            </b-button>

            <b-card v-if="selected === registry">
                <b-card-text>
                    <label for="repoSelect">Select your {{registry}} repository</label>
                    <vue-multiselect
                            v-model="selectedRepository"
                            id="repoSelect"
                            placeholder="Type to search"
                            label="fullName"
                            :options="repositories"
                            :loading="isLoading"
                            @open="search">
                        <span slot="noResult">Oops! No repository found. </span>
                    </vue-multiselect>
                </b-card-text>

                <b-button :disabled="! selectedRepository" href="#" variant="primary" @click="importRepository">Import this repository !</b-button>
            </b-card>

            <template v-slot:footer>
                <em>Import an existing module code from existing {{registry}} repository</em>
            </template>
        </b-card>
    </div>
</template>

<script>
Vue.component('module-import', {
    template: "#module-import-template",
    props: ["registry", "selected"],
    data: () => ({
            selectedRepository: null,
            repositories: [],
            isLoading: false,
            loaded: false,
            isImporting: false
        }
    ),
    methods: {
        toggle: function(){
            this.$emit("toggle");
        },
        search(query) {
            if(! this.loaded){
                this.isLoading = true;
                $.get(`/api/registries/${this.registry}/repositories`).then(response => {
                    this.repositories = response;
                    this.loaded = true;
                    this.isLoading = false;
                })
            }
        },
        importRepository: function() {
            this.isImporting = true;
            const id = this.selectedRepository.id ? this.selectedRepository.id : this.selectedRepository.fullName;
            $.get(`/api/registries/${this.registry}/repositories/${id}/import`).then(module => {
                window.location = `/modules/${module.id}`
            })
        }
    }
});
</script>

<style>
.btn-gitlab {
    color: #fca326;
}

.btn-gitlab:hover {
    background-color: #fca326;
    border-color: #fca326;
    color: #fff;
}

.btn-github {
    color: #333;
}

.btn-github:hover {
    background-color: #333;
    border-color: #333;
    color: #fff;
}
</style>
