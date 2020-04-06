<template>
  <div class="form-typeahead">
    <b-form-input
      :id="id"
      v-model.trim="inputValue"
      :placeholder="placeholder"
      :state="state"
      :formatter="formatter"
      :class="groupPosition"
      :disabled="disabled"
      :readonly="readonly"
      type="text"
      autocomplete="false"
      @change="propagateChangeEvent"
    />
  </div>
</template>

<script>
  import $ from 'jquery';
  import Bloodhound from 'corejs-typeahead';

  export default {
    name: 'AppFormTypeahead',
    props: {
      // properties for input
      id: { type: String, required: true }, // id of the input
      placeholder: { type: String, default: null }, // placeholder of the input
      value: { type: String, default: null }, // for v-model binding
      state: { type: Boolean }, // for form validation
      formatter: { type: Function, default: null }, // for input format
      groupPosition: { type: String, required: true }, // position in an input group
      disabled: { type: Boolean }, // input is disabled or not
      readonly: { type: Boolean }, // input is readonly or not
      // properties for typeahead
      source: { type: [String, Array], required: true }, // can be an url or an array of objects
      wildcard: { type: String, required: true }, // wildcard when source as url
      filterProperty: { type: String, required: true }, // property of object to filter on
      minLength: { type: Number, default: 3 }, // minimum character length before rendering suggestions
      limit: { type: Number, default: 10 }, // max number of suggestions to be displayed
    },
    data: () => ({
      inputValue: null,
    }),
    watch: {
      value: {
        immediate: true,
        handler(newValue) {
          this.inputValue = newValue;
          $(`#${this.id}`).typeahead('val', newValue);
        },
      },
      inputValue(newValue) {
        // emit to update model outside the component
        this.$emit('input', newValue);
      },
      source() {
        this.resetTypeahead();
      },
    },
    mounted() {
      this.resetTypeahead();
    },
    beforeDestroy() {
      this.destroyTypeahead();
    },
    methods: {
      typeaheadEl() {
        return $(`#${this.id}`);
      },
      destroyTypeahead() {
        this.typeaheadEl().typeahead('close').typeahead('destroy');
      },
      resetTypeahead() {
        const engine = this.initSearchEngine();
        this.bindTypeahead(engine);
      },
      initSearchEngine() {
        if (typeof this.source === 'string') {
          return new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            identify: (item) => item[this.filterProperty],
            remote: {
              url: this.source,
              wildcard: this.wildcard,
            },
          });
        }
        return new Bloodhound({
          datumTokenizer: Bloodhound.tokenizers.whitespace,
          queryTokenizer: Bloodhound.tokenizers.whitespace,
          identify: (item) => item[this.filterProperty],
          local: this.source,
        });
      },
      bindTypeahead(engine) {
        this.destroyTypeahead();
        this.typeaheadEl().typeahead(
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
          },
        );
        this.typeaheadEl().on('typeahead:select', (e, item) => {
          this.inputValue = item.name;
        });
      },
      propagateChangeEvent(...args) {
        this.$emit('change', args);
      },
    },
  };
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
  /deep/ .tt-menu {
    background: #fff;
    border: 1px solid #e8e8e8;
    color: #35495e;
    min-width: 100%;
  }

  /deep/ .tt-suggestion {
    padding: 12px;
    min-height: 40px;
    line-height: 16px;
    white-space: nowrap;
    cursor: pointer;
  }

  /deep/ .tt-suggestion.tt-cursor {
    background: #0069d9;
    color: #fff;
  }

  /deep/ .tt-suggestion .tt-highlight {
    color: #41b883;
  }

  /deep/ .tt-suggestion.tt-cursor .tt-highlight {
    color: #fff;
  }
</style>
