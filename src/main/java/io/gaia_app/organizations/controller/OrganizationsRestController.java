package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
import io.gaia_app.organizations.repository.OrganizationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/organizations")
@Secured("ROLE_SUPERADMIN")
public class OrganizationsRestController {

    private OrganizationsRepository organizationsRepository;

    @Autowired
    public OrganizationsRestController(OrganizationsRepository organizationsRepository) {
        this.organizationsRepository = organizationsRepository;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Organization> organizations(User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERADMIN"))) {
            return this.organizationsRepository.findAll();
        }
        else {
            return Collections.singletonList(user.getOrganization());
        }
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization){
        return this.organizationsRepository.save(organization);
    }

    @DeleteMapping("/{organizationId}")
    public void deleteOrganization(@PathVariable String organizationId){
        this.organizationsRepository.deleteById(organizationId);
    }

}
