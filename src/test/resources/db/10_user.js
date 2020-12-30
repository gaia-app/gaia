gaia = db.getSiblingDB('gaia');
gaia.user.drop();
gaia.user.insert([
    {
        "_id": "admin",
        "team": {"$ref": "team", "$id": "Ze Team"},
        "isAdmin": true,
        // admin123
        "password": "$2a$10$hr8QjaJ0ync5OQoCtoown.XKplCdhAnyfkWaCf9fto9Cd4470hO/e"
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
