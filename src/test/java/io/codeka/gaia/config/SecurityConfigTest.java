package io.codeka.gaia.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@SpringBootTest(classes = {EmptyController.class, SecurityConfig.class})
@AutoConfigureMockMvc
@TestPropertySource(properties = "gaia.admin-password=admin456")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminUserPassword_shouldBeConfigurable() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("admin456"))
                .andExpect(authenticated().withUsername("admin"));
    }

}

@Controller
class EmptyController{}