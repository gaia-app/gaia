export const isUserNotAuthenticated = (to, from, next) => {
  if (!false) {
    next();
    return;
  }
  next('/');
};

export const isUserAuthenticated = (to, from, next) => {
  if (true) {
    next();
    return;
  }
  next('/login');
};
