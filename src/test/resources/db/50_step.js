gaia = db.getSiblingDB('gaia');
gaia.step.drop();
gaia.step.insert([
    {
        "_id" : "b3bcea1e-3fb8-4443-bd8b-1caab78e6922",
        "jobId" : "32e4c316-287a-48ba-9c49-dd1b1a0204c8",
        "startDateTime" : ISODate("2020-02-03T18:03:12.227Z"),
        "endDateTime" : ISODate("2020-02-03T18:03:17.035Z"),
        "executionTime" : NumberLong(4807),
        "type" : "PLAN",
        "status" : "FINISHED",
        "logs" : "",
        "_class" : "io.gaia_app.stacks.bo.Step"
    },
    {
        "_id" : "ec9d0334-0055-4727-aa79-731ea79326e3",
        "jobId" : "32e4c316-287a-48ba-9c49-dd1b1a0204c8",
        "startDateTime" : ISODate("2020-02-03T18:03:19.301Z"),
        "endDateTime" : ISODate("2020-02-03T18:03:24.323Z"),
        "executionTime" : NumberLong(5021),
        "type" : "APPLY",
        "status" : "FINISHED",
        "logs" : "using image hashicorp/terraform:latest\ncloning https://oauth2:[MASKED]@github.com/juwit/terraform-docker-mongo\nCloning into 'module'...\ngenerating backend configuration\nTerraform v0.12.20\n\n\u001b[0m\u001b[1mInitializing the backend...\u001b[0m\n\u001b[0m\u001b[32m\nSuccessfully configured the backend \"http\"! Terraform will automatically\nuse this backend unless the backend configuration changes.\u001b[0m\n\n\u001b[0m\u001b[1mInitializing provider plugins...\u001b[0m\n- Checking for available provider plugins...\n- Downloading plugin for provider \"docker\" (terraform-providers/docker) 2.6.0...\n\nThe following providers do not have any version constraints in configuration,\nso the latest version was installed.\n\nTo prevent automatic upgrades to new major versions that may contain breaking\nchanges, it is recommended to add version = \"...\" constraints to the\ncorresponding provider blocks in configuration, with the constraint strings\nsuggested below.\n\n* provider.docker: version = \"~> 2.6\"\n\n\u001b[0m\u001b[1m\u001b[32mTerraform has been successfully initialized!\u001b[0m\u001b[32m\u001b[0m\n\u001b[0m\u001b[1mdocker_image.mongo: Creating...\u001b[0m\u001b[0m\n\u001b[0m\u001b[1mdocker_image.mongo: Creation complete after 0s [id=sha256:5976dac61f4fb85c1a2d1f7c717600f9c78fb02badba6b3c5961a4091ef75905mongo]\u001b[0m\u001b[0m\n\u001b[0m\u001b[1mdocker_container.mongo: Creating...\u001b[0m\u001b[0m\n\u001b[0m\u001b[1mdocker_container.mongo: Creation complete after 1s [id=b247c86c6cee2c3ad6e9565c48cae29877374d07f7c6be5f42489002bdf66b42]\u001b[0m\u001b[0m\n\u001b[0m\u001b[1m\u001b[32m\nApply complete! Resources: 2 added, 0 changed, 0 destroyed.\u001b[0m\n\u001b[0m\u001b[1m\u001b[32m\nOutputs:\n\ndocker_container_name = test\u001b[0m\n",
        "_class" : "io.gaia_app.stacks.bo.Step"
    },
    {
        "_id" : "0ddcb316-8681-4416-bc59-f3e7d47836f1",
        "jobId" : "5e856dc7-6bed-465f-abf1-02980206ab2a",
        "startDateTime" : ISODate("2020-02-05T07:01:30.875Z"),
        "endDateTime" : ISODate("2020-02-05T07:01:37.393Z"),
        "executionTime" : NumberLong(6518),
        "type" : "PLAN",
        "status" : "FINISHED",
        "logs" : "",
        "plan": {
          "$ref" : "plan",
          "$id" : "1f38d7e3-b7d5-4bbb-9a75-1c1ea35dfee9"
        },
        "_class" : "io.gaia_app.stacks.bo.Step"
    },
    {
        "_id" : "e294e7a2-abb7-4c0b-9102-5fb72ad42292",
        "jobId" : "5e856dc7-6bed-465f-abf1-02980206ab2a",
        "startDateTime" : ISODate("2020-02-05T07:02:13.086Z"),
        "endDateTime" : ISODate("2020-02-05T07:02:18.625Z"),
        "executionTime" : NumberLong(5539),
        "type" : "APPLY",
        "status" : "FINISHED",
        "logs" : "using image hashicorp/terraform:latest\ncloning https://oauth2:[MASKED]@github.com/juwit/terraform-docker-mongo\nCloning into 'module'...\ngenerating backend configuration\nTerraform v0.12.20\n\n\u001b[0m\u001b[1mInitializing the backend...\u001b[0m\n\u001b[0m\u001b[32m\nSuccessfully configured the backend \"http\"! Terraform will automatically\nuse this backend unless the backend configuration changes.\u001b[0m\n\n\u001b[0m\u001b[1mInitializing provider plugins...\u001b[0m\n- Checking for available provider plugins...\n- Downloading plugin for provider \"docker\" (terraform-providers/docker) 2.6.0...\n\nThe following providers do not have any version constraints in configuration,\nso the latest version was installed.\n\nTo prevent automatic upgrades to new major versions that may contain breaking\nchanges, it is recommended to add version = \"...\" constraints to the\ncorresponding provider blocks in configuration, with the constraint strings\nsuggested below.\n\n* provider.docker: version = \"~> 2.6\"\n\n\u001b[0m\u001b[1m\u001b[32mTerraform has been successfully initialized!\u001b[0m\u001b[32m\u001b[0m\n\u001b[0m\u001b[1mdocker_image.mongo: Refreshing state... [id=sha256:5976dac61f4fb85c1a2d1f7c717600f9c78fb02badba6b3c5961a4091ef75905mongo]\u001b[0m\n\u001b[0m\u001b[1mdocker_container.mongo: Refreshing state... [id=b247c86c6cee2c3ad6e9565c48cae29877374d07f7c6be5f42489002bdf66b42]\u001b[0m\n\u001b[0m\u001b[1mdocker_container.mongo: Creating...\u001b[0m\u001b[0m\n\u001b[0m\u001b[1mdocker_container.mongo: Creation complete after 0s [id=330b26c77d65e106df926454f9c3c3448f30c4a79062daefe66fe1d4a8f2970e]\u001b[0m\u001b[0m\n\u001b[0m\u001b[1m\u001b[32m\nApply complete! Resources: 1 added, 0 changed, 0 destroyed.\u001b[0m\n\u001b[0m\u001b[1m\u001b[32m\nOutputs:\n\ndocker_container_name = local-mongo\u001b[0m\n",
        "_class" : "io.gaia_app.stacks.bo.Step"
    }
]);
