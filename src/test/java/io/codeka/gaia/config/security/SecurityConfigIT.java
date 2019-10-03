package io.codeka.gaia.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@SpringBootTest(classes = SecurityConfig.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "gaia.admin-password=admin456")
class SecurityConfigIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SuccessHandler successHandler;

    @Test
    void adminUserPassword_shouldBeConfigurable() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("admin456"))
                .andExpect(authenticated().withUsername("admin"));

        verify(successHandler).onAuthenticationSuccess(any(), any(), any());
    }
}