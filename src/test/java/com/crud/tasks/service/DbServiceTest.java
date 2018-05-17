package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void testGetAllTasks(){
        //given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "task", "test_task") );

        when(repository.findAll()).thenReturn(tasks);

        //when
        List <Task> resultList=dbService.getAllTasks();

        //then
        assertNotNull(resultList);
        assertEquals(1,resultList.size());
        assertEquals(new Long (1L),resultList.get(0).getId() );
        assertEquals("task",resultList.get(0).getTitle() );
        assertEquals("test_task", resultList.get(0).getContent());
    }

    @Test
    public void testGetAllTasksWithEmptyList() {
        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //when
        List <Task> resultList=dbService.getAllTasks();

        //then
        assertEquals(0, resultList.size());
    }

    @Test

    public  void testGetTask(){
        Task task = new Task(1L, "task", "test_task");
        Optional<Task> opt = Optional.ofNullable(task);
        Long id=task.getId();

        when(repository.findById(id)).thenReturn(opt);

        //when
        Optional<Task>result=dbService.getTask(id);

        //then
        assertTrue(result.isPresent());

    }

    @Test

    public  void testGetTaskEmpty(){
        Optional<Task> opt = Optional.empty();
        Long id=1L;

        when(repository.findById(id)).thenReturn(opt);

        //when
        Optional<Task>result=dbService.getTask(id);

        //then
        assertFalse(result.isPresent());

    }

    @Test
    public  void testSaveTask(){
        //given
        Task task = new Task(new Long (1L), "task", "test_task");
        when(repository.save(task)).thenReturn(task);

         //when
        Task savedTask=dbService.saveTask(task);

        //then
        assertEquals(new Long (1L),savedTask.getId() );
        assertEquals("task",savedTask.getTitle() );
        assertEquals("test_task", savedTask.getContent());
    }
}
