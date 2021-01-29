gaia = db.getSiblingDB('gaia');
gaia.plan.drop();
gaia.plan.insert([
  {
    '_id': '1f38d7e3-b7d5-4bbb-9a75-1c1ea35dfee9',
    'terraform_version': '0.12.29',
    'resource_changes': [
      {
        'address': 'docker_container.mongo',
        'provider_name': 'docker',
        'type': 'docker_container',
        'change': { 'actions': ['CREATE'] },
      }, {
        'address': 'docker_image.mongo',
        'provider_name': 'docker',
        'type': 'docker_image',
        'change': { 'actions': ['CREATE'] },
      }],
    '_class': 'io.gaia_app.stacks.bo.Plan',
  },
]);
