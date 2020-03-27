export const updatePageTitleGuard = async (to, from, next) => {

  if (to.meta && to.meta.title) {
    // update the title
    document.title = to.meta.title;
  }

  next();
};
