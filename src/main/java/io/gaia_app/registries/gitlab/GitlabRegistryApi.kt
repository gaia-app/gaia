package io.gaia_app.registries.gitlab

import com.fasterxml.jackson.annotation.JsonAlias
import io.gaia_app.registries.AbstractRegistryApi
import io.gaia_app.registries.RegistryType
import io.gaia_app.registries.SourceRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URLEncoder
import java.nio.charset.Charset

@Service
class GitlabRegistryApi(restTemplate: RestTemplate): AbstractRegistryApi<GitlabRepository>(
        restTemplate,
        RegistryType.GITLAB,
        GitlabRepository::class.java,
        Array<GitlabRepository>::class.java) {

    override fun getRepository(projectId: String): GitlabRepository {
        return super.getRepository(URLEncoder.encode(projectId, Charset.defaultCharset()))
    }
}

/**
 * Gitlab source repository implementation
 */
data class GitlabRepository(
        @JsonAlias("id") override val id: String,
        @JsonAlias("path_with_namespace") override val fullName: String,
        @JsonAlias("web_url") override val htmlUrl: String): SourceRepository
