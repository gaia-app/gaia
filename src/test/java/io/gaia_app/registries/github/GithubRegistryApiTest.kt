package io.gaia_app.registries.github

import com.fasterxml.jackson.databind.ObjectMapper
import io.gaia_app.registries.RegistryFile
import io.gaia_app.organizations.OAuth2User
import io.gaia_app.organizations.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.header
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.nio.charset.Charset
import java.util.*

@RestClientTest(GithubRegistryApi::class)
@AutoConfigureWebClient(registerRestTemplate = true)
class GithubRegistryApiTest{

    @Autowired
    lateinit var githubRegistryApi: GithubRegistryApi

    @Autowired
    lateinit var server: MockRestServiceServer

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `getRepositories() should call the user repos github api`() {
        // given
        val sampleResult = GithubRepository(fullName = "terraform-aws-modules/terraform-aws-rds", htmlUrl = "https://github.com/terraform-aws-modules/terraform-aws-rds")
        val sampleListResult = listOf(sampleResult)
        val listDetailsString = objectMapper.writeValueAsString(sampleListResult)

        server.expect(requestTo("https://api.github.com/user/repos?visibility=public&per_page=100"))
                .andExpect(header("Authorization", "Bearer johnstoken"))
                .andRespond(withSuccess(listDetailsString, MediaType.APPLICATION_JSON))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("github", "johnstoken", null)

        // when
        val repositories = githubRegistryApi.getRepositories(john)

        // then
        assertThat(repositories).containsExactly(sampleResult)
    }

    @Test
    fun `getRepositories() should return empty list in case of 404`() {
        // given
        server.expect(requestTo("https://api.github.com/user/repos?visibility=public&per_page=100"))
            .andExpect(header("Authorization", "Bearer johnstoken"))
            .andRespond(withStatus(HttpStatus.NOT_FOUND))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("github", "johnstoken", null)

        // when
        val repositories = githubRegistryApi.getRepositories(john)

        // then
        assertThat(repositories).isEmpty()
    }

    @Test
    fun `getRepository() should call the repos github api`() {
        // given
        val sampleResult = GithubRepository(fullName = "terraform-aws-modules/terraform-aws-rds", htmlUrl = "https://github.com/terraform-aws-modules/terraform-aws-rds")
        val detailsString = objectMapper.writeValueAsString(sampleResult)
        server.expect(requestTo("https://api.github.com/repos/terraform-aws-modules/terraform-aws-rds"))
                .andExpect(header("Authorization", "Bearer johnstoken"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("github", "johnstoken", null)

        // when
        val repositories = githubRegistryApi.getRepository(john, "terraform-aws-modules/terraform-aws-rds")

        // then
        assertThat(repositories).isEqualTo(GithubRepository(fullName = "terraform-aws-modules/terraform-aws-rds", htmlUrl = "https://github.com/terraform-aws-modules/terraform-aws-rds"))
    }

    @Test
    fun `getFileContent() should call the contents github api`() {
        // given
        val readmeContent = """
            # Sample README.md
        """
        val sampleResult = RegistryFile(content = Base64.getEncoder().encodeToString(readmeContent.toByteArray(Charset.defaultCharset())))
        val detailsString = objectMapper.writeValueAsString(sampleResult)
        server.expect(requestTo("https://api.github.com/repos/terraform-aws-modules/terraform-aws-rds/contents/README.md"))
                .andExpect(header("Authorization", "Bearer johnstoken"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("github", "johnstoken", null)

        // when
        val content = githubRegistryApi.getFileContent(john, "terraform-aws-modules/terraform-aws-rds", "README.md")

        // then
        assertThat(content).isEqualTo(readmeContent)
    }

    @Test
    fun `getFileContent() should return an empty string when file doesnt exists`() {
        // given
        server.expect(requestTo("https://api.github.com/repos/terraform-aws-modules/terraform-aws-rds/contents/non-existing.md"))
            .andExpect(header("Authorization", "Bearer johnstoken"))
            .andRespond(withStatus(HttpStatus.NOT_FOUND))

        val john = User("john", null)
        john.oAuth2User = OAuth2User("github", "johnstoken", null)

        // when
        val content = githubRegistryApi.getFileContent(john, "terraform-aws-modules/terraform-aws-rds", "non-existing.md")

        // then
        assertThat(content).isEqualTo("")
    }

}
