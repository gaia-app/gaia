import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faAngleDoubleLeft,
  faAngleDoubleRight,
  faCog,
  faEdit,
  faLayerGroup,
  faLock,
  faObjectGroup,
  faSignOutAlt,
  faTachometerAlt,
  faTag,
  faUser,
  faUserFriends,
  faInfo,
  faRocket,
} from '@fortawesome/free-solid-svg-icons';
import {
  faGithub,
  faGitlab,
} from '@fortawesome/free-brands-svg-icons';
import { faUser as farUser } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  init: () => {
    library.add(
      faEdit,
      faUser,
      faLock,
      faGithub,
      faGitlab,
      faTachometerAlt,
      faObjectGroup,
      faLayerGroup,
      faCog,
      faUserFriends,
      faTag,
      faSignOutAlt,
      farUser,
      faAngleDoubleLeft,
      faAngleDoubleRight,
      faInfo,
      faRocket,
    );
    Vue.component('font-awesome-icon', FontAwesomeIcon);
  },
};
