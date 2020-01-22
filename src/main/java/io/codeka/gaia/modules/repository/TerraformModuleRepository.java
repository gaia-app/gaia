package io.codeka.gaia.modules.repository;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerraformModuleRepository extends MongoRepository<TerraformModule, String> {

    int countByModuleMetadataCreatedBy(User user);

    int countByAuthorizedTeamsContaining(Team team);

    int countByAuthorizedTeamsContainingOrModuleMetadataCreatedBy(Team team, User user);

    List<TerraformModule> findAllByAuthorizedTeamsContainingOrModuleMetadata_CreatedBy(Team team, User user);

    Optional<TerraformModule> findByIdAndAuthorizedTeamsContaining(String id, Team team);

    List<TerraformModule> findAllByModuleMetadataCreatedByOrAuthorizedTeamsContaining(User user, Team team);

    List<TerraformModule> findAllByModuleMetadataCreatedBy(User user);
}
