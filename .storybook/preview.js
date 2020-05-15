export const parameters = {
}

import Vue from 'vue';
import { Multiselect } from 'vue-multiselect';
import VueFormWizard from 'vue-form-wizard';

import fontawesomeConfig from '@/shared/config/fontawesome-config';
import bootstrapVueConfig from '@/shared/config/bootstrap-vue-config';

fontawesomeConfig.init();
bootstrapVueConfig.init();
Vue.component('vue-multiselect', Multiselect);
Vue.use(VueFormWizard);

/* vendor css */
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.min.css';
import 'vue-multiselect/dist/vue-multiselect.min.css';
import 'vue-form-wizard/dist/vue-form-wizard.min.css';
/* application css */
import "@/assets/css/style.css";
import "@/assets/css/color_2.css";
import "@/assets/css/responsive.css";
