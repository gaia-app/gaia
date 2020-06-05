package io.gaia_app.modules.bo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TerraformImageTest {

    @Test
    fun `image() should return the repository and tag`() {
        assertEquals("tatooine/mos-esley:sw-4", TerraformImage("tatooine/mos-esley", "sw-4").image())
    }

    @Test
    fun `default() should return the default image`() {
        assertEquals("hashicorp/terraform:latest", TerraformImage.defaultInstance().image())
    }

}
