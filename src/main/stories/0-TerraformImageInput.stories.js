import TerraformImageInput from '../client/app/pages/modules/terraform-image-input';

export default {
  title: 'TerraformImageInput',
  component: TerraformImageInput,
};

const defaultImage = {
  repository: "hashicorp/terraform",
  tag: "0.12.7"
}

const customImage = {
  repository: "google/terraform",
  tag: "0.12.7"
}

export const withDefaultImage = () => ({
  components: { TerraformImageInput },
  template: '<b-form><terraform-image-input :image="image"/></b-form>',
  props: {
    image: {
      default: () => defaultImage
    }
  }
});

export const withCustomImage = () => ({
  components: { TerraformImageInput },
  template: '<terraform-image-input :image="image"/>',
  props: {
    image: {
      default: () => customImage
    }
  }
});
