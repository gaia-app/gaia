<template id="stack-vars">
    <div>
        <div class="form-row align-items-end" v-for="(modVar,modVarIdx) in editableVars" v-if="editableVars.length > 0">
            <b-form-group class="form-group col">
                <label for="var-name">{{modVar.name}}: </label>
                <b-input id="var-name"
                         v-model="stack.variableValues[modVar.name]"
                         :state="validateVariable(modVar)"
                         @update="recomputeState"
                         aria-describedby="input-live-help input-live-feedback"
                         trim></b-input>
                <b-form-invalid-feedback id="input-live-feedback">This field is mandatory</b-form-invalid-feedback>
                <b-form-text id="input-live-help">{{modVar.description}}</b-form-text>
            </b-form-group>
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
            if(this.stack.state === "RUNNING"){
                this.stack.state = "TO_UPDATE";
            }
            this.$emit("validated", this.variablesValid);
        },
        validateVariable: function(moduleVariable){
            if(! moduleVariable.mandatory){
                return null;
            }
            const value = this.stack.variableValues[moduleVariable.name];
            return !(value === null || typeof value === "undefined" || value.length === 0);
        }
    },
    computed: {
        editableVars: function() {
            return this.module.variables.filter(variable => variable.editable);
        },
        variablesValid: function(){
            return this.editableVars.every(moduleVar => {
                const valid = this.validateVariable(moduleVar);
                return valid === null || valid === true;
            });
        }
    }
});
</script>