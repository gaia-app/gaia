import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faAngleDoubleLeft,
  faAngleDoubleRight,
  faCog,
  faLayerGroup,
  faLock,
  faObjectGroup,
  faSignOutAlt,
  faTachometerAlt,
  faTag,
  faUser,
  faUserFriends,
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
    );
    Vue.component('font-awesome-icon', FontAwesomeIcon);
  },
};
