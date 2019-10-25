package io.codeka.gaia.registries.github

import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.RegistryRawContent
import org.springframework.web.client.RestTemplate
import java.util.regex.Pattern

class GitHubRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITHUB, restTemplate) {

    override val pattern: Pattern = Pattern.compile("^http[s]?://[www.]?github.com(.*).git$")

}
