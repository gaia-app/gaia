package io.codeka.gaia.stacks.repository;

import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.bo.StackState;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataMongoTest
@Testcontainers
@DirtiesContext
class StackRepositoryIT {

    @Autowired
    StackRepository stackRepository;

    @Container
    private static MongoContainer mongo = new MongoContainer();

    @BeforeEach
    void setUp() {
        stackRepository.deleteAll();
    }

    @Test
    void findAllByState_shouldFindStacksByTheirState(){
        // given
        var team = new Team("userTeam");

        var stackRunning1 = new Stack();
        stackRunning1.setId("1");
        stackRunning1.setState(StackState.RUNNING);
        stackRunning1.setOwnerTeam(team);

        var stackRunning2 = new Stack();
        stackRunning2.setId("2");
        stackRunning2.setState(StackState.RUNNING);
        stackRunning2.setOwnerTeam(team);

        var stackToUpdate1 = new Stack();
        stackToUpdate1.setId("3");
        stackToUpdate1.setState(StackState.TO_UPDATE);
        stackToUpdate1.setOwnerTeam(team);

        stackRepository.saveAll(List.of(stackRunning1, stackRunning2, stackToUpdate1));

        // when
        var result = stackRepository.countStacksByStateAndOwnerTeam(StackState.RUNNING, team);

        // then
        assertEquals(2, result);
    }

    @Test
    void stacks_shouldBeVisibleByTheirOwnerTeam(){
        // given

        // sample teams
        var team1 = new Team("team1");
        var team2 = new Team("team2");

        var stack1 = new Stack();
        stack1.setId("1");
        stack1.setOwnerTeam(team1);

        var stack2 = new Stack();
        stack2.setId("2");
        stack2.setOwnerTeam(team1);

        var stack3 = new Stack();
        stack3.setId("3");
        stack3.setOwnerTeam(team2);

        stackRepository.saveAll(List.of(stack1, stack2, stack3));

        // when
        var result = stackRepository.findByOwnerTeam(team1);

        // then
        assertThat(result).hasSize(2)
                .anyMatch(s -> "1".equals(s.getId()))
                .anyMatch(s -> "2".equals(s.getId()));
    }


}