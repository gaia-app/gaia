package io.codeka.gaia.registries

import io.codeka.gaia.teams.User
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.http.HttpClient
import java.util.*

abstract class AbstractRegistryApi<K: SourceRepository>(val restTemplate: RestTemplate,
                                                        private val registryType: RegistryType,
                                                        private val registryFileClass: Class<K>,
                                                        private val registryListFileClass: Class<Array<K>>): RegistryApi<K> {

    private fun <T> callWithAuth(url: String, token: String, responseType: Class<T>): T? {
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $token")

        val requestEntity = HttpEntity<Any>(headers)

        try{
            val response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                responseType)
            return response.body
        }
        catch (e:HttpClientErrorException){
            // in case of 404, returns null as an empty body
            return null
        }

    }

    override fun getRepositories(user: User): List<K> {
        // fetching repositories
        val url = registryType.repositoriesUrl

        val token = user.oAuth2User?.token!!

        return callWithAuth(url, token, registryListFileClass)?.toList() ?: emptyList()
    }

    override fun getRepository(user: User, projectId: String): K {
        // fetching repositories
        val url = registryType.repositoryUrl.format(projectId)

        val token = user.oAuth2User?.token!!

        return callWithAuth(url, token, registryFileClass)!!
    }

    override fun getFileContent(user: User, projectId: String, filename: String): String {
        val url = registryType.fileContentUrl.format(projectId, filename)

        val token = user.oAuth2User?.token!!

        val file = callWithAuth(url, token, RegistryFile::class.java)

        // removing trailing \n
        return String(Base64.getDecoder().decode(file?.content?.replace("\n", "") ?: ""))
    }
}
