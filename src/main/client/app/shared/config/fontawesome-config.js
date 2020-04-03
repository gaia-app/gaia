import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faAngleDoubleLeft,
  faAngleDoubleRight,
  faCalendarAlt,
  faCaretSquareUp,
  faCog,
  faEdit,
  faHistory,
  faInfo,
  faLayerGroup,
  faLock,
  faObjectGroup,
  faMinus,
  faPlus,
  faRocket,
  faSave,
  faSignOutAlt,
  faStopCircle,
  faStopwatch,
  faTachometerAlt,
  faTag,
  faTrashAlt,
  faUser,
  faUserFriends,
  faUpload,
  faUserShield,
} from '@fortawesome/free-solid-svg-icons';
import {
  faGithub,
  faGitlab,
  faMarkdown,
  faSuperpowers,
} from '@fortawesome/free-brands-svg-icons';
import { faUser as farUser } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  init: () => {
    library.add(
      faCalendarAlt,
      faEdit,
      faUser,
      faLock,
      faGithub,
      faGitlab,
      faHistory,
      faMarkdown,
      faMinus,
      faPlus,
      faSave,
      faTachometerAlt,
      faTrashAlt,
      faObjectGroup,
      faLayerGroup,
      faCog,
      faUserFriends,
      faTag,
      faSignOutAlt,
      faStopwatch,
      faSuperpowers,
      farUser,
      faAngleDoubleLeft,
      faAngleDoubleRight,
      faInfo,
      faRocket,
      faCaretSquareUp,
      faSave,
      faStopCircle,
      faUpload,
      faUserShield,
    );
    Vue.component('font-awesome-icon', FontAwesomeIcon);
  },
};
