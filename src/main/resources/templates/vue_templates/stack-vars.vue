<template id="stack-vars">
    <div>
        <div class="form-row align-items-end" v-for="(modVar,modVarIdx) in editableVars" v-if="editableVars.length > 0">
            <div class="form-group col">
                <label for="var-name">{{modVar.name}}: </label>
                <input type="text" class="form-control" id="var-name" v-model="stack.variableValues[modVar.name]" @change="recomputeState" />
                <small id="emailHelp" class="form-text text-muted">{{modVar.description}}</small>
            </div>
        </div>
        <div v-if="editableVars.length === 0">
            <p>No editable variable defined for this module.</p>
        </div>
    </div>
</template>

<script>
Vue.component('stack-vars', {
    template: "#stack-vars",
    props: ["stack", "module"],
    methods: {
        recomputeState: function(){
            if(stack.state === "RUNNING"){
                stack.state = "TO_UPDATE";
            }
        }
    },
    computed: {
        editableVars: function() {
            return this.module.variables.filter(variable => variable.editable);
        }
    }
});
</script>