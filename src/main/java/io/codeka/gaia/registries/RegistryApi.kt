package io.codeka.gaia.registries

import io.codeka.gaia.teams.User
import org.springframework.web.client.RestTemplate

abstract class RegistryApi(private val registryType: RegistryType, val restTemplate: RestTemplate) {

    abstract fun getRepositories(user: User) : List<String>

}