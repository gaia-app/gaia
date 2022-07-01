package io.gaia_app.dashboard.controller

import io.gaia_app.modules.repository.TerraformModuleRepository
import io.gaia_app.stacks.bo.StackState
import io.gaia_app.stacks.repository.StackRepository
import io.gaia_app.organizations.Organization
import io.gaia_app.organizations.User
import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DashboardRestControllerTest {

    @Mock
    lateinit var moduleRepository: TerraformModuleRepository

    @Mock
    lateinit var stackRepository: StackRepository

    @InjectMocks
    lateinit var controller: DashboardRestController

    @Nested
    inner class WhenAdminUserTest {

        private lateinit var user: User

        @BeforeEach
        internal fun setUp() {
            user = User("admin", null)
            user.isAdmin = true
        }

        @Test
        fun `summary() should return modules count`() {
            // when
            whenever(moduleRepository.count()).thenReturn(1L)
            val result = controller.summary(user, null)

            // then
            verify(moduleRepository, times(1)).count()
            verifyNoMoreInteractions(moduleRepository)
            assertThat(result)
                .isNotNull
                .containsEntry("modulesCount", 1L)
        }

        @Test
        fun `summary() should return stacks count`() {
            // when
            whenever(stackRepository.countStacksByState(StackState.RUNNING)).thenReturn(2)
            whenever(stackRepository.countStacksByState(StackState.TO_UPDATE)).thenReturn(3)
            val result = controller.summary(user, null)

            // then
            verify(stackRepository, times(1)).countStacksByState(StackState.RUNNING)
            verify(stackRepository, times(1)).countStacksByState(StackState.TO_UPDATE)
            verifyNoMoreInteractions(stackRepository)
            assertThat(result)
                .isNotNull
                .containsEntry("runningStacksCount", 2L)
                .containsEntry("toUpdateStacksCount", 3L)
        }

    }

    @Nested
    inner class WhenUserWithOrganizationTest {

        private val organization = Organization("userOrganization")
        private val user = User("user", organization)

        @Test
        fun `summary() should return modules count`() {
            // when
            whenever(moduleRepository.countByAuthorizedOrganizationsContainingOrModuleMetadataCreatedBy(organization, user)).thenReturn(4)
            val result = controller.summary(user, organization)

            // then
            verify(moduleRepository, times(1)).countByAuthorizedOrganizationsContainingOrModuleMetadataCreatedBy(organization, user)
            verifyNoMoreInteractions(moduleRepository)
            assertThat(result)
                .isNotNull
                .containsEntry("modulesCount", 4L)
        }

        @Test
        fun `summary() should return stacks count`() {
            // when
            whenever(stackRepository.countStacksByStateAndOwnerOrganization(StackState.RUNNING, organization)).thenReturn(5)
            whenever(stackRepository.countStacksByStateAndOwnerOrganization(StackState.TO_UPDATE, organization)).thenReturn(6)
            val result = controller.summary(user, organization)

            // then
            verify(stackRepository, times(1)).countStacksByStateAndOwnerOrganization(StackState.RUNNING, organization)
            verify(stackRepository, times(1)).countStacksByStateAndOwnerOrganization(StackState.TO_UPDATE, organization)
            verifyNoMoreInteractions(stackRepository)
            assertThat(result)
                .isNotNull
                .containsEntry("runningStacksCount", 5L)
                .containsEntry("toUpdateStacksCount", 6L)
        }

    }

    @Nested
    inner class WhenUserWithoutOrganizationTest {

        private val user = User("user", null)

        @Test
        fun `summary() should return modules count`() {
            // when
            whenever(moduleRepository.countByModuleMetadataCreatedBy(user)).thenReturn(7)
            val result = controller.summary(user, null)

            // then
            verify(moduleRepository, times(1)).countByModuleMetadataCreatedBy(user)
            verifyNoMoreInteractions(moduleRepository)
            assertThat(result)
                .isNotNull
                .containsEntry("modulesCount", 7L)
        }

        @Test
        fun `summary() should return stacks count`() {
            // when
            whenever(stackRepository.countStacksByStateAndCreatedBy(StackState.RUNNING, user)).thenReturn(8)
            whenever(stackRepository.countStacksByStateAndCreatedBy(StackState.TO_UPDATE, user)).thenReturn(9)
            val result = controller.summary(user, null)

            // then
            verify(stackRepository, times(1)).countStacksByStateAndCreatedBy(StackState.RUNNING, user)
            verify(stackRepository, times(1)).countStacksByStateAndCreatedBy(StackState.TO_UPDATE, user)
            verifyNoMoreInteractions(stackRepository)
            assertThat(result)
                .isNotNull
                .containsEntry("runningStacksCount", 8L)
                .containsEntry("toUpdateStacksCount", 9L)
        }

    }

}
