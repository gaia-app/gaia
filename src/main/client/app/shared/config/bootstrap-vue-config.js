import Vue from 'vue';
import {
  BreadcrumbPlugin,
  ButtonPlugin,
  CardPlugin,
  DropdownPlugin,
  FormCheckboxPlugin,
  FormGroupPlugin,
  FormInputPlugin,
  FormTextareaPlugin,
  FormPlugin,
  InputGroupPlugin,
  LayoutPlugin,
  NavbarPlugin,
  NavPlugin,
  SpinnerPlugin,
  TabsPlugin,
  ToastPlugin,
} from 'bootstrap-vue';

export default {
  init: () => {
    Vue.use(ButtonPlugin);
    Vue.use(CardPlugin);
    Vue.use(FormPlugin);
    Vue.use(FormGroupPlugin);
    Vue.use(FormInputPlugin);
    Vue.use(FormCheckboxPlugin);
    Vue.use(FormTextareaPlugin);
    Vue.use(InputGroupPlugin);
    Vue.use(LayoutPlugin);
    Vue.use(ToastPlugin);
    Vue.use(NavbarPlugin);
    Vue.use(DropdownPlugin);
    Vue.use(SpinnerPlugin);
    Vue.use(TabsPlugin);
    Vue.use(NavPlugin);
    Vue.use(BreadcrumbPlugin);
  },
};
