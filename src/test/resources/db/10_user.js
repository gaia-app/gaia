gaia = db.getSiblingDB('gaia');
gaia.user.drop();
gaia.user.insert([
    {
        "_id": "admin",
        "organization": {"$ref": "organization", "$id": "Ze Organization"},
        "isAdmin": true,
        // admin123
        "password": "$2a$10$hr8QjaJ0ync5OQoCtoown.XKplCdhAnyfkWaCf9fto9Cd4470hO/e"
    },
    {
        "_id": "Mary J",
        "organization": {"$ref": "organization", "$id": "Not Ze Organization"}
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
