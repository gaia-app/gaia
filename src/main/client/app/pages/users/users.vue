<template>
  <div class="block">
    <b-table
      :items="users"
      :fields="fields"
      striped
      hover
      fixed
      outlined
      tbody-tr-class="user-row"
    >
      <template v-slot:cell(team)="row">
        <vue-multiselect
          v-model="row.item.team"
          :options="teams"
          track-by="id"
          label="id"
          :multiple="false"
          searchable
          placeholder="Select user team"
          @input="saveUser(row.item)"
        />
      </template>
    </b-table>
  </div>
</template>

<script>
  import {
    getUsers,
    updateUser,
  } from '@/shared/api/users-api';
  import { getTeams } from '@/shared/api/teams-api';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppUsers',
    data: () => ({
      teams: [],
      users: [],
      fields: [
        { key: 'username', label: 'User', sortable: true },
        { key: 'team', sortable: true },
      ],
    }),
    async created() {
      [this.users, this.teams] = await Promise.all([getUsers(), getTeams()]);
    },
    methods: {
      saveUser(user) {
        updateUser(user)
          .then(() => displayNotification(this, { message: 'User saved', variant: 'success' }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
    },
  };
</script>

<style scoped>
  /deep/ .user-row td:first-child {
    font-size: 16px;
    vertical-align: middle;
  }
</style>
