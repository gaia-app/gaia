export const displayNotification = (vueContext, options) => {
  vueContext.$bvToast.toast(options.message, {
    solid: true,
    toaster: 'b-toaster-top-center',
    title: options.title ? `Gaia - ${options.title}` : 'Gaia',
    variant: options.variant,
  });
};

export const displayConfirmDialog = async (vueContext, options) => vueContext.$bvModal.msgBoxConfirm(options.message, {
  title: options.title,
  centered: true,
  noCloseOnBackdrop: true,
  cancelTitle: 'No',
  okVariant: 'danger',
  okTitle: 'Yes',
  returnFocus: 'body',
});
