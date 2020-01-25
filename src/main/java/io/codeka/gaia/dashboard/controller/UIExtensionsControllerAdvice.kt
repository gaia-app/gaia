package io.codeka.gaia.dashboard.controller

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

/***
 * A controller advice to inject ui-extensions
 */
@ControllerAdvice
class UIExtensionsControllerAdvice(uiExtensions: List<UiExtension>) {

    private var cssExtensions: List<UiExtension> = uiExtensions.filter { it.resourcePath.endsWith(".css") }

    private var jsExtensions: List<UiExtension> = uiExtensions.filter { it.resourcePath.endsWith(".js") }

    @ModelAttribute(value = "cssExtensions", binding = false)
    fun getCssExtensions() = this.cssExtensions

    @ModelAttribute(value = "jsExtensions", binding = false)
    fun getJsExtensions() = this.jsExtensions

}

data class UiExtension(val resourcePath: String)

@Configuration
open class UIExtensionsConfig(){

    @Bean
    open fun uiExtensions(applicationContext: ApplicationContext): List<UiExtension>? {
        val scanner = UiExtensionsScanner(applicationContext)
        return scanner.scan(
                // CSS
                "classpath*:/**/bootstrap/**/bootstrap.min.css",
                "classpath*:/**/font-awesome/**/all.css",
                "classpath*:/**/bootstrap-vue/**/bootstrap-vue.min.css",
                "classpath*:/**/vue-multiselect/**/vue-multiselect.min.css",

                // JS
                "classpath*:/**/jquery/**/jquery.min.js",
                "classpath*:/**/popper.js/**/umd/popper.min.js",
                "classpath*:/**/bootstrap/**/bootstrap.min.js",
                "classpath*:/**/vue/**/vue.min.js",
                "classpath*:/**/bootstrap-vue/**/bootstrap-vue.min.js",
                "classpath*:/**/marked/**/marked.min.js",
                "classpath*:/**/vue-multiselect/**/vue-multiselect.min.js"
        )
    }
}

