import Vue from 'vue';
import {
  FormCheckboxPlugin,
  FormInputPlugin,
  FormPlugin,
  ToastPlugin,
} from 'bootstrap-vue';

export default {
  init: () => {
    Vue.use(FormPlugin);
    Vue.use(FormInputPlugin);
    Vue.use(FormCheckboxPlugin);
    Vue.use(ToastPlugin);
  },
};
