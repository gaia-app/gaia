package io.gaia_app.test;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.vault.VaultContainer;

public class VaultContainerTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final VaultContainer vaultContainer = new VaultContainer<>(DockerImageName.parse("vault").withTag("1.1.3")).withVaultToken("admin123");

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        vaultContainer.start();
        TestPropertyValues.of(
            "gaia.vault.uri=http://localhost:" + vaultContainer.getMappedPort(8200),
            "gaia.vault.authentication.token=admin123")
            .applyTo(configurableApplicationContext.getEnvironment());
    }
}
