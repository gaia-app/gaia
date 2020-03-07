<template>
  <a
    class="sidebar-toggle"
    @click="toggle"
  >
    <template v-if="collapsed">
      <font-awesome-icon
        icon="angle-double-right"
        class="icon"
      />
    </template>
    <template v-else>
      <font-awesome-icon
        icon="angle-double-left"
        class="icon"
      />
      <span>Collapse sidebar</span>
    </template>
  </a>
</template>

<script>
  export default {
    name: 'AppSideBarToggle',
    props: {
      value: { type: Boolean, required: true }, // for v-model binding
    },
    data: () => ({
      collapsed: false,
    }),
    watch: {
      value: {
        immediate: true,
        handler(newValue) {
          this.collapsed = newValue;
        },
      },
    },
    methods: {
      toggle() {
        this.collapsed = !this.collapsed;
        // emit to update model outside the component
        this.$emit('input', this.collapsed);
      },
    },
  };
</script>

<style scoped>
  .sidebar-toggle {
    transition: width 0.3s;
    background-color: #f3f3f3;
    border-bottom: 1px solid #e5e5e5;
    padding: 15px 25px;
    color: #707070;
    display: flex;
    align-items: center;
    cursor: pointer;
    line-height: 20px;
    white-space: nowrap;
  }

  .sidebar-toggle .icon {
    font-size: 20px;
    width: 20px;
  }

  .sidebar-toggle span {
    margin-left: 15px;
  }
</style>
