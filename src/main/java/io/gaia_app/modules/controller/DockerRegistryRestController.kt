package io.gaia_app.modules.controller

import io.gaia_app.modules.api.DockerRegistryApi
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/docker")
@Secured
class DockerRegistryRestController(private val dockerRegistryApi: DockerRegistryApi) {

    @GetMapping("/repositories")
    fun listRepositoriesByName(@RequestParam name: String) = this.dockerRegistryApi.findRepositoriesByName(name)

    @GetMapping(
            "/repositories/{repository}/tags",
            "/repositories/{owner}/{repository}/tags")
    fun listTagsByName(
            @PathVariable repository: String,
            @PathVariable(required = false) owner: String?,
            @RequestParam name: String
    ) = this.dockerRegistryApi.findTagsByName(name, "${owner ?: "library"}/$repository")

}

