package io.codeka.gaia.dashboard.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.rnorth.visibleassertions.VisibleAssertions.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import java.util.*

@SpringBootTest(classes = [UIExtensionsConfig::class])
class UiExtensionsScannerIT(private val applicationContext: ApplicationContext) {

    @Test
    fun `scan should find all existing extensions`() {
        val scanner = UiExtensionsScanner(applicationContext)

        val locations = arrayOf(
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
                "classpath*:/**/vue-multiselect/**/vue-multiselect.min.js",
                "classpath*:/**/momentjs/**/moment.min.js"
        )

        val uiExtension = scanner.scan(*locations)

        assertThat(uiExtension.size).isEqualTo(locations.size)
    }

    @Test
    fun `scan should throw an exception when an extension cannot be found`() {
        val scanner = UiExtensionsScanner(applicationContext)

        val locations = arrayOf("classpath*:/**/bootstrap/**/bootstrap.min.BOUM")

        assertThrows("Array is empty", NoSuchElementException::class.java) { scanner.scan(*locations) }
    }
}