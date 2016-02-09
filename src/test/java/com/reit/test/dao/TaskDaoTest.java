package com.reit.test.dao;


import java.util.ArrayList;
import java.util.List;

import com.reit.dao.TaskDaoImpl;
import com.reit.model.Task;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Matchers.any;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.mockito.runners.MockitoJUnitRunner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;


import com.reit.test.utils.*;


@RunWith(MockitoJUnitRunner.class)
public class TaskDaoTest {
    Logger logger = LoggerFactory.getLogger(TaskDaoTest.class);

    SessionFactory sessionFactoryMock;

    @Mock
    Session sessionMock;


    @Captor
    ArgumentCaptor<Task> captor;


    @Mock
    TaskDaoImpl taskDaoMock;

    @Spy
    List<Task> taskList = new ArrayList<>();

    Task task;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(taskDaoMock.getCurrentSession()).thenReturn(sessionMock);
        task = Constants.getTaskSample();
        Mockito.spy(task);
    }


    @Test
    public void add() {
        Mockito.when(sessionMock.save(any())).thenReturn(task);
        taskDaoMock.add(Constants.getTaskSample()); // task1
        verify(taskDaoMock, times(1)).add(captor.capture());
        Mockito.verifyNoMoreInteractions(taskDaoMock);
        assertEquals(captor.getValue().getTask().getDescription(), Constants.getTaskSample().getTask().getDescription());

    }


    @Test
    public void update() {
        doNothing().when(sessionMock).update(any());
        taskDaoMock.update(Constants.getTaskSample());
        verify(taskDaoMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(taskDaoMock);
        assertEquals(captor.getValue().getTask().getDescription(), Constants.getTaskSample().getTask().getDescription());
    }

    @Test
    public void delete() {
        doNothing().when(sessionMock).delete(any());
        taskDaoMock.delete(Constants.getTaskSample());
        verify(taskDaoMock, times(1)).delete(captor.capture());
        Mockito.verifyNoMoreInteractions(taskDaoMock);
        assertEquals(captor.getValue().getTask().getDescription(), Constants.getTaskSample().getTask().getDescription());

    }


    @Test
    public void deleteAll() {
        doNothing().when(sessionMock).delete(any());
        Mockito.when(taskDaoMock.findAll()).thenReturn(taskList);
        taskDaoMock.deleteAll();
        verify(taskDaoMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(taskDaoMock);
    }

    @Test
    public void findById() {
        Mockito.when(sessionMock.get(Mockito.<Class>any(), Mockito.any())).thenReturn(task);
        Mockito.when(taskDaoMock.findById(Mockito.any())).thenCallRealMethod();
        Task result = (Task) taskDaoMock.findById(Constants.getTaskSample().getId());
      //  assertEquals(result.getUsers().getUserName(), Constants.getTaskSample().getTask().getUsers().getUserName());

    }


    @Test
    public void findAll() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(sessionMock.createQuery(any())).thenReturn(q);
        Mockito.when(q.list()).thenReturn(taskList);
        Mockito.when(taskDaoMock.findAll()).thenCallRealMethod();
        List<Task> result = taskDaoMock.findAll();
        assertEquals(result.size(), taskList.size());

    }

}
