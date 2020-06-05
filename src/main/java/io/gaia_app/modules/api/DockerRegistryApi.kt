package io.gaia_app.modules.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}

@Repository
class DockerRegistryApi(
        @Value("\${docker.registry.api.url}") private val dockerRegistryApiUrl: String,
        private val restTemplate: RestTemplate) {

    fun findRepositoriesByName(name: String, pageNum: Int = 1, pageSize: Int = 10): List<DockerRegistryRepository> {
        val response = restTemplate.exchange(
                "$dockerRegistryApiUrl/search/repositories?query=$name&page=$pageNum&page_size=$pageSize",
                HttpMethod.GET,
                HttpEntity<Any>(HttpHeaders()),
                typeRef<DockerRegistryResponse<DockerRegistryRepository>>())
        return if (HttpStatus.OK == response.statusCode && null != response.body) {
            response.body!!.results
        } else listOf()
    }

    fun findTagsByName(name: String, repository: String, pageNum: Int = 1, pageSize: Int = 10): List<DockerRegistryRepositoryTag> {
        val response = restTemplate.exchange(
                "$dockerRegistryApiUrl/repositories/$repository/tags?name=$name&page=$pageNum&page_size=$pageSize",
                HttpMethod.GET,
                HttpEntity<Any>(HttpHeaders()),
                typeRef<DockerRegistryResponse<DockerRegistryRepositoryTag>>())
        return if (HttpStatus.OK == response.statusCode && null != response.body) {
            response.body!!.results
        } else listOf()
    }

}
