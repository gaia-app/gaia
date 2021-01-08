<template>
  <div class="block">
    <div class="block_head job_header">
      <h2><span><font-awesome-icon icon="history" /> Job history</span></h2>
    </div>
    <ul
      v-if="jobs.length"
      class="job_list"
    >
      <li
        v-for="(job, index) in jobs"
        :key="job.id"
        :class="job.status"
        class="job"
      >
        <div class="job_id">
          <router-link
            :to="{ name: 'job', params: { jobId: job.id }}"
          >
            Job #{{ jobs.length - index }}
          </router-link>
        </div>
        <div class="filler" />
        <div class="job_detail">
          <div>
            <span class="job_attr_id">Type</span>
            <span
              class="job_attr_value"
              :class="job.type"
            >
              {{ job.type }}
            </span>
          </div>
          <div>
            <span class="job_attr_id">Status</span>
            <span
              class="job_attr_value"
              :class="job.status"
            >
              {{ job.status }}
            </span>
          </div>
          <div>
            <span class="job_attr_id">User</span>
            <span class="job_attr_value">
              <app-user-badge
                :user="job.user"
                :css-class="'job_attr_value'"
              />
            </span>
          </div>
        </div>
        <div class="filler" />
        <div class="job_detail">
          <div>
            <span class="job_attr_icon"><font-awesome-icon icon="calendar-alt" /></span>
            <span class="job_attr_value">{{ job.startDateTime | dateTime }}</span>
          </div>
          <div>
            <span class="job_attr_icon"><font-awesome-icon icon="stopwatch" /></span>
            <app-job-timer
              :start-time="job.startDateTime"
              :end-time="job.endDateTime"
              :css-class="'job_attr_value'"
            />
          </div>
        </div>
        <div class="filler" />
        <div class="job_detail job_actions">
          <font-awesome-icon
            v-if="isRetryAvailable(job.status)"
            icon="redo"
            class="icon retry"
            @click="retryJob(job.id)"
          />
          <font-awesome-icon
            v-if="isDeleteAvailable(job.status)"
            :icon="['far', 'trash-alt']"
            class="icon delete"
            @click="deleteJob(job.id)"
          />
        </div>
      </li>
    </ul>
    <div
      v-else
      class="block_content"
    >
      <img
        src="@/assets/images/ghost.png"
        width="32"
        alt="ghost"
      >
      No jobs found !
    </div>
  </div>
</template>

<script>
  import {
    displayConfirmDialog,
    displayNotification,
  } from '@/shared/services/modal-service';
  import {
    deleteJob,
    retryJob,
  } from '@/shared/api/jobs-api';

  import AppJobTimer from '@/pages/stacks/job/job-timer.vue';
  import AppUserBadge from '@/pages/users/user-badge.vue';

  export default {
    name: 'AppJobHistory',
    components: {
      AppJobTimer,
      AppUserBadge,
    },
    props: {
      jobs: {
        type: Array,
        default: () => [],
      },
    },
    methods: {
      retryJob(jobId) {
        this.callRetry(jobId);
      },
      async deleteJob(jobId) {
        const message = 'This will delete the job. Continue?';
        if (await displayConfirmDialog(this, { title: 'Delete request', message })) {
          this.callDelete(jobId);
        }
      },
      async callRetry(jobId) {
        try {
          await retryJob(jobId);
          displayNotification(this, { message: 'Job retried.', variant: 'info' });
          this.$emit('job-retried', jobId);
        } catch (e) {
          displayNotification(this, { message: 'Unable to retry job.', variant: 'danger' });
        }
      },
      async callDelete(jobId) {
        try {
          await deleteJob(jobId);
          displayNotification(this, { message: 'Job deleted.', variant: 'info' });
          this.$emit('job-deleted', jobId);
        } catch (e) {
          displayNotification(this, { message: 'Unable to delete job.', variant: 'danger' });
        }
      },
      isRetryAvailable(status) {
        return status !== null && status.includes('FAILED');
      },
      isDeleteAvailable(status) {
        return status !== null && !status.includes('STARTED');
      },
    },
  };
</script>

<style scoped>
  .job_header {
    background: #0971b8;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    border-bottom: 0;
    margin-top: 0;
  }
  .job_header h2 {
    color: #fff;
  }

  .job_list {
    width: 100%;
    list-style: none;
    margin: 0;
  }

  .job_list li {
    padding: 10px;
    border-bottom: solid #eee 1px;
    line-height: normal;
    font-size: 16px;
    border-left: solid 5px #666;
  }

  .job_list li[class*=STARTED] {
    border-left-color: #2196f3;
  }

  .job_list li[class*=FAILED] {
    border-left-color: #e91e63;
  }

  .job_list li[class*=FINISHED] {
    border-left-color: #1ed085;
  }

  .job_list .job {
    display: flex;
    justify-content: space-between;
  }

  .job_list .job .job_id {
    display: flex;
    justify-content: center;
    align-items: center;
    min-width: 100px;
  }

  .job_list .job .job_id a {
    font-size: larger;
  }

  .job_list .job .filler {
    display: flex;
    flex: auto;
    border-left: 2px dashed cadetblue;
  }

  .job_list .job .job_detail {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex: 0 0 35%;
  }

  .job_list .job .job_detail.job_actions {
    flex: 0 0 5%;
  }

  .job_list .job .job_detail.job_actions .icon {
    cursor: pointer;
  }

  .job_list .job .job_detail.job_actions .icon + .icon {
    margin-top: 0.5rem
  }

  .job_list .job .job_detail.job_actions .retry {
    color: #2196f3;
  }

  .job_list .job .job_detail.job_actions .delete {
    color: #dc3545;
  }

  .job_list .job .job_attr_id {
    width: 50px;
  }

  .job_list .job .job_attr_icon {
    width: 20px;
  }

  .job_list .job .job_attr_value {
    font-weight: bolder;
    margin-left: 5px;
  }

  .job_list .job .job_attr_value[class*=STARTED] {
    color: #2196f3;
  }

  .job_list .job .job_attr_value[class*=FINISHED] {
    color: #1ed085;
  }

  .job_list .job .job_attr_value[class*=FAILED] {
    color: #dc3545;
  }

  .job_list .job .job_attr_value.RUN {
    color: #007bff;
  }

  .job_list .job .job_attr_value.DESTROY {
    color: #dc3545;
  }
</style>
