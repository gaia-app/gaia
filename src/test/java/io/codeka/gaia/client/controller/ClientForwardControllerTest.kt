package io.codeka.gaia.client.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class ClientForwardControllerTest {

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(ClientForwardController(), TestController())
            .build()
    }

    @Test
    fun `clientForwardController should forward to home page for unknown mapping`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/path/to/go"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.forwardedUrl("/"))
    }

    @Test
    fun `clientForwardController should ignore backend`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
            .andExpect(MockMvcResultMatchers.content().string("test"))
    }

    @RestController
    class TestController {

        @RequestMapping("/api/test")
        fun test(): String = "test"

    }

}
