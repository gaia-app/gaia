<template>
  <div>
    <b-button
      variant="primary"
      :to="{ name: 'user_edition', params: { username: 'new' }}"
    >
      <font-awesome-icon
        icon="user-plus"
        class="icon"
      />
      Create new local user
    </b-button>

    <div class="block mt-3">
      <b-table
        :items="users"
        :fields="fields"
        striped
        fixed
        outlined
        tbody-tr-class="user-row"
      >
        <template v-slot:cell(username)="row">
          <app-user-name
            :user="row.item"
          />
        </template>
        <template v-slot:cell(edit)="row">
          <b-button
            variant="outline-secondary"
            :to="{ name: 'user_edition', params: { user: row.item, username: row.item.username }}"
          >
            <font-awesome-icon
              icon="edit"
              class="icon"
            />
          </b-button>
        </template>
      </b-table>
    </div>
  </div>
</template>

<script>
  import {
    getUsers,
    updateUser,
  } from '@/shared/api/users-api';
  import { getTeams } from '@/shared/api/teams-api';
  import { displayNotification } from '@/shared/services/modal-service';
  import AppUserName from '@/pages/users/user-name.vue';

  export default {
    name: 'AppUsers',
    components: { AppUserName },
    data: () => ({
      teams: [],
      users: [],
      fields: [
        { key: 'username', label: 'User', sortable: true },
        { key: 'team.id', label: 'Team', sortable: true },
        { key: 'edit' },
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
