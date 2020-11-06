<template>
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

        <b-form-group
          label="Team"
          description="The user team"
        >
          <vue-multiselect
            v-model="user.team"
            :options="teams"
            track-by="id"
            label="id"
            :multiple="false"
            placeholder="Select user team"
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

      <b-alert
        v-if="user.oauth2User"
        show
      >
        This user is an OAuth2 User. Its password cannot be changed.
      </b-alert>
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
</template>

<script>
  import { getTeams } from '@/shared/api/teams-api';
  import { createUser, deleteUser, updateUser } from '@/shared/api/users-api';

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
      teams: [],
    }),
    async created() {
      console.log(this.user);
      this.teams = await getTeams();
    },
    methods: {
      async saveUser() {
        console.log('Save User', this.user);
        if (this.user.new) {
          await createUser(this.user);
        } else {
          await updateUser(this.user);
        }
      },
      async deleteUser() {
        console.log('Delete user', this.username);
        await deleteUser(this.username);
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
