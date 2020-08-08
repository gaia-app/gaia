package io.gaia_app.vault

import io.gaia_app.credentials.CredentialsRepository
import io.gaia_app.credentials.VaultAWSCredentials
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.header
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.vault.core.VaultTemplate
import org.springframework.web.client.RestTemplate

@RestClientTest(components = [VaultCredentialsService::class, VaultConfiguration::class, VaultEncryptionService::class],
properties = ["gaia.vault.enabled=true",
"gaia.vault.authentication.token=vault-token",
"gaia.vault.uri=http://vault-host:8200"
])
@AutoConfigureWebClient(registerRestTemplate = true)
class VaultCredentialsServiceIT {

    @Autowired
    lateinit var credentialsService: VaultCredentialsService

    @MockBean
    lateinit var credentialsRepository: CredentialsRepository

    @Autowired
    lateinit var vaultTemplate: VaultTemplate

    @Test
    fun `loadAWSCredentialsFromVault should call vault aws secret engine`(){
        // given
        val jsonResponse = """{
                                  "request_id": "00000000-0000-0000-0000-00000000",
                                  "lease_id": "aws-gaia/creds/gaia-role/THISISALEASEID",
                                  "renewable": true,
                                  "lease_duration": 2764800,
                                  "data": {
                                    "access_key": "THISISANACCESSKEY",
                                    "secret_key": "7H1515453Cr374CC355K3Y",
                                    "security_token": null
                                  },
                                  "wrap_info": null,
                                  "warnings": null,
                                  "auth": null
                                }""".trimIndent()
        // binding vaultTemplate internal RestTemplates to the MockServer
        val sessionServer = MockRestServiceServer.createServer(ReflectionTestUtils.getField(vaultTemplate, "sessionTemplate") as RestTemplate)

        sessionServer.expect(requestTo("http://vault-host:8200/v1/aws-gaia/creds/gaia-role"))
            .andExpect(header("X-Vault-Token", "vault-token"))
            .andRespond(MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON))

        // when
        val vaultAWSCredentials = VaultAWSCredentials("/aws-gaia/", "gaia-role")
        val credentials = credentialsService.loadAWSCredentialsFromVault(vaultAWSCredentials)

        // then
        assertThat(credentials.accessKey).isEqualTo("THISISANACCESSKEY")
        assertThat(credentials.secretKey).isEqualTo("7H1515453Cr374CC355K3Y")
    }

}
