<template id="terraform-image-input">
    <b-form-group label="Terraform Image" :state="isFormValid">
        <b-input-group>

            <b-input-group-prepend is-text>
                <b-form-checkbox
                        id="image_override"
                        switch
                        v-model="isCustomImage"
                        @change="overrideImage">
                    Custom
                </b-form-checkbox>
            </b-input-group-prepend>

            <form-typeahead
                    v-if="isCustomImage"
                    id="image_repository"
                    placeholder="Repository"
                    v-model="image.repository"
                    @input="resetTag"
                    :state="isRepositoryNotEmpty && isRepositoryValid"
                    :formatter="formatRepository"
                    group-position="middle"
                    source="/api/docker/repositories?name=%REPO"
                    wildcard="%REPO"
                    filter-property="name"
            ></form-typeahead>

            <form-typeahead
                    id="image_tag"
                    placeholder="Tag"
                    v-model="image.tag"
                    :state="isTagNotEmpty"
                    :disabled="!isRepositoryNotEmpty || !isRepositoryValid"
                    group-position="right"
                    :source="'/api/docker/repositories/' + image.repository + '/tags?name=%TAG'"
                    wildcard="%TAG"
                    filter-property="name"
                    min-length="1"
            ></form-typeahead>

        </b-input-group>

        <b-form-text>
            Default from <strong>hashicorp/terraform</strong>. Switch to override it
        </b-form-text>

        <template slot="invalid-feedback">{{invalidFeedback}}</template>
    </b-form-group>
</template>

<script>
    Vue.component('terraform-image-input', {
        template: '#terraform-image-input',
        props: ['image'],
        data: () => ({
            isCustomImage: null,
            officialRepository: 'hashicorp/terraform',
        }),
        created: function () {
            this.isCustomImage = this.image.repository !== this.officialRepository;
        },
        computed: {
            isFormValid: function () {
                const result = this.isRepositoryNotEmpty && this.isRepositoryValid && this.isTagNotEmpty;
                // emit form status to parent
                this.$emit('form-status', result);
                return result;
            },
            isRepositoryNotEmpty: function () {
                return !!this.image.repository;
            },
            isRepositoryValid: function () {
                return /^[\w][\w.\-\/]{0,127}$/.test(this.image.repository);
            },
            isTagNotEmpty: function () {
                return !!this.image.tag && /^\S*$/.test(this.image.tag);
            },
            invalidFeedback: function () {
                let error = 'These fields are mandatory';
                if (!this.isRepositoryValid) {
                    error = 'The repository does not match the docker name convention [\w][\w.-/]{0,127}';
                }
                return error;
            },
        },
        watch: {
            isCustomImage: function (newValue) {
                // emit to override status to parent
                this.$emit('override-status', newValue);
            },
        },
        methods: {
            overrideImage: function () {
                if (this.isCustomImage && this.image.repository !== this.officialRepository) {
                    // force the repository and reset the version
                    this.image.repository = this.officialRepository;
                    this.resetTag();
                }
            },
            formatRepository: function (value) {
                if (!value) return '';
                if (!value.includes(':')) return value;
                [this.image.repository, this.image.tag] = value.split(':');
                $('#image_tag').focus();
                return this.image.repository;
            },
            resetTag: function () {
                this.image.tag = null;
            },
        },
    });
</script>
