package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;
    private TaskNotFoundException taskNotFoundException;

    @Test
    void shouldReturnEmptyList() {
        //Given
        List<Task> tasks = List.of();
        when(taskRepository.findAll()).thenReturn(tasks);
        //When
        List<Task> result = dbService.getAllTasks();
        //Then
        assertThat(result.size()==0);
        assertThat(result).isNotNull();

    }

    @Test
    void getTaskMethodShouldThrowException() {
        //Given
        Task task = null;
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        //When
        assertThatExceptionOfType(TaskNotFoundException.class).isThrownBy(() -> dbService.getTask(1L));
    }

    @Test
    void deleteTaskMethodShouldThrowException() {
        //Given
        Task task = null;
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        //When
        assertThatExceptionOfType(TaskNotFoundException.class).isThrownBy(() -> dbService.deleteTask(1L));
    }
}
