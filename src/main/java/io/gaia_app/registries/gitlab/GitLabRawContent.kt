package io.gaia_app.registries.gitlab

import io.gaia_app.registries.RegistryRawContent
import io.gaia_app.registries.RegistryType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GitLabRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITLAB, restTemplate)
