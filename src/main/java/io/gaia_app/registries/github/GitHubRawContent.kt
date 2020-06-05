package io.gaia_app.registries.github

import io.gaia_app.registries.RegistryRawContent
import io.gaia_app.registries.RegistryType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GitHubRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITHUB, restTemplate)
