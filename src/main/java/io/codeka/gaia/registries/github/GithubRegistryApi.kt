package io.codeka.gaia.registries.github

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryFile
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.gitlab.GitlabRepository
import io.codeka.gaia.teams.User
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class GithubRegistryApi(val restTemplate: RestTemplate): RegistryApi<GithubRepository> {

    fun <T> callWithAuth(url: String, token: String, responseType: Class<T>): T{
        val headers = HttpHeaders()
        if(token != null) {
            headers.add("Authorization", "Bearer $token")
        }

        val requestEntity = HttpEntity<Any>(headers)

        val response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                responseType)
        if(response.statusCode == HttpStatus.OK) {
            return response.body
        }
        else {
            TODO("error code mgmt")
        }
    }

    override fun getRepositories(user: User): List<GithubRepository> {
        // fetching repositories
        val url = RegistryType.GITHUB.repositoriesUrl

        val token = user.oAuth2User?.token!!

        val repos = callWithAuth(url, token, Array<GithubRepository>::class.java)

        return repos.toList()
    }

    override fun getRepository(user: User, projectId: String): GithubRepository {
        // fetching repositories
        val url = RegistryType.GITHUB.repositoryUrl.format(projectId)

        val token = user.oAuth2User?.token!!

        return callWithAuth(url, token, GithubRepository::class.java)
    }

    override fun getFileContent(user: User, projectId: String, filename: String): String {
        val url = RegistryType.GITHUB.fileContentUrl.format(projectId, filename)

        val token = user.oAuth2User?.token!!

        val file = callWithAuth(url, token, RegistryFile::class.java)

        // removing trailing \n
        return String(Base64.getDecoder().decode(file.content.replace("\n", "")))
    }

}

data class GithubRepository(
        @JsonAlias("full_name") val fullName: String,
        @JsonAlias("html_url") val htmlUrl: String)