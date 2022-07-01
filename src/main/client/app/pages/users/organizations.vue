<template>
  <div>
    <b-modal
      id="new-organization-modal"
      title="New Organization"
      ok-title="Create"
      auto-focus-button="ok"
      @ok="createOrg"
      @cancel="newOrganizationName = ''"
      @close="newOrganizationName = ''"
    >
      <b-input
        v-model="newOrganizationName"
        autofocus
      />
    </b-modal>

    <b-button
      v-b-modal.new-organization-modal
      variant="primary"
    >
      <font-awesome-icon
        icon="plus"
        class="icon"
      />
      Create new organization
    </b-button>

    <div class="block mt-3">
      <b-table
        :items="organizations"
        :fields="fields"
        striped
        fixed
        outlined
      >
        <template v-slot:cell(edit)="row">
          <b-button
            variant="outline-danger"
            class="ml-1"
            @click="deleteOrg(row.item)"
          >
            <font-awesome-icon
              :icon="['far', 'trash-alt']"
              class="icon"
            />
          </b-button>
        </template>
      </b-table>
    </div>
  </div>
</template>

<script>
  import { createOrganization, deleteOrganization, getOrganizations } from '@/shared/api/organizations-api';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppOrganizations',
    data: () => ({
      organizations: [],
      newOrganizationName: '',
      fields: [
        { key: 'id', label: 'Name', sortable: true },
        { key: 'edit' },
      ],
    }),
    async created() {
      await this.refresh();
    },
    methods: {
      async refresh() {
        this.organizations = await getOrganizations();
      },
      async createOrg() {
        await createOrganization({ id: this.newOrganizationName })
          .then(() => displayNotification(this, { message: 'Organization created', variant: 'success' }))
          .then(this.refresh)
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
      async deleteOrg(organization) {
        await deleteOrganization(organization.id)
          .then(() => displayNotification(this, { message: 'Organization deleted', variant: 'success' }))
          .then(this.refresh)
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
    },
  };
</script>
