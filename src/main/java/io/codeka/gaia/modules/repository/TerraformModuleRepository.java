package io.codeka.gaia.modules.repository;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.teams.bo.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerraformModuleRepository extends MongoRepository<TerraformModule, String> {

    int countByAuthorizedTeamsContaining(Team team);

    List<TerraformModule> findAllByAuthorizedTeamsContaining(Team team);

    Optional<TerraformModule> findByIdAndAuthorizedTeamsContaining(String id, Team team);
}
