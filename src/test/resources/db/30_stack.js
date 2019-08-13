gaia = db.getSiblingDB('gaia');
gaia.stack.drop();
gaia.stack.insert([
    {
        "_id": "5a215b6b-fe53-4afa-85f0-a10175a7f264",
        "moduleId": "e01f9925-a559-45a2-8a55-f93dc434c676",
        "name": "mongo-instance-1",
        "description": "first instance of mongo module",
        "variableValues": {
            "mongo_container_name": "test",
            "mongo_exposed_port": "27117"
        },
        "providerSpec": "provider \"docker\" {\n    host = \"unix:///var/run/docker.sock\"\n}",
        "state": "NEW",
        "ownerTeam": {"$ref": "team", "$id": "Ze Team"},
        "estimatedRunningCost": null,
        "createdBy": {"$ref": "user", "$id": "admin"},
        "createdAt": new Date(),
        "updatedBy": null,
        "updatedAt": null
    },
    {
        "_id": "143773fa-4c2e-4baf-a7fb-79d23e01c5ca",
        "moduleId": "e01f9925-a559-45a2-8a55-f93dc434c676",
        "name": "mongo-instance-2",
        "description": "second instance of mongo module",
        "variablesValues": {},
        "providerSpec": null,
        "state": "NEW",
        "ownerTeam": {"$ref": "team", "$id": "Ze Team"},
        "estimatedRunningCost": null,
        "createdBy": {"$ref": "user", "$id": "admin"},
        "createdAt": new Date(),
        "updatedBy": null,
        "updatedAt": null
    },
    {
        "_id": "845543d0-20a5-466c-8978-33c9a4661606",
        "moduleId": "845543d0-20a5-466c-8978-33c9a4661606",
        "name": "mongo-instance-limited",
        "description": "instance of mongo module for team Not Ze Team",
        "variablesValues": {},
        "providerSpec": null,
        "state": "NEW",
        "ownerTeam": {"$ref": "team", "$id": "Not Ze Team"},
        "estimatedRunningCost": null,
        "createdBy": {"$ref": "user", "$id": "Mary J"},
        "createdAt": new Date(),
        "updatedBy": null,
        "updatedAt": null
    }
]);