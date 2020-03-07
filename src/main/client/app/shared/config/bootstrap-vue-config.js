import Vue from 'vue';
import {
  DropdownPlugin,
  FormCheckboxPlugin,
  FormInputPlugin,
  FormPlugin,
  NavbarPlugin,
  NavPlugin,
  ToastPlugin,
} from 'bootstrap-vue';

export default {
  init: () => {
    Vue.use(FormPlugin);
    Vue.use(FormInputPlugin);
    Vue.use(FormCheckboxPlugin);
    Vue.use(ToastPlugin);
    Vue.use(NavbarPlugin);
    Vue.use(DropdownPlugin);
    Vue.use(NavPlugin);
  },
};
