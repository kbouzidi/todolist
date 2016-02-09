package com.reit.test.service;

import com.reit.dao.TaskDao;
import com.reit.model.Task;
import com.reit.service.TaskService;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import com.reit.test.utils.*;

import static org.mockito.Mockito.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    TaskDao todoDaoMock;

    @Spy
    List<Task> taskList = new ArrayList<>();

    @Mock
    TaskService taskServiceMock;

    @Captor
    ArgumentCaptor<Task> captor;

    @Captor
    ArgumentCaptor<Long> captorLong;

    Task task;

    @Mock
    Session sessionMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        task = Constants.getTaskSample();
        Mockito.spy(task);
    }

    @Test
    public void add() {
        doNothing().when(todoDaoMock).add(any());
        taskServiceMock.add(Constants.getTaskSample());
        verify(taskServiceMock, times(1)).add(captor.capture());
        assertEquals(captor.getValue().getTask().getDescription(), Constants.getTaskSample().getTask().getDescription());
    }

    @Test
    public void update() {
        doNothing().when(todoDaoMock).update(any());
        taskServiceMock.update(Constants.getTaskSample());
        verify(taskServiceMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(todoDaoMock);
        assertEquals(captor.getValue().getTask().getDescription(), Constants.getTaskSample().getTask().getDescription());
    }

    @Test
    public void delete() {
        doNothing().when(todoDaoMock).delete(any());
        taskServiceMock.delete(Constants.getTaskSample().getId());
        verify(taskServiceMock, times(1)).delete(captorLong.capture());
        Mockito.verifyNoMoreInteractions(taskServiceMock);

    }

    @Test
    public void deleteAll() {
        doNothing().when(todoDaoMock).deleteAll();
        Mockito.when(todoDaoMock.findAll()).thenReturn(taskList);
        taskServiceMock.deleteAll();
        verify(taskServiceMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(taskServiceMock);
    }

    @Test
    public void findById() {
        Mockito.when(todoDaoMock.findById(any())).thenReturn(task);
        Mockito.when(taskServiceMock.getTaskDao()).thenReturn(todoDaoMock);
        Mockito.when(taskServiceMock.findById(Mockito.any())).thenCallRealMethod();
        Task result = (Task) taskServiceMock.findById(Constants.getTaskSample().getId());
        assertEquals(result.getName(),  Constants.getTaskSample().getTask().getName());

    }

    @Test
    public void findAll() {
        Mockito.when(todoDaoMock.findAll()).thenReturn(taskList);
        Mockito.when(taskServiceMock.getTaskDao()).thenReturn(todoDaoMock);
        Mockito.when(taskServiceMock.findAll()).thenCallRealMethod();
        List<Task> result = todoDaoMock.findAll();
        assertEquals(result.size(), taskList.size());

    }

}
