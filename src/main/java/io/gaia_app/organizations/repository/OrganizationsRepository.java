package io.gaia_app.organizations.repository;

import io.gaia_app.organizations.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationsRepository extends MongoRepository<Organization, String> {
}
