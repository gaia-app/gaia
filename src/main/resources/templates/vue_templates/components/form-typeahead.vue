<template id="form-typeahead">
    <div class="form-typeahead">
        <b-form-input
                :id="id"
                :placeholder="placeholder"
                v-model.trim="value"
                :state="state"
                :formatter="formatter"
                :class="groupPosition"
                :disabled="disabled"
                :readonly="readonly"
                type="text"
                autocomplete="false"
                @change="propagateChangeEvent"
        ></b-form-input>
    </div>
</template>

<script>
    Vue.component('form-typeahead', {
        template: '#form-typeahead',
        props: {
            // properties for input
            id: {type: String, required: true}, // id of the input
            placeholder: {type: String}, // placeholder of the input
            value: {type: String, required: true}, // for v-model binding
            state: {type: Boolean}, // for form validation
            formatter: {type: String}, // for input format
            groupPosition: {type: 'left' | 'middle' | 'right'}, // position in an input group
            disabled: {type: Boolean}, // input is disabled or not
            readonly: {type: Boolean}, // input is readonly or not
            // properties for typeahead
            source: {type: [String, Array], required: true}, // can be an url or an array of objects
            wildcard: {type: String, required: true}, // wildcard when source as url
            filterProperty: {type: String, required: true}, // property of object to filter on
            minLength: {type: Number, default: 3}, // minimum character length before rendering suggestions
            limit: {type: Number, default: 10}, // max number of suggestions to be displayed
        },
        mounted: function () {
            this.resetTypeahead();
        },
        watch: {
            value: function (newValue) {
                // emit to update model outside the component
                this.$emit('input', newValue);
            },
            source: function () {
                this.resetTypeahead();
            }
        },
        methods: {
            resetTypeahead: function () {
                const engine = this.initSearchEngine();
                this.bindTypeahead(engine);
            },
            initSearchEngine: function () {
                if (typeof this.source === 'string') {
                    return new Bloodhound({
                        datumTokenizer: Bloodhound.tokenizers.whitespace,
                        queryTokenizer: Bloodhound.tokenizers.whitespace,
                        identify: (item) => item[this.filterProperty],
                        remote: {
                            url: this.source,
                            wildcard: this.wildcard
                        }
                    });
                }
                return new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.whitespace,
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                    identify: (item) => item[this.filterProperty],
                    local: this.source
                });
            },
            bindTypeahead: function (engine) {
                let input = $(`#${this.id}`);
                input.typeahead('destroy');
                input.typeahead(
                    {
                        minLength: this.minLength,
                        highlight: true,
                        hint: false,
                        autoselect: true,
                    },
                    {
                        source: engine,
                        limit: this.limit,
                        display: (item) => item[this.filterProperty],
                    }
                );
                input.on('typeahead:select', (e, item) => {
                    this.value = item.name;
                });
            },
            propagateChangeEvent: function () {
                this.$emit('change', arguments);
            },
        },
    });
</script>

<style scoped>
    /* when inside a bootstrap input-group */
    .form-typeahead {
        position: relative;
        flex: 1 1 0;
        min-width: 0;
        margin-bottom: 0;
    }

    .form-typeahead input.left {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
    }

    .form-typeahead input.middle {
        border-radius: 0;
    }

    .form-typeahead input.right {
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
    }

    /* for twitter typeahead */
    .tt-menu {
        background: #fff;
        border: 1px solid #e8e8e8;
        color: #35495e;
        min-width: 100%;
    }

    .tt-suggestion {
        padding: 12px;
        min-height: 40px;
        line-height: 16px;
        white-space: nowrap;
        cursor: pointer;
    }

    .tt-suggestion.tt-cursor {
        background: #0069d9;
        color: #fff;
    }

    .tt-suggestion .tt-highlight {
        color: #41b883;
    }

    .tt-suggestion.tt-cursor .tt-highlight {
        color: #fff;
    }
</style>
