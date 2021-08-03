package io.gaia_app.credentials

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CredentialsTest {

    @Test
    fun `toEnv() for AWSCredentials should return AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY`() {
        assertThat(AWSCredentials("access", "secret").toEnv())
            .containsExactly("AWS_ACCESS_KEY_ID=access", "AWS_SECRET_ACCESS_KEY=secret")
    }

    @Test
    fun `toEnv() for AzureRMCredentials should return ARM_CLIENT_ID, ARM_CLIENT_SECRET, ARM_SUBSCRIPTION_ID, ARM_TENANT_ID, ARM_ACCESS_KEY, ARM_ENVIRONMENT`() {
        assertThat(AzureRMCredentials("clientId", "secret", "subscriptionId", "tenantId", "environment", "backendAccessKey").toEnv())
            .containsExactly("ARM_CLIENT_ID=clientId", "ARM_CLIENT_SECRET=secret", "ARM_SUBSCRIPTION_ID=subscriptionId", "ARM_TENANT_ID=tenantId", "ARM_ACCESS_KEY=backendAccessKey", "ARM_ENVIRONMENT=environment")
    }

    @Test
    fun `toEnv() for GoogleCredentials should return GOOGLE_CREDENTIALS and GOOGLE_PROJECT`() {
        assertThat(GoogleCredentials("jsonContent", "projectId").toEnv())
            .containsExactly("GOOGLE_CREDENTIALS=jsonContent", "GOOGLE_PROJECT=projectId")
    }
}
