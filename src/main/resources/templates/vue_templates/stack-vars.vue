<template id="stack-vars">
    <div>
        <div class="form-row align-items-end" v-for="(modVar,modVarIdx) in editableVars" v-if="editableVars.length > 0">
            <b-col>
                <b-form-group :label="modVar.name" :description="modVar.description">
                    <b-input v-if="! isListRegex(modVar.validationRegex)"
                             v-model="stack.variableValues[modVar.name]"
                             :state="validateVariable(modVar).result"
                             @update="recomputeState"
                             trim></b-input>
                    <b-select v-if="isListRegex(modVar.validationRegex)"
                              v-model="stack.variableValues[modVar.name]"
                              :state="validateVariable(modVar).result"
                              @change="recomputeState"
                              :options="listOptions(modVar.validationRegex)"></b-select>
                    <b-form-invalid-feedback>{{validateVariable(modVar).message}}</b-form-invalid-feedback>
                </b-form-group>
            </b-col>
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
            // no validation needed if variable is not mandatory and if no validation regex
            if(! moduleVariable.mandatory && ! moduleVariable.validationRegex){
                return {result:null};
            }

            const value = this.stack.variableValues[moduleVariable.name];

            // checking mandatory first
            if(moduleVariable.mandatory){
                if(value === null || typeof value === "undefined" || value.trim().length === 0){
                    return {result:false, message:"This field is mandatory"};
                }
            }
            else{
                // not checking empty values
                if(value === null || typeof value === "undefined" || value.trim().length === 0){
                    return {result:null};
                }
            }

            // check regex if defined
            if(moduleVariable.validationRegex){
                if( ! new RegExp(moduleVariable.validationRegex).test(value) ) {
                    return {result:false, message:`This field must match the regex ${moduleVariable.validationRegex}`};
                }
            }

            return {result:true};
        },
        isListRegex: function(regex){
            const listRegex = /^\((\w*)(\|(\w*))*\)$/;
            return listRegex.test(regex);
        },
        listOptions: function(regex){
            const listExtractRegex = /[^()|]+/g;
            // extracting the values from the regex
            // also adding empty string to the result to be able to not select anything !
            return ["", ...regex.match(listExtractRegex)];
        }
    },
    computed: {
        editableVars: function() {
            return this.module.variables.filter(variable => variable.editable);
        },
        variablesValid: function(){
            return this.editableVars
                .map(this.validateVariable)
                .map(validationResult => validationResult.result)
                .every(result => result === null || result === true);
        }
    },
    created: function(){
        this.recomputeState();
    }
});
</script>