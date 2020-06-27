package io.gaia_app.credentials;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credentials")
@Secured({"ROLE_USER","ROLE_ADMIN"})
public class CredentialsRestController {

    private CredentialsRepository credentialsRepository;

    public CredentialsRestController(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @GetMapping
    public List<Credentials> getAllCredentials(){
        return this.credentialsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Credentials getCredentials(@PathVariable String id){
        return this.credentialsRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Credentials createCredentials(@RequestBody Credentials credentials){
        return this.credentialsRepository.save(credentials);
    }

    @PutMapping("/{id}")
    public Credentials updateCredentials(@RequestBody Credentials credentials, @PathVariable  String id){
        return this.credentialsRepository.save(credentials);
    }

    @DeleteMapping("/{id}")
    public void deleteCredentials(@PathVariable String id){
        this.credentialsRepository.deleteById(id);
    }
}
