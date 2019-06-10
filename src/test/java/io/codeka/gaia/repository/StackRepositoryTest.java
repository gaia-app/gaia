package io.codeka.gaia.repository;

import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.bo.StackState;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataMongoTest
@Testcontainers
@DirtiesContext
public class StackRepositoryTest {

    @Autowired
    StackRepository stackRepository;

    @Container
    private static MongoContainer mongo = new MongoContainer();

    @Test
    void findAllByState_shouldFindStacksByTheirState(){
        // given
        stackRepository.deleteAll();

        var stackRunning1 = new Stack();
        stackRunning1.setId("1");
        stackRunning1.setState(StackState.RUNNING);

        var stackRunning2 = new Stack();
        stackRunning2.setId("2");
        stackRunning2.setState(StackState.RUNNING);

        var stackToUpdate1 = new Stack();
        stackToUpdate1.setId("3");
        stackToUpdate1.setState(StackState.TO_UPDATE);

        stackRepository.saveAll(List.of(stackRunning1, stackRunning2, stackToUpdate1));

        // when
        var result = stackRepository.countStacksByState(StackState.RUNNING);

        // then
        assertEquals(2, result);
    }


}