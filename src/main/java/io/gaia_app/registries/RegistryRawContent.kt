package io.gaia_app.registries

import io.gaia_app.modules.bo.TerraformModule
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.util.*

abstract class RegistryRawContent(private val registryType: RegistryType, private val restTemplate: RestTemplate) {

    open fun matches(module: TerraformModule) = registryType == module.registryDetails?.registryType

    open fun getReadme(module: TerraformModule): Optional<String> {
        // no project details, impossible to load a readme, so returning empty
        module.registryDetails ?: return Optional.empty()

        val token = module.moduleMetadata.createdBy?.oAuth2User?.token

        val headers = HttpHeaders()
        if (token != null) {
            headers.add("Authorization", "Bearer $token")
        }

        val requestEntity = HttpEntity<Any>(headers)

        try {
            val response = restTemplate.exchange(
                this.registryType.readmeUrl.replace("{id}", module.registryDetails.projectId),
                HttpMethod.GET,
                requestEntity,
                RegistryFile::class.java)

            if (response.statusCode == HttpStatus.OK) {
                return Optional.of(String(Base64.getDecoder().decode(response.body?.content?.replace("\n", ""))))
            }
        } catch (e: RestClientException) {
            return Optional.empty()
        }
        return Optional.empty()
    }

}
