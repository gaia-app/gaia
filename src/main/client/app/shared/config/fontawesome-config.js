import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faLock,
  faUser,
} from '@fortawesome/free-solid-svg-icons';
import {
  faGithub,
  faGitlab,
} from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  init: () => {
    library.add(
      faUser,
      faLock,
      faGithub,
      faGitlab,
    );
    Vue.component('font-awesome-icon', FontAwesomeIcon);
  },
};
