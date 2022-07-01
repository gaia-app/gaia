<template>
  <div>
    <div class="block">
      <div class="block_head">
        <h2>User {{ user.username }}</h2>
      </div>

      <div class="block_content">
        <b-form>
          <b-form-group
            v-if="user.new"
            label="Username"
            description="The username"
          >
            <b-form-input v-model="user.username" />
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
          @click="saveUser"
        >
          <font-awesome-icon
            icon="save"
            class="icon"
          />
          Save User
        </b-button>

        <b-button
          v-if="! user.new"
          variant="danger"
          class="ml-3"
          @click="deleteUser"
        >
          <font-awesome-icon
            icon="user-minus"
            class="icon"
          />
          Delete User
        </b-button>
      </div>
    </div>
    <div class="block mt-2">
      <div class="block_head">
        <h2>Change user password</h2>
      </div>

      <div class="block_content">
        <b-form v-if="! user.oauth2User">
          <b-form-row
            v-if="! user.oauth2User"
          >
            <b-form-group
              label="Password"
              description="Change the user password"
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
          </b-form-row>
        </b-form>

        <b-alert
          v-else
          show
        >
          This user is an OAuth2 User. Its password cannot be changed.
        </b-alert>
      </div>

      <div
        v-if="! user.oauth2User"
        class="block_content"
      >
        <b-button
          variant="primary"
          @click="changePassword"
        >
          <font-awesome-icon
            icon="save"
            class="icon"
          />
          Change user password
        </b-button>
      </div>
    </div>
  </div>
</template>

<script>
  import { getOrganizations } from '@/shared/api/organizations-api';
  import { changeUserPassword, deleteUser, updateUser } from '@/shared/api/users-api';
  import { displayNotification } from '@/shared/services/modal-service';

  export default {
    name: 'AppUserEdition',
    props: {
      user: {
        type: Object,
        default: () => ({
          new: true,
        }),
      },
      username: {
        type: String,
        required: true,
      },
    },
    data: () => ({
      organizations: [],
    }),
    async created() {
      this.organizations = await getOrganizations();
    },
    methods: {
      async redirectToUsersPage() {
        await this.$router.push({ name: 'users' });
      },
      async saveUser() {
        await updateUser(this.user)
          .then(() => displayNotification(this, { message: 'User updated', variant: 'success' }))
          .then(this.redirectToUsersPage)
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
      async changePassword() {
        await changeUserPassword(this.username, this.user.password)
          .then(() => displayNotification(this, { message: 'Password changed', variant: 'success' }))
          .catch(({ error, message }) => displayNotification(this, { title: error, message, variant: 'danger' }));
      },
      async deleteUser() {
        await deleteUser(this.username)
          .then(() => displayNotification(this, { message: 'User deleted', variant: 'success' }))
          .then(this.redirectToUsersPage)
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
