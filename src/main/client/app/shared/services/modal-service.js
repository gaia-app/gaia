export const displayNotification = (vueContext, options) => {
  vueContext.$bvToast.toast(options.message, {
    solid: true,
    toaster: 'b-toaster-top-center',
    title: options.title ? `Gaia - ${options.title}` : 'Gaia',
    variant: options.variant,
  });
};
