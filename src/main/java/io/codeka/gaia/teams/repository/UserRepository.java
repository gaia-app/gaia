package io.codeka.gaia.teams.repository;

import io.codeka.gaia.teams.bo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
