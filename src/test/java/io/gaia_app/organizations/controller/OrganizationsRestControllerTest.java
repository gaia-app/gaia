package io.gaia_app.organizations.controller;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.repository.OrganizationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationsRestControllerTest {

    @Mock
    private OrganizationsRepository organizationsRepository;

    @InjectMocks
    private OrganizationsRestController organizationsRestController;

    @Test
    void organizations_shouldReturnAllOrganizations() {
        // given
        var a = new Organization("A");
        when(organizationsRepository.findAll()).thenReturn(List.of(a));

        // when
        var organizations = organizationsRestController.organizations();

        // then
        assertThat(organizations).contains(a);
        verify(organizationsRepository).findAll();
    }
}
