let gaia = db.getSiblingDB('gaia');
gaia.organization.drop();
gaia.organization.insert([
    {
        "_id": "Ze Organization"
    },
    {
        "_id": "Not Ze Organization"
    },
    {
        "_id": "Sith"
    }
]);
