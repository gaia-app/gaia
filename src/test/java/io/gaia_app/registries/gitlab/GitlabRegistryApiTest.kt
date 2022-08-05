package io.gaia_app.registries.gitlab

import com.fasterxml.jackson.databind.ObjectMapper
import io.gaia_app.registries.RegistryFile
import io.gaia_app.organizations.OAuth2User
import io.gaia_app.organizations.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.header
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.nio.charset.Charset
import java.util.Base64


@RestClientTest(GitlabRegistryApi::class)
@AutoConfigureWebClient(registerRestTemplate = true)
class GitlabRegistryApiTest{

    @Autowired
    lateinit var gitlabRegistryApi: GitlabRegistryApi

    @Autowired
    lateinit var server: MockRestServiceServer

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `getRepositories() should call the user repos gitlab api`() {
        // given
        val sampleResult = GitlabRepository("15689","Group / repository","https://gitlab.com/group/repository")
        val sampleListResult = listOf(sampleResult)
        val listDetailsString = objectMapper.writeValueAsString(sampleListResult)

        server.expect(requestTo("https://gitlab.com/api/v4/projects?visibility=public&owned=true"))
                .andExpect(header("Authorization", "Bearer johnstoken"))
                .andRespond(withSuccess(listDetailsString, MediaType.APPLICATION_JSON))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("gitlab", "johnstoken", null)

        // when
        val repositories = gitlabRegistryApi.getRepositories(john)

        // then
        assertThat(repositories).containsExactly(sampleResult)
    }

    @Test
    fun `getRepository() should call the repos gitlab api`() {
        // given
        val sampleResult = GitlabRepository("15689","Group / repository","https://gitlab.com/group/repository")
        val detailsString = objectMapper.writeValueAsString(sampleResult)
        server.expect(requestTo("https://gitlab.com/api/v4/projects/15689"))
                .andExpect(header("Authorization", "Bearer johnstoken"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("gitlab", "johnstoken", null)

        // when
        val repositories = gitlabRegistryApi.getRepository(john, "15689")

        // then
        assertThat(repositories).isEqualTo(sampleResult)
    }

    @Test
    fun `getFileContent() should call the contents gitlab api`() {
        // given
        val readmeContent = """
            # Sample README.md
        """
        val sampleResult = RegistryFile(content = Base64.getEncoder().encodeToString(readmeContent.toByteArray(Charset.defaultCharset())))
        val detailsString = objectMapper.writeValueAsString(sampleResult)
        server.expect(requestTo("https://gitlab.com/api/v4/projects/15689/repository/files/README.md?ref=master"))
                .andExpect(header("Authorization", "Bearer johnstoken"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("gitlab", "johnstoken", null)

        // when
        val content = gitlabRegistryApi.getFileContent(john, "15689", "README.md")

        // then
        assertThat(content).isEqualTo(readmeContent)
    }

}
