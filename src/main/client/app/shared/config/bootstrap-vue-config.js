import Vue from 'vue';
import {
  AlertPlugin,
  BadgePlugin,
  BreadcrumbPlugin,
  ButtonPlugin,
  CardPlugin,
  DropdownPlugin,
  FormCheckboxPlugin,
  FormGroupPlugin,
  FormInputPlugin,
  FormPlugin,
  FormSelectPlugin,
  FormTextareaPlugin,
  ImagePlugin,
  InputGroupPlugin,
  LayoutPlugin,
  ModalPlugin,
  NavbarPlugin,
  NavPlugin,
  OverlayPlugin,
  SpinnerPlugin,
  TablePlugin,
  TabsPlugin,
  ToastPlugin,
  TooltipPlugin,
} from 'bootstrap-vue';

export default {
  init: () => {
    Vue.use(AlertPlugin);
    Vue.use(BadgePlugin);
    Vue.use(BreadcrumbPlugin);
    Vue.use(ButtonPlugin);
    Vue.use(CardPlugin);
    Vue.use(DropdownPlugin);
    Vue.use(FormCheckboxPlugin);
    Vue.use(FormGroupPlugin);
    Vue.use(FormInputPlugin);
    Vue.use(FormPlugin);
    Vue.use(FormSelectPlugin);
    Vue.use(FormTextareaPlugin);
    Vue.use(ImagePlugin);
    Vue.use(InputGroupPlugin);
    Vue.use(LayoutPlugin);
    Vue.use(ModalPlugin);
    Vue.use(NavbarPlugin);
    Vue.use(NavPlugin);
    Vue.use(OverlayPlugin);
    Vue.use(SpinnerPlugin);
    Vue.use(TablePlugin);
    Vue.use(TabsPlugin);
    Vue.use(ToastPlugin);
    Vue.use(TooltipPlugin);
  },
};
