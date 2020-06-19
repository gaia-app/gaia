package io.gaia_app.credentials

import org.springframework.data.mongodb.repository.MongoRepository

interface CredentialsRepository: MongoRepository<Credentials, String>
