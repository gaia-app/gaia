package io.codeka.gaia.client.controller

import io.codeka.gaia.test.MongoContainer
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@DirtiesContext
@Testcontainers
@AutoConfigureMockMvc
class AuthenticationRestControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    companion object {
        @Container
        val mongoContainer = MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js")
    }

    @Test
    @WithMockUser("admin")
    fun `user() should return user`() {
        mockMvc.perform(get("/auth/user"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username", equalTo("admin")))
            .andExpect(jsonPath("$.admin", equalTo(true)))
            .andExpect(jsonPath("$.team.id", equalTo("Ze Team")))
    }

    @Test
    @WithMockUser("admin", roles = ["USER", "ADMIN"])
    fun `authorities() should return roles`() {
        mockMvc.perform(get("/auth/authorities"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(2)))
            .andExpect(jsonPath("$", equalTo(listOf("ROLE_ADMIN", "ROLE_USER"))))
    }

}
