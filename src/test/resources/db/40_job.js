gaia = db.getSiblingDB('gaia');
gaia.job.drop();
gaia.job.insert([
    {
        "_id" : "32e4c316-287a-48ba-9c49-dd1b1a0204c8",
        "stackId" : "de28a01f-257a-448d-8e1b-00e4e3a41db2",
        "scheduleTime" : ISODate("2020-02-03T18:03:12.227Z"),
        "startDateTime" : ISODate("2020-02-03T18:03:12.227Z"),
        "endDateTime" : ISODate("2020-02-03T18:03:24.323Z"),
        "type" : "RUN",
        "status" : "APPLY_FINISHED",
        "terraformImage" : {
            "repository" : "hashicorp/terraform",
            "tag" : "latest"
        },
        "steps" : [
            {
                "$ref" : "step",
                "$id" : "b3bcea1e-3fb8-4443-bd8b-1caab78e6922"
            },
            {
                "$ref" : "step",
                "$id" : "ec9d0334-0055-4727-aa79-731ea79326e3"
            }
        ],
        "user" : {
            "$ref" : "user",
            "$id" : "selmak"
        },
        "_class" : "io.gaia_app.stacks.bo.Job"
    },
    {
        "_id" : "5e856dc7-6bed-465f-abf1-02980206ab2a",
        "stackId" : "de28a01f-257a-448d-8e1b-00e4e3a41db2",
        "scheduleTime" : ISODate("2020-02-05T07:01:30.875Z"),
        "startDateTime" : ISODate("2020-02-05T07:01:30.875Z"),
        "endDateTime" : ISODate("2020-02-05T07:02:18.625Z"),
        "type" : "RUN",
        "status" : "APPLY_FINISHED",
        "terraformImage" : {
            "repository" : "hashicorp/terraform",
            "tag" : "latest"
        },
        "steps" : [
            {
                "$ref" : "step",
                "$id" : "0ddcb316-8681-4416-bc59-f3e7d47836f1"
            },
            {
                "$ref" : "step",
                "$id" : "e294e7a2-abb7-4c0b-9102-5fb72ad42292"
            }
        ],
        "user" : {
            "$ref" : "user",
            "$id" : "selmak"
        },
        "_class" : "io.gaia_app.stacks.bo.Job"
    }

]);
