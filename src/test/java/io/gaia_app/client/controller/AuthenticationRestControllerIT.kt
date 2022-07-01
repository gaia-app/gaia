package io.gaia_app.client.controller

import io.gaia_app.test.SharedMongoContainerTest
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationRestControllerIT: SharedMongoContainerTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeAll
    fun setUp() {
        mongo.emptyDatabase()
        mongo.runScript("00_organization.js")
        mongo.runScript("10_user.js")
    }

    @Test
    @WithMockUser("admin")
    fun `user() should return user`() {
        mockMvc.perform(get("/auth/user"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username", equalTo("admin")))
            .andExpect(jsonPath("$.admin", equalTo(true)))
            .andExpect(jsonPath("$.organization.id", equalTo("Ze Organization")))
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
