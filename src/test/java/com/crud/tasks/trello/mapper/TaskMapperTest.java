package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void mapToTaskTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals("Test task", task.getTitle());
        assertEquals(1L, task.getId());
    }

    @Test
    void mapToTaskDtoTest() {
        //Given
        Task task = new Task(1L, "Test task", "Test content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals("Test task", taskDto.getTitle());
        assertEquals(1L, taskDto.getId());
    }

    @Test
    void shouldReturnEmptyList() {
        //Given
        List<Task> taskList = List.of();
        //When
        List<TaskDto> resultList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(resultList).isNotNull();
    }
}
