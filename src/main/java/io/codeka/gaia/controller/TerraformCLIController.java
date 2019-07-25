package io.codeka.gaia.controller;

import io.codeka.gaia.repository.TerraformCLIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TerraformCLIController {

    private TerraformCLIRepository repository;

    @Autowired
    public TerraformCLIController(TerraformCLIRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/terraform-cli/versions")
    public List<String> listCLIVersions() {
        return repository.listCLIVersion();
    }

}
