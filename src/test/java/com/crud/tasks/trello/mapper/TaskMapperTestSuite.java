package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskMapperTestSuite {
    TaskMapper taskMapper=new TaskMapper();

    @Test
    public void  testMapToTask(){
        //Given
        TaskDto taskDto=new TaskDto( 1L, "taskDto", "testing taskDto");

        //when
        Task mappedTask=taskMapper.mapToTask(taskDto);

        //then
        assertEquals("taskDto",mappedTask.getTitle() );
        assertEquals("testing taskDto",mappedTask.getContent() );

    }

    @Test
    public  void testMapToTaskDto(){
        //given
        Task task =new Task(1L,"task" ,"testing task");

        //when
        TaskDto mappedTask=taskMapper.mapToTaskDto(task);

        //then
        assertEquals(new Long(1L), mappedTask.getId());
        assertEquals("task",mappedTask.getTitle());
        assertEquals("testing task", mappedTask.getContent());
    }

    @Test
    public void testMapToTaskDtoList(){
        //given
        List<Task> taskList= new ArrayList<>();
        taskList.add(new Task(1L,"task list", "task list content"));
        //when
        List<TaskDto>mappedList=taskMapper.mapToTaskDtoList(taskList);

        //then
        assertNotNull(mappedList);
        assertEquals(1, mappedList.size());
        mappedList.forEach(taskDto-> {
            assertEquals(new Long(1L), taskDto.getId());
            assertEquals("task list", taskDto.getTitle());
            assertEquals("task list content", taskDto.getContent());
        });
    }
}
