<template>
  <div
    v-if="module"
    class="block module_description"
  >
    <section class="block_head row">
      <div class="col-md-8">
        <div class="row">
          <div class="col-md-2">
            <img
              :src="imageUrl"
              :alt="module.mainProvider"
            >
          </div>
          <div class="col-md-10">
            <h1>
              <span>{{ module.name }}</span>
              <app-cli-badge
                :cli="module.terraformImage"
                badge-style="for-the-badge"
              />
            </h1>
            <div class="provider">
              {{ module.id }}
            </div>
            <div class="desc">
              {{ module.description }}
            </div>
            <hr>
            <div class="metadata">
              <p>
                Published <strong>{{ module.moduleMetadata.createdAt | dateTimeLong }}</strong>
                by <a href="#">{{ module.moduleMetadata.createdBy.username }}</a>
              </p>
              <p v-if="module.moduleMetadata.updatedAt">
                Last modified <b>{{ module.moduleMetadata.updatedAt | dateTimeLong }}</b>
                by <a href="#">{{ module.moduleMetadata.updatedBy.username }}</a>
              </p>
              <p>Source: <a :href="module.gitRepositoryUrl">{{ module.gitRepositoryUrl }}</a></p>
              <p v-if="module.estimatedMonthlyCost">
                Estimated monthly cost: ${{ module.estimatedMonthlyCost }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section>
      <b-tabs>
        <b-tab active>
          <template slot="title">
            <font-awesome-icon :icon="['fab','markdown']" />  Readme
          </template>
          <app-readme :module-id="module.id" />
        </b-tab>
        <b-tab v-if="module.estimatedMonthlyCostDescription">
          <template slot="title">
            <font-awesome-icon :icon="['fab','markdown']" /> Cost Of Ownership
          </template>
          <b-container fluid>
            <app-markdown :content="module.estimatedMonthlyCostDescription" />
          </b-container>
        </b-tab>
      </b-tabs>
    </section>
  </div>
</template>

<script>
  import { AppCliBadge, AppMarkdown } from '@/shared/components';

  import { getModule } from '@/shared/api/modules-api';

  import AppReadme from './readme.vue';

  export default {
    name: 'AppModuleDescription',

    components: {
      AppCliBadge,
      AppReadme,
      AppMarkdown,
    },

    props: {
      moduleId: {
        type: String,
        required: true,
      },
    },

    data: function data() {
      return {
        module: null,
      };
    },

    computed: {
      imageUrl() {
        // eslint-disable-next-line global-require, import/no-dynamic-require
        return require(`@/assets/images/providers/${this.module.mainProvider || 'unknown'}.png`);
      },
    },

    async created() {
      this.module = await getModule(this.moduleId);
    },
  };
</script>
