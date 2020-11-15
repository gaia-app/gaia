<template>
  <div
    class="job-metadata-container"
    :class="job.status"
  >
    <div class="job-infos metadata">
      <span class="job-detail-title">Job&nbsp;{{ job.id }}</span>
      <p>
        <font-awesome-icon
          icon="folder"
          class="icon"
        />&nbsp;
        Module
        <router-link :to="{ name: 'module_description', params: { moduleId: stack.module.id }}">
          {{ stack.module.id }}
        </router-link>
      </p>
      <p>
        <font-awesome-icon
          icon="file"
          class="icon"
        />&nbsp;
        Stack
        <router-link :to="{ name: 'stack_edition', params: { stackId: stack.id }}">
          {{ stack.id }}
        </router-link>
      </p>
      <p>
        <font-awesome-icon
          icon="user"
          class="icon"
        />&nbsp;
        <app-user-badge :user="job.user" />
      </p>
    </div>
    <div class="job-execution metadata">
      <span class="job-detail-title">Status&nbsp;{{ job.status }}</span>
      <p>
        <font-awesome-icon
          icon="play"
          class="icon"
        />&nbsp;
        Started&nbsp;<strong>{{ job.startDateTime | dateTimeLong }}</strong>
      </p>
      <p v-if="job.endDateTime">
        <font-awesome-icon
          icon="stop"
          class="icon"
        />&nbsp;
        Ended&nbsp;<strong>{{ job.endDateTime | dateTimeLong }}</strong>
      </p>
      <p>
        <font-awesome-icon
          icon="stopwatch"
          class="icon"
        />&nbsp;Duration
        <strong>
          <app-job-timer
            :start-time="job.startDateTime"
            :end-time="job.endDateTime"
          />
        </strong>
      </p>
    </div>
    <div class="job-actions">
      <b-button
        v-if="isRetryAvailable"
        variant="info"
        @click="$emit('retry')"
      >
        <font-awesome-icon icon="redo" />&nbsp;Retry
      </b-button>
      <b-button
        v-if="isDeleteAvailable"
        variant="danger"
        @click="$emit('delete')"
      >
        <font-awesome-icon :icon="['far', 'trash-alt']" />&nbsp;Delete
      </b-button>
    </div>
  </div>
</template>

<script>
  import AppUserBadge from '@/pages/users/user-badge.vue';
  import AppJobTimer from '@/pages/stacks/job/job-timer.vue';

  export default {
    name: 'AppJobMetadata',
    components: {
      AppUserBadge,
      AppJobTimer,
    },
    props: {
      stack: { type: Object, required: true },
      job: { type: Object, required: true },
    },
    computed: {
      isRetryAvailable() {
        return this.job.status !== null && this.job.status.includes('FAILED');
      },
      isDeleteAvailable() {
        return this.job.status !== null && !this.job.status.includes('STARTED');
      },
    },
  };
</script>

<style scoped>
  .job-metadata-container {
    display: flex;
    background: linear-gradient(to right, #eaeaea 0, #eaeaea 1rem, #fff 1rem, #fff 100%) no-repeat;
    border: 1px solid #eaeaea;
    padding: 1rem 1rem 1rem 2rem;
  }

  .job-metadata-container .job-infos, .job-metadata-container .job-execution {
    flex: 0 1 45%;
  }

  .job-metadata-container .job-execution, .job-metadata-container .job-actions {
    margin-left: 1rem;
  }

  .job-metadata-container .job-actions {
    display: flex;
    flex-direction: column;
    flex: 1 1 auto;
  }

  .job-metadata-container .job-actions button + button {
    margin-top: 0.25rem;
  }

  .job-metadata-container .metadata p {
    font-size: 14px;
  }

  .job-metadata-container .metadata .icon {
    width: 1rem;
    margin-right: 1rem;
    text-align: center;
  }

  .job-metadata-container .job-detail-title {
    display: inline-block;
    font-size: larger;
    margin-bottom: 1rem;
  }

  .job-metadata-container[class*=PENDING] {
    background: linear-gradient(to right, #91B0B1 0, #91B0B1 1rem, #fff 1rem, #fff 100%) no-repeat;
    color: #91B0B1;
  }

  .job-metadata-container[class*=PENDING] .job-detail-title {
    color: #91B0B1;
  }

  .job-metadata-container[class*=STARTED] {
    background: linear-gradient(to right, #2196f3 0, #2196f3 1rem, #fff 1rem, #fff 100%) no-repeat;
  }

  .job-metadata-container[class*=STARTED] .job-detail-title {
    color: #2196f3;
  }

  .job-metadata-container[class*=FINISHED] {
    background: linear-gradient(to right, #1ed085 0, #1ed085 1rem, #fff 1rem, #fff 100%) no-repeat;
  }

  .job-metadata-container[class*=FINISHED] .job-detail-title {
    color: #1ed085;
  }

  .job-metadata-container[class*=FAILED] {
    background: linear-gradient(to right, #dc3545 0, #dc3545 1rem, #fff 1rem, #fff 100%) no-repeat;
  }

  .job-metadata-container[class*=FAILED] .job-detail-title {
    color: #dc3545;
  }
</style>
