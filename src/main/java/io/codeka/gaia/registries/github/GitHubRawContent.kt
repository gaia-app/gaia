package io.codeka.gaia.registries.github

import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.RegistryRawContent
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.regex.Pattern

@Component
class GitHubRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITHUB, restTemplate)
