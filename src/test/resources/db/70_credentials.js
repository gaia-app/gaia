gaia = db.getSiblingDB('gaia');
gaia.credentials.drop();
gaia.credentials.insert([
  {
    "_id": "1",
    "provider": "aws",
    "name": "Holocron",
    "accessKey": "DEATH_STAR_KEY",
    "secretKey": "DEATH_STAR_SECRET",
    "createdBy": {"$ref": "user", "$id": "Darth Vader"},
    "_class": "io.gaia_app.credentials.AWSCredentials"
  }
]);
