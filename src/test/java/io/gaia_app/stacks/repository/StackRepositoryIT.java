package io.gaia_app.stacks.repository;

import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.stacks.bo.StackState;
import io.gaia_app.organizations.Organization;
import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataMongoTest
class StackRepositoryIT extends SharedMongoContainerTest {

    @Autowired
    StackRepository stackRepository;

    @BeforeEach
    void setUp() {
        stackRepository.deleteAll();
    }

    @Test
    void findAllByState_shouldFindStacksByTheirState(){
        // given
        var organization = new Organization("userOrganization");

        var stackRunning1 = new Stack();
        stackRunning1.setId("1");
        stackRunning1.setState(StackState.RUNNING);
        stackRunning1.setOwnerOrganization(organization);

        var stackRunning2 = new Stack();
        stackRunning2.setId("2");
        stackRunning2.setState(StackState.RUNNING);
        stackRunning2.setOwnerOrganization(organization);

        var stackToUpdate1 = new Stack();
        stackToUpdate1.setId("3");
        stackToUpdate1.setState(StackState.TO_UPDATE);
        stackToUpdate1.setOwnerOrganization(organization);

        stackRepository.saveAll(List.of(stackRunning1, stackRunning2, stackToUpdate1));

        // when
        var result = stackRepository.countStacksByStateAndOwnerOrganization(StackState.RUNNING, organization);

        // then
        assertEquals(2, result);
    }

    @Test
    void stacks_shouldBeVisibleByTheirOwnerOrganization(){
        // given

        // sample organizations
        var organization1 = new Organization("organization1");
        var organization2 = new Organization("organization2");

        var stack1 = new Stack();
        stack1.setId("1");
        stack1.setOwnerOrganization(organization1);

        var stack2 = new Stack();
        stack2.setId("2");
        stack2.setOwnerOrganization(organization1);

        var stack3 = new Stack();
        stack3.setId("3");
        stack3.setOwnerOrganization(organization2);

        stackRepository.saveAll(List.of(stack1, stack2, stack3));

        // when
        var result = stackRepository.findByOwnerOrganization(organization1);

        // then
        assertThat(result).hasSize(2)
                .anyMatch(s -> "1".equals(s.getId()))
                .anyMatch(s -> "2".equals(s.getId()));
    }


}
