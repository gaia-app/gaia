package io.codeka.gaia.registries.github

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
class GithubRegistryApi(restTemplate: RestTemplate): RegistryApi(RegistryType.GITHUB, restTemplate){

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

    override fun getRepositories(user: User): List<String> {
        // fetching repositories
        val url = "https://api.github.com/user/repos?visibility=public"

        val token = user.oAuth2User?.token!!

        val repos = callWithAuth(url, token, Array<GithubRepository>::class.java)

        return repos.map { it.fullName }.toList()
    }

    fun getRepository(user: User, owner: String, repo: String): GithubRepository {
        // fetching repositories
        val url = "https://api.github.com/repos/$owner/$repo"

        val token = user.oAuth2User?.token!!

        return callWithAuth(url, token, GithubRepository::class.java)
    }

    fun getFileContent(user: User, projectId: String, filename: String): String {
        val url = "https://api.github.com/repos/$projectId/contents/$filename?ref=master";

        val token = user.oAuth2User?.token!!;

        val file = callWithAuth(url, token, RegistryFile::class.java)

        // removing trailing \n
        println(file.content.replace("\n", ""))
        return String(Base64.getDecoder().decode(file.content.replace("\n", "")))
    }

}

data class GithubRepository(
        @JsonProperty("full_name") val fullName: String,
        @JsonProperty("html_url") val htmlUrl: String)