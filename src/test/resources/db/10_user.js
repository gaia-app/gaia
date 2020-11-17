gaia = db.getSiblingDB('gaia');
gaia.user.drop();
gaia.user.insert([
    {
        "_id": "admin",
        "team": {"$ref": "team", "$id": "Ze Team"},
        "isAdmin": true
    },
    {
        "_id": "Mary J",
        "team": {"$ref": "team", "$id": "Not Ze Team"}
    }
    ,
    {
        "_id": "Darth Vader"
    },
    {
        "_id": "Luke Skywalker"
    },
    {
        "_id": "selmak",
        "oAuth2User": {
            "provider": "github",
            "token": "Tok'ra"
        }
    }
]);
