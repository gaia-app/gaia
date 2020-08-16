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
    fun `toEnv() for AzureRMCredentials should return ARM_CLIENT_ID and ARM_CLIENT_SECRET`() {
        assertThat(AzureRMCredentials("clientId", "secret").toEnv())
            .containsExactly("ARM_CLIENT_ID=clientId", "ARM_CLIENT_SECRET=secret")
    }

    @Test
    fun `toEnv() for GoogleCredentials should return GOOGLE_CREDENTIALS`() {
        assertThat(GoogleCredentials("jsonContent").toEnv())
            .containsExactly("GOOGLE_CREDENTIALS=jsonContent")
    }
}
