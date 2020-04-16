package io.codeka.gaia.client.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ClientForwardController {

    @RequestMapping("/**/{path:[^\\.]*}")
    fun redirect(): String = "forward:/"

}
