let gaia = db.getSiblingDB('gaia');
gaia.team.drop();
gaia.team.insert([
    {
        "_id": "Ze Team"
    },
    {
        "_id": "Not Ze Team"
    },
    {
        "_id": "Sith"
    }
]);