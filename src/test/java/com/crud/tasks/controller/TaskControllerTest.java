package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public  void shouldGetTasks() throws Exception{
        //given
        List<TaskDto> taskList =new ArrayList<>();
        taskList.add(new TaskDto(1L,"test task","test content"));

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(taskList);

        //when & then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$",hasSize(1) ));
    }

    @Test
    public  void shouldGetTask() throws Exception{
        //given
        Task task = new Task (1L,"test task","test content");
        TaskMapper mapper=new TaskMapper();
        TaskDto taskDto=mapper.mapToTaskDto(task);

        Optional<Task> opt = Optional.ofNullable(task);
        Long taskId =task.getId();
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.getTask(taskId)).thenReturn(opt);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //when & then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1) ));
    }

    @Test
    public void shouldDeleteTask()throws Exception{
        Task task = new Task (1L,"test task","test content");
        //Long taskId =task.getId();

        //when & then
        mockMvc.perform(delete("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test

    public void shouldUpdateTask()throws Exception{
        TaskDto taskDto = new TaskDto (1L,"test task","test content");
        TaskDto updatedTask=new TaskDto(1L,"test task","test updated task");

        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(updatedTask);

        Gson gson =new Gson();
        String jsonContent = gson.toJson(taskDto);

        //when & then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title",is("test task")))
                .andExpect(jsonPath("$.content",is("test updated task")));
    }

    @Test
    public  void shouldCreateTask() throws Exception {
        TaskDto taskDto=new TaskDto(1L,"test task","test content");
        TaskMapper mapper=new TaskMapper();
        Task task = mapper.mapToTask(taskDto);

        when(service.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);

        Gson gson =new Gson();
        String jsonContent = gson.toJson(task);

        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}