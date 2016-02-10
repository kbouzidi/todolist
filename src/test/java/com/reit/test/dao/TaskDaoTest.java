/*
 * The MIT License
 *
 * Copyright 2016 kbouzidi.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
        Task result = (Task) taskDaoMock.findById(Constants.getTaskSample().getTaskId());
        assertEquals(result.getTaskName(), Constants.getTaskSample().getTask().getTaskName());

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
