<template>
  <b-form-group
    label="Terraform Image"
    :state="isFormValid"
  >
    <b-input-group>
      <b-input-group-prepend is-text>
        <b-form-checkbox
          id="image_override"
          v-model="isCustomImage"
          switch
          @change="overrideImage"
        >
          Custom
        </b-form-checkbox>
      </b-input-group-prepend>

      <app-form-typeahead
        v-if="isCustomImage"
        id="image_repository"
        v-model="image.repository"
        placeholder="Repository"
        :state="isRepositoryNotEmpty && isRepositoryValid"
        :formatter="formatRepository"
        group-position="middle"
        source="/api/docker/repositories?name=%REPO"
        wildcard="%REPO"
        filter-property="name"
      />

      <app-form-typeahead
        id="image_tag"
        v-model="image.tag"
        placeholder="Tag"
        :state="isTagNotEmpty"
        :disabled="!isRepositoryNotEmpty || !isRepositoryValid"
        group-position="right"
        :source="'/api/docker/repositories/' + image.repository + '/tags?name=%TAG'"
        wildcard="%TAG"
        filter-property="name"
        :min-length="1"
      />
    </b-input-group>

    <b-form-text>
      Default from <strong>hashicorp/terraform</strong>. Switch to override it
    </b-form-text>

    <template slot="invalid-feedback">
      {{ invalidFeedback }}
    </template>
  </b-form-group>
</template>

<script>
  import { AppFormTypeahead } from '@/shared/components';

  const OFFICIAL_REPOSITORY = 'hashicorp/terraform';

  export default {
    name: 'AppTerraformImageInput',
    components: {
      AppFormTypeahead,
    },
    props: {
      image: { type: Object, required: true },
    },
    data: () => ({
      isCustomImage: null,
    }),
    computed: {
      isFormValid() {
        const result = this.isRepositoryNotEmpty && this.isRepositoryValid && this.isTagNotEmpty;
        // emit form status to parent
        this.$emit('form-status', result);
        return result;
      },
      isRepositoryNotEmpty() {
        return !!this.image.repository;
      },
      isRepositoryValid() {
        return /^[\w][\w.\-/]{0,127}$/.test(this.image.repository);
      },
      isTagNotEmpty() {
        return !!this.image.tag && /^\S*$/.test(this.image.tag);
      },
      invalidFeedback() {
        let error = 'These fields are mandatory';
        if (!this.isRepositoryValid) {
          error = 'The repository does not match the docker name convention [\\w][\\w.-/]{0,127}';
        }
        return error;
      },
    },
    watch: {
      isCustomImage(newValue) {
        // emit to override status to parent
        this.$emit('override-status', newValue);
      },
    },
    created() {
      this.isCustomImage = this.image.repository !== OFFICIAL_REPOSITORY;
    },
    methods: {
      overrideImage() {
        if (this.isCustomImage && this.image.repository !== OFFICIAL_REPOSITORY) {
          // force the repository and reset the version
          this.image.repository = OFFICIAL_REPOSITORY;
          this.image.tag = null;
        }
      },
      formatRepository(value) {
        if (!value) return '';
        if (!value.includes(':')) return value;
        [this.image.repository, this.image.tag] = value.split(':');
        document.getElementById('image_tag').focus();
        return this.image.repository;
      },
    },
  };
</script>
