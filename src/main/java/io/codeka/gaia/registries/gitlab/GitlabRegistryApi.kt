package io.codeka.gaia.registries.gitlab

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.codeka.gaia.registries.RegistryApi
import io.codeka.gaia.registries.RegistryFile
import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.teams.User
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class GitlabRegistryApi(val restTemplate: RestTemplate): RegistryApi<GitlabRepository> {

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

    override fun getRepositories(user: User): List<GitlabRepository> {
        // fetching repositories
        val url = "https://gitlab.com/api/v4/projects?visibility=public&owned=true"

        val token = user.oAuth2User?.token!!

        val repos = callWithAuth(url, token, Array<GitlabRepository>::class.java)

        return repos.toList()
    }

    override fun getRepository(user: User, projectId: String): GitlabRepository {
        // fetching repositories
        val url = "https://gitlab.com/api/v4/projects/$projectId"

        val token = user.oAuth2User?.token!!

        return callWithAuth(url, token, GitlabRepository::class.java)
    }

    override fun getFileContent(user: User, projectId: String, filename: String): String {
        val url = "https://gitlab.com/api/v4/projects/$projectId/repository/files/$filename?ref=master";

        val token = user.oAuth2User?.token!!;

        val file = callWithAuth(url, token, RegistryFile::class.java)

        // removing trailing \n
        println(file.content.replace("\n", ""))
        return String(Base64.getDecoder().decode(file.content.replace("\n", "")))
    }

}

data class GitlabRepository(
        @JsonAlias("id") val id: String,
        @JsonAlias("path_with_namespace") val fullName: String,
        @JsonAlias("web_url") val htmlUrl: String)