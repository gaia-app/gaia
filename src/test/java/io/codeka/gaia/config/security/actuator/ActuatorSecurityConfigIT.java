package io.codeka.gaia.config.security.actuator;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@Testcontainers
class ActuatorSecurityConfigIT {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static MongoContainer mongo = new MongoContainer();

    @Test
    void actuatorHealth_shouldNotNeedAuthentication() throws Exception {
        this.mockMvc.perform(get("/admin/health")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("UP")));
    }
}