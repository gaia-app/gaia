package io.codeka.gaia.registries.gitlab

import io.codeka.gaia.registries.RegistryType
import io.codeka.gaia.registries.RegistryRawContent
import org.springframework.web.client.RestTemplate
import java.util.regex.Pattern

class GitLabRawContent(restTemplate: RestTemplate) : RegistryRawContent(RegistryType.GITLAB, restTemplate) {

    override val pattern: Pattern = Pattern.compile("^(http[s]?://[www.]?gitlab.*).git$")

}