package io.codeka.gaia.repository;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class TerraformCLIRepository {

    private static final Pattern TERRAFORM_VERSION_PATTERN = Pattern.compile("terraform_([\\d]+.[\\d]+.[\\d]+)");
    private static final Pattern SEMVER_PATTERN = Pattern.compile("([\\d]*).([\\d]*).([\\d]*)");

    private String terraformReleasesUrl;
    private String terraformReleasesVersionMin;
    private RestTemplate restTemplate;

    private class CLIVersion {
        private short major;
        private short minor;
        private short patch;

        private CLIVersion(short major, short minor, short patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }
    }

    @Autowired
    public TerraformCLIRepository(
            @Value("${terraform.releases.url}") String terraformReleasesUrl,
            @Value("${terraform.releases.version.min}") String terraformReleasesVersionMin,
            RestTemplate restTemplate) {
        this.terraformReleasesUrl = terraformReleasesUrl;
        this.terraformReleasesVersionMin = terraformReleasesVersionMin;
        this.restTemplate = restTemplate;
    }

    /**
     * Returns acceptable Terraform CLI versions by scanning HashiCorp releases url for terraform.
     * Versions lower than the one in parameter and non final versions are ignored.
     *
     * @return
     */
    public List<String> listCLIVersion() {
        var response = restTemplate.exchange(
                terraformReleasesUrl,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK && StringUtils.isNotBlank(response.getBody())) {
            return TERRAFORM_VERSION_PATTERN
                    .matcher(response.getBody())
                    .results()
                    .map(m -> m.group(1))
                    .distinct() // to remove duplicate generating by alpha, beta, rc...
                    .filter(this::isVersionAccepted)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private boolean isVersionAccepted(String version) {
        var currentCLI = getCLIVersion(version);
        if (currentCLI.isEmpty()) {
            return false;
        }
        var minCLI = getCLIVersion(terraformReleasesVersionMin);
        if (minCLI.isEmpty()) {
            return false;
        }

        // greater major
        if (currentCLI.get().major > minCLI.get().major) {
            return true;
        }
        // greater minor
        if (currentCLI.get().major == minCLI.get().major &&
                currentCLI.get().minor > minCLI.get().minor) {
            return true;
        }
        // greater or equal patch
        return currentCLI.get().major == minCLI.get().major &&
                currentCLI.get().minor == minCLI.get().minor &&
                currentCLI.get().patch >= minCLI.get().patch;
    }

    private Optional<CLIVersion> getCLIVersion(String version) {
        var matcher = SEMVER_PATTERN.matcher(version);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        var result = new CLIVersion(Short.valueOf(matcher.group(1)),
                Short.valueOf(matcher.group(2)),
                Short.valueOf(matcher.group(3)));
        return Optional.of(result);
    }

}
