package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.repository.OrganizationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationsRestController {

    private OrganizationsRepository organizationsRepository;

    @Autowired
    public OrganizationsRestController(OrganizationsRepository organizationsRepository) {
        this.organizationsRepository = organizationsRepository;
    }

    @GetMapping
    public List<Organization> organizations(){
        return this.organizationsRepository.findAll();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public Organization createOrganization(@RequestBody Organization organization){
        return this.organizationsRepository.save(organization);
    }

    @DeleteMapping("/{organizationId}")
    @Secured("ROLE_ADMIN")
    public void deleteOrganization(@PathVariable String organizationId){
        this.organizationsRepository.deleteById(organizationId);
    }

}
