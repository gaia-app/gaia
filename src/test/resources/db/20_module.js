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
        "authorizedTeams": [{"$ref": "team", "$id": "Ze Team"}],
        "moduleMetadata": {
            "createdBy": {"$ref": "user", "$id": "admin"}
        },
        "estimatedMonthlyCost": 0.99,
        "estimatedMonthlyCostDescription": "Kamoulox",
        "variables": [
            {
                "name": "mongo_container_name",
                "description": "the name of the docker container",
                "defaultValue": null,
                "editable": true
            },
            {
                "name": "mongo_exposed_port",
                "description": "the exposed port of the mongo container",
                "defaultValue": 27017,
                "editable": true
            }
        ]
    },
    {
        "_id": "845543d0-20a5-466c-8978-33c9a4661606",
        "name": "terraform-docker-mongo-limited",
        "description": "A module only visible by admin and team Not Ze Team",
        "gitRepositoryUrl": "https://github.com/juwit/terraform-docker-mongo.git",
        "directory": null,
        "gitBranch": "master",
        "terraformImage": {
            "repository": "hashicorp/terraform",
            "tag": "0.11.14"
        },
        "authorizedTeams": [{"$ref": "team", "$id": "Not Ze Team"}],
        "moduleMetadata": {
            "createdBy": {"$ref": "user", "$id": "admin"}
        },
        "estimatedMonthlyCost": 9.99,
        "estimatedMonthlyCostDescription": "Not Ze Team! Not Ze Team!",
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
        "authorizedTeams": [{"$ref": "team", "$id": "Ze Team"}],
        "moduleMetadata": {
            "createdBy": {"$ref": "user", "$id": "admin"}
        },
        "estimatedMonthlyCost": 0.99,
        "estimatedMonthlyCostDescription": "Kamoulox",
        "variables": [
            {
                "name": "mongo_container_name",
                "description": "the name of the docker container",
                "defaultValue": null,
                "editable": true,
                "mandatory": true
            },
            {
                "name": "mongo_exposed_port",
                "description": "the exposed port of the mongo container",
                "defaultValue": 27017,
                "editable": true,
                "validationRegex": "\\d{3,6}"
            }
        ]
    }
]);