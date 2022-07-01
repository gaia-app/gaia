<template>
  <div class="block">
    <div class="block_head">
      <h2>New user</h2>
    </div>

    <div class="block_content">
      <b-form>
        <b-form-group
          label="Username"
          description="The username"
        >
          <b-form-input v-model="user.username" />
        </b-form-group>

        <b-form-group
          label="Password"
          description="Set the user password"
          class="mr-2"
        >
          <b-form-input
            v-model="user.password"
            type="password"
            :state="user.password && user.password === user.passwordConfirmation"
          />
        </b-form-group>
        <b-form-group
          label="Confirm password"
          description="Confirm the user password"
        >
          <b-form-input
            v-model="user.passwordConfirmation"
            type="password"
            :state="user.password && user.password === user.passwordConfirmation"
          />
        </b-form-group>

        <b-form-group
          label="Organization"
          description="The user organization"
        >
          <vue-multiselect
            v-model="user.organization"
            :options="organizations"
            track-by="id"
            label="id"
            :multiple="false"
            placeholder="Select user organization"
            :show-labels="false"
          />
        </b-form-group>
        <b-form-group
          label="Administrator"
          :description="user.admin === true ? 'The user is an administrator' : 'The user is not an administrator'"
        >
          <b-form-checkbox
            v-model="user.admin"
            type="password"
            switch
            size="lg"
          />
        </b-form-group>
      </b-form>
    </div>

    <div class="block_content">
      <b-button
        variant="primary"
        @click="createUser"
      >
        <font-awesome-icon
          icon="user-plus"
          class="icon"
        />
        Create User
      </b-button>
    </div>
  </div>
</template>

<script>
  import { getOrganizations } from '@/shared/api/organizations-api';
  import { createUser } from '@/shared/api/users-api';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppUserEdition',
    data: () => ({
      organizations: [],
      user: {},
    }),
    async created() {
      this.organizations = await getOrganizations();
    },
    methods: {
      async createUser() {
        await createUser(this.user)
          .then(() => displayNotification(this, { message: 'User created', variant: 'success' }))
          .then(() => this.$router.push({ name: 'users' }))
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
