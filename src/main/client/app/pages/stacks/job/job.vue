<template>
  <div
    v-if="loaded"
    class="block"
  >
    <div class="block_head_flex">
      <h2>Stack {{ stack.name }}</h2>
      <app-cli-badge
        :cli="job.terraformImage"
        badge-style="for-the-badge"
      />
    </div>
    <div class="block_content">
      <app-job-metadata
        :stack="stack"
        :job="job"
        @retry="retryJob"
        @delete="deleteJob"
      />
      <app-job-step
        id="step-1"
        :header-title="firstStepTitle"
        :step="job.steps[0]"
      />
      <app-job-apply-confirm
        v-if="isSecondStepDoable"
        :title="secondStepTitle"
        @apply="applyJob"
      />
      <app-job-step
        id="step-2"
        :header-title="secondStepTitle"
        :step="job.steps[1]"
      />
    </div>
  </div>
</template>

<script>
  import {
    applyJob,
    deleteJob,
    getJob,
    planJob,
    retryJob,
  } from '@/shared/api/jobs-api';
  import { getStack } from '@/shared/api/stacks-api';
  import { AppCliBadge } from '@/shared/components';
  import AppJobMetadata from '@/pages/stacks/job/job-metadata.vue';
  import AppJobStep from '@/pages/stacks/job/job-step.vue';
  import AppJobApplyConfirm from '@/pages/stacks/job/job-apply-confirm.vue';
  import {
    displayConfirmDialog,
    displayNotification,
  } from '@/shared/services/modal-service';

  const INTERVAL_TIMEOUT = 1000;

  export default {
    name: 'AppJob',
    components: {
      AppCliBadge,
      AppJobMetadata,
      AppJobStep,
      AppJobApplyConfirm,
    },
    props: {
      stackId: { type: String, required: true },
      jobId: { type: String, required: true },
    },
    data: () => ({
      stack: null,
      job: null,
      loaded: false,
      refreshIntervalId: null,
    }),
    computed: {
      firstStepTitle() {
        return this.job.type === 'RUN' ? 'plan' : 'plan destroy';
      },
      secondStepTitle() {
        return this.job.type === 'RUN' ? 'apply' : 'destroy';
      },
      isSecondStepDoable() {
        return this.job.status
          && !this.job.status.includes('STARTED')
          && !this.job.status.includes('FAILED')
          && !this.job.status.includes('APPLY');
      },
    },
    async created() {
      [this.stack, this.job] = await Promise.all([getStack(this.stackId), this.refreshJob()]);
      this.loaded = true;
      if (!this.job.status) {
        await this.planJob();
      }
    },
    destroyed() {
      clearInterval(this.refreshIntervalId);
    },
    methods: {
      async planJob() {
        await planJob(this.jobId);
        this.refreshIntervalId = setInterval(this.refreshJob, INTERVAL_TIMEOUT);
      },
      async applyJob() {
        await applyJob(this.jobId);
        this.refreshIntervalId = setInterval(this.refreshJob, INTERVAL_TIMEOUT);
      },
      async retryJob() {
        await retryJob(this.jobId);
        this.refreshIntervalId = setInterval(this.refreshJob, INTERVAL_TIMEOUT);
      },
      async deleteJob() {
        const message = 'This will delete the job. Continue?';
        const confirm = await displayConfirmDialog(this, { title: 'Delete Request', message });
        if (confirm) {
          try {
            await deleteJob(this.jobId);
            displayNotification(this, { message: 'Job deleted.', variant: 'info' });
            this.$router.push({ name: 'stack_edition' });
          } catch (e) {
            displayNotification(this, { message: 'Unable to delete job.', variant: 'danger' });
          }
        }
      },
      async refreshJob() {
        this.job = await getJob(this.jobId);
        if (this.job.status && !this.job.status.includes('STARTED')) {
          clearInterval(this.refreshIntervalId);
        }
        return this.job;
      },
    },
  };
</script>
