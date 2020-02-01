package io.codeka.gaia.registries.gitlab

import io.codeka.gaia.registries.RegistryRawContent
import io.codeka.gaia.registries.RegistryType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GitLabRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITLAB, restTemplate)