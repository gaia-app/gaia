gaia = db.getSiblingDB('gaia');
gaia.user.drop();
gaia.user.insert([
    {
        "_id": "admin",
        "team": {"$ref": "team", "$id": "Ze Team"}
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
        "_id": "selmak",
        "oAuth2User": {
            "provider": "github",
            "token": "Tok'ra"
        }
    }
]);
