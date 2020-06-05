package io.gaia_app.client.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.info.GitProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/build-info")
class BuildInfoRestController(
    @Autowired(required = false) var buildProperties: BuildProperties? = null,
    @Autowired(required = false) var gitProperties: GitProperties? = null) {

    @GetMapping
    fun infos(): Map<String, String?>? =
        if (buildProperties == null && gitProperties == null) {
            null
        } else {
            mapOf(
                "version" to buildProperties?.version,
                "commitId" to gitProperties?.shortCommitId)
        }

}
