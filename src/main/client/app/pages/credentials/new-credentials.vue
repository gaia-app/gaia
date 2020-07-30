<template>
  <div>
    <div v-if="credentials.provider == null">
      <h1>Choose Terraform provider</h1>
      <b-card-group columns>
        <a
          v-for="provider in providerList"
          :key="provider"
          @click="credentials.provider = provider"
        >
          <b-card
            header-class="provider-card"
            no-body
          >
            <template slot="header">
              <app-provider-header
                :provider="provider"
                style="width: 100%;"
              />
            </template>
          </b-card>
        </a>
      </b-card-group>
    </div>
    <app-credentials
      v-if="credentials.provider != null"
      :credentials="credentials"
    />
  </div>
</template>

<script>
  import { AppProviderHeader } from '@/shared/components';
  import AppCredentials from '@/pages/credentials/credentials.vue';
  import { getProviders } from '@/shared/api/credentials-api';

  export default {
    name: 'NewCredentials',
    components: {
      AppProviderHeader,
      AppCredentials,
    },
    data: () => (
      {
        credentials: {
          provider: null,
        },
        providerList: [],
      }
    ),
    async created() {
      this.providerList = await getProviders();
    },
  };
</script>

<style>
  .card-header {
    display: flex;
    align-items: center;
  }

  a {
    cursor: pointer;
  }

  .provider-card {
    padding: 0;
  }
</style>
