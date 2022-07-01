gaia = db.getSiblingDB('gaia');
gaia.terraformModule.drop();
gaia.terraformModule.insert([
    {
        "_id": "e01f9925-a559-45a2-8a55-f93dc434c676",
        "name": "terraform-docker-mongo",
        "description": "A sample terraform 🌍 module for running a mongodb 🍃 database inside a docker 🐳 container",
        "gitRepositoryUrl": "https://github.com/juwit/terraform-docker-mongo.git",
        "directory": null,
        "gitBranch": "master",
        "terraformImage": {
            "repository": "hashicorp/terraform",
            "tag": "0.11.14"
        },
        "authorizedOrganizations": [{"$ref": "organization", "$id": "Ze Organization"}],
        "moduleMetadata": {
            "createdAt": "2020-08-07T14:32:22.015+00:00",
            "createdBy": {"$ref": "user", "$id": "admin"}
        },
        "registryDetails": {
            "registryType": "GITHUB",
            "projectId": "juwit/terraform-docker-mongo"
        },
        "variables": [
            {
                "name": "mongo_container_name",
                "description": "the name of the docker container",
                "defaultValue": null,
                "type": "string",
                "editable": true
            },
            {
                "name": "mongo_exposed_port",
                "description": "the exposed port of the mongo container",
                "defaultValue": 27017,
                "type": "number",
                "editable": true
            }
        ]
    },
    {
        "_id": "845543d0-20a5-466c-8978-33c9a4661606",
        "name": "terraform-docker-mongo-limited",
        "description": "A module only visible by admin and organization Not Ze Organization",
        "gitRepositoryUrl": "https://github.com/juwit/terraform-docker-mongo.git",
        "directory": null,
        "gitBranch": "master",
        "terraformImage": {
            "repository": "hashicorp/terraform",
            "tag": "0.11.14"
        },
        "authorizedOrganizations": [{"$ref": "organization", "$id": "Not Ze Organization"}],
        "moduleMetadata": {
            "createdBy": {"$ref": "user", "$id": "admin"}
        },
        "registryDetails": {
            "registryType": "GITHUB",
            "projectId": "juwit/terraform-docker-mongo"
        },
        "variables": []
    },
    {
        "_id": "b39ccd07-80f5-455f-a6b3-b94f915738c4",
        "name": "terraform-docker-mongo-with-validation",
        "description": "A sample terraform 🌍 module for running a mongodb 🍃 database inside a docker 🐳 container",
        "gitRepositoryUrl": "https://github.com/juwit/terraform-docker-mongo.git",
        "directory": null,
        "gitBranch": "master",
        "terraformImage": {
            "repository": "hashicorp/terraform",
            "tag": "0.11.14"
        },
        "authorizedOrganizations": [{"$ref": "organization", "$id": "Ze Organization"}],
        "moduleMetadata": {
            "createdBy": {"$ref": "user", "$id": "admin"}
        },
        "registryDetails": {
            "registryType": "GITHUB",
            "projectId": "juwit/terraform-docker-mongo"
        },
        "variables": [
            {
                "name": "mongo_container_name",
                "description": "the name of the docker container",
                "defaultValue": null,
                "editable": true,
                "type": "string",
                "mandatory": true
            },
            {
                "name": "mongo_exposed_port",
                "description": "the exposed port of the mongo container",
                "defaultValue": 27017,
                "editable": true,
                "type": "number",
                "validationRegex": "\\d{3,6}"
            }
        ]
    }
]);
