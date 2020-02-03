<template id="job-history-template">
    <div class="block">
        <div class="block_head dark_bg">
            <h2><span><i class="fas fa-history"></i> Job history</span></h2>
        </div>
        <ul class="job_list">
            <li v-for="(job, index) in jobs" :class="job.status" class="job">
                <div class="job_id">
                    <a :href="'/stacks/' + job.stackId + '/jobs/' + job.id">Job #{{jobs.length - index}}</a>
                </div>
                <div class="filler"></div>
                <div class="job_detail">
                    <div>
                        <span class="job_attr_id">Type</span>
                        <span class="job_attr_value" :class="job.type">{{job.type}}</span>
                    </div>
                    <div>
                        <span class="job_attr_id">Status</span>
                        <span class="job_attr_value" :class="job.status">{{job.status}}</span>
                    </div>
                    <div>
                        <span class="job_attr_id">User</span>
                        <user-badge :user="job.user" :css-class="'job_attr_value'"></user-badge>
                    </div>
                </div>
                <div class="filler"></div>
                <div class="job_detail">
                    <div>
                        <span class="job_attr_icon"><i class="far fa-calendar-alt"></i></span>
                        <span class="job_attr_value">{{job.startDateTime | dateTime}}</span>
                    </div>
                    <div>
                        <span class="job_attr_icon"><i class="fas fa-stopwatch"></i></span>
                        <job-timer
                                :start-time="job.startDateTime"
                                :end-time="job.endDateTime"
                                :css-class="'job_attr_value'">
                        </job-timer>
                    </div>
                </div>
                <div class="filler"></div>
                <div class="job_detail job_actions">
                    <i class="fas fa-redo retry" v-if="isRetryAvailable(job.status)" @click="retryJob(job.id)"></i>
                    <i class="far fa-trash-alt delete" v-if="isDeleteAvailable(job.status)" @click="deleteJob(job.id)"></i>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    Vue.component('job-history', {
        template: "#job-history-template",
        props: ["jobs"],
        methods: {
            retryJob: function (jobId) {
                this.callRetry(jobId);
            },
            deleteJob: function (jobId) {
                this.confirmDialog()
                    .then(value => {
                        if (value) this.callDelete(jobId);
                    });
            },
            confirmDialog: function () {
                return this.$bvModal.msgBoxConfirm('This will delete the job. Continue?', {
                    title: 'Delete request',
                    centered: true,
                    noCloseOnBackdrop: true,
                    cancelTitle: 'No',
                    okVariant: 'danger',
                    okTitle: 'Yes',
                    returnFocus: 'body',
                });
            },
            callRetry: function (jobId) {
                const message = Messenger().post({type: "info", message: "Retrying job."});
                return $.ajax({
                    url: `/api/jobs/${jobId}/retry`,
                    type: 'POST',
                    success: () => {
                        message.update({type: "info", message: "Job deleted."});
                    },
                    error: () => {
                        message.update({type: "error", message: "Unable to delete job,"});
                    }
                });
            },
            callDelete: function (jobId) {
                const message = Messenger().post({type: "info", message: "Deleting job."});
                return $.ajax({
                    url: `/api/jobs/${jobId}`,
                    type: 'DELETE',
                    success: () => {
                        message.update({type: "info", message: "Job deleted."});
                    },
                    error: () => {
                        message.update({type: "error", message: "Unable to delete job,"});
                    }
                });
            },
            isRetryAvailable: function (status) {
                return status !== null && status.indexOf('FAILED') > 0;
            },
            isDeleteAvailable: function (status) {
                return status !== null && status.indexOf('STARTED') < 0;
            },
        }
    });
</script>
