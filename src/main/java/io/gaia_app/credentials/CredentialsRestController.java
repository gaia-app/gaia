package io.gaia_app.credentials;

import io.gaia_app.teams.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/credentials")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class CredentialsRestController {

    private CredentialsRepository credentialsRepository;

    public CredentialsRestController(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @GetMapping
    public List<EmptyCredentials> getAllCredentials(User user) {
        return this.credentialsRepository.findAllByCreatedBy(user);
    }

    @GetMapping("/{id}")
    public Credentials getCredentials(@PathVariable String id, User user) {
        return this.credentialsRepository.findByIdAndCreatedBy(id, user)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
    }

    @PostMapping
    public Credentials createCredentials(@RequestBody Credentials credentials) {
        return this.credentialsRepository.save(credentials);
    }

    @PutMapping("/{id}")
    public Credentials updateCredentials(@RequestBody Credentials credentials, @PathVariable String id, User user) {
        // checking if we have the rights on this credentials
        if (this.credentialsRepository.findByIdAndCreatedBy(id, user).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return this.credentialsRepository.save(credentials);
    }

    @DeleteMapping("/{id}")
    public void deleteCredentials(@PathVariable String id, User user) {
        // checking if we have the rights on this credentials
        if (this.credentialsRepository.findByIdAndCreatedBy(id, user).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        this.credentialsRepository.deleteById(id);
    }
}
