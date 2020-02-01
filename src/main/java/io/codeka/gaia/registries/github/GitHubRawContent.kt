package io.codeka.gaia.registries.github

import io.codeka.gaia.registries.RegistryRawContent
import io.codeka.gaia.registries.RegistryType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GitHubRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITHUB, restTemplate)
