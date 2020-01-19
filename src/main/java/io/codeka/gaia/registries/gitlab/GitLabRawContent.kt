package io.codeka.gaia.registries.gitlab

import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.RegistryRawContent
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.regex.Pattern

@Component
class GitLabRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITLAB, restTemplate)