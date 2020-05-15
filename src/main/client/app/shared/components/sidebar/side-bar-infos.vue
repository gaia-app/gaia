<template>
  <div class="infos">
    <span
      v-if="buildInfo.version"
      class="build-info"
    >
      <font-awesome-icon
        icon="tag"
        class="icon"
      />
      <span>{{ buildInfo.version }}</span>
    </span>
    <span
      v-if="buildInfo.commitId"
      class="build-info build-commit"
    >
      <font-awesome-icon
        :icon="['fab', 'github']"
        class="icon"
      />
      <span>{{ buildInfo.commitId }}</span>
    </span>
  </div>
</template>

<script>
  export default {
    name: 'AppSideBarInfos',
    props: {
      buildInfo: { type: Object, required: true },
    },
  };
</script>

<style scoped>
  .infos {
    background: #f3f3f3;
    color: #222;
    padding: 15px 25px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    white-space: nowrap;
  }

  .build-info {
    display: flex;
    align-items: center;
  }

  .build-info span {
    margin-left: 5px;
  }
</style>
