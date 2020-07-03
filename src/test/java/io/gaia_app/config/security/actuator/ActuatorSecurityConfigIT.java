package io.gaia_app.config.security.actuator;

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorSecurityConfigIT extends SharedMongoContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void actuatorHealth_shouldNotNeedAuthentication() throws Exception {
        this.mockMvc.perform(get("/admin/health")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("UP")));
    }
}
