package io.codeka.gaia.controller;

import io.codeka.gaia.bo.StackState;
import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    @Mock
    private TerraformModuleRepository terraformModuleRepository;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private Model model;

    @Test
    void index_shouldShowModuleCount(){
        // given
        when(terraformModuleRepository.count()).thenReturn(12L);

        // when
        var result = indexController.index(model);

        // then
        assertEquals("index", result);
        verify(terraformModuleRepository).count();
        verify(model).addAttribute("moduleCount", 12L);
    }

    @Test
    void index_shouldShowStacksCount(){
        // given
        doReturn(1).when(stackRepository).countStacksByState(StackState.RUNNING);
        doReturn(2).when(stackRepository).countStacksByState(StackState.TO_UPDATE);

        // when
        var result = indexController.index(model);

        // then
        assertEquals("index", result);
        verify(stackRepository).countStacksByState(StackState.RUNNING);
        verify(stackRepository).countStacksByState(StackState.TO_UPDATE);
        verify(model).addAttribute("runningStackCount", 1);
        verify(model).addAttribute("toUpdateStackCount", 2);
    }

}