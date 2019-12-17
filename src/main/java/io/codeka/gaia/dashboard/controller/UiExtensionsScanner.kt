package io.codeka.gaia.dashboard.controller

import org.springframework.core.io.Resource
import org.springframework.core.io.support.ResourcePatternResolver

class UiExtensionsScanner(private val resolver: ResourcePatternResolver) {

    fun scan(vararg locations: String): List<UiExtension> {
        return locations
                .map { resolveAsset(it) }
                .filter { it.isReadable }
                .map { getResourcePath(it) }
                .map { UiExtension(it) }
    }

    /**
     * Resolves a wildcard location to a classpath Resource
     */
    private fun resolveAsset(location: String): Resource {
        return resolver.getResources(location).single()
    }

    /**
     * Get a resource path to be included in html (from /webjars)
     */
    private fun getResourcePath(resource: Resource): String {
        return resource.url.toString().replaceBeforeLast("/webjars","")
    }

}