package io.gaia_app.modules.controller;

import io.gaia_app.test.SharedMongoContainerTest
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.client.MockRestServiceServer.bindTo
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.client.RestTemplate

@SpringBootTest
@AutoConfigureWebClient
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DockerRegistryRestControllerIT: SharedMongoContainerTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var restTemplate: RestTemplate

    @BeforeAll
    internal fun setUp() {
        mongo.emptyDatabase()
        mongo.runScript("10_user.js")
    }

    @Test
    fun `resource repositories should return the repositories matching the query param name`() {
        // given
        val server = bindTo(restTemplate).build()
        val urlToCall = "https://registry.hub.docker.com/v2/search/repositories?query=terraform&page=1&page_size=10"
        server.expect(requestTo(urlToCall)).andRespond(withSuccess(
                ClassPathResource("/rest/docker-hub/terraform-repositories.json"), MediaType.APPLICATION_JSON))

        // when
        mockMvc.perform(get("/api/docker/repositories")
                .queryParam("name", "terraform")
                .with(user("Mary J")))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(3)))
                .andExpect(jsonPath("$[0]name", equalTo("hashicorp/terraform")))
                .andExpect(jsonPath("$[0]description", equalTo("official one")))
                .andExpect(jsonPath("$[1]name", equalTo("rogue/terraform")))
                .andExpect(jsonPath("$[1]description", equalTo("rebels one")))
                .andExpect(jsonPath("$[2]name", equalTo("empire/terraform")))
                .andExpect(jsonPath("$[2]description", equalTo("empire one")))

        // then
        server.verify()
    }

    @Test
    fun `resource tags should return the tags for the repository`() {
        // given
        val server = bindTo(restTemplate).build()
        val urlToCall = "https://registry.hub.docker.com/v2/repositories/hashicorp/terraform/tags?name=latest&page=1&page_size=10"
        server.expect(requestTo(urlToCall)).andRespond(withSuccess(
                ClassPathResource("/rest/docker-hub/terraform-tags.json"), MediaType.APPLICATION_JSON))

        // when
        mockMvc.perform(get("/api/docker/repositories/hashicorp/terraform/tags?name=latest")
                .with(user("Mary J")))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(3)))
                .andExpect(jsonPath("$[0]name", equalTo("latest")))
                .andExpect(jsonPath("$[1]name", equalTo("light")))
                .andExpect(jsonPath("$[2]name", equalTo("full")))

        // then
        server.verify()
    }

    @Test
    fun `resource tags should return the tags for the repository when default owner`() {
        // given
        val server = bindTo(restTemplate).build()
        val urlToCall = "https://registry.hub.docker.com/v2/repositories/library/terraform/tags?name=unknown&page=1&page_size=10"
        server.expect(requestTo(urlToCall)).andRespond(withSuccess())

        // when
        mockMvc.perform(get("/api/docker/repositories/terraform/tags?name=unknown")
                .with(user("Mary J")))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", empty<Any>()))

        // then
        server.verify()
    }

}
