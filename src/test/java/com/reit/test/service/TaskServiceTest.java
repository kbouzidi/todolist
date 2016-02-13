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
package com.reit.test.service;

import com.reit.dao.TaskDaoImpl;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    TaskDaoImpl taskDaoMock;

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
        Mockito.when(taskDaoMock.add(any(), any(), any())).thenReturn(task);
        Mockito.when(taskServiceMock.getTaskDao()).thenReturn(taskDaoMock);
        Mockito.when(taskServiceMock.add(any(), any(), any())).thenCallRealMethod();
        Task result = (Task) taskServiceMock.add(Constants.getTaskSample(), Constants.getProjectSample(null), Constants.getUserSample(null));
        assertEquals(result.getTaskName(), Constants.getTaskSample().getTask().getTaskName());

    }

    @Test
    public void update() {
        doNothing().when(taskDaoMock).update(any());
        taskServiceMock.update(Constants.getTaskSample());
        verify(taskServiceMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(taskDaoMock);
        assertEquals(captor.getValue().getTask().getDescription(), Constants.getTaskSample().getTask().getDescription());
    }

    @Test
    public void delete() {
        doNothing().when(taskDaoMock).delete(any());
        taskServiceMock.delete(Constants.getTaskSample().getTaskId());
        verify(taskServiceMock, times(1)).delete(captorLong.capture());
        Mockito.verifyNoMoreInteractions(taskServiceMock);

    }

    @Test
    public void deleteAll() {
        doNothing().when(taskDaoMock).deleteAll();
        Mockito.when(taskDaoMock.findAll()).thenReturn(taskList);
        taskServiceMock.deleteAll();
        verify(taskServiceMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(taskServiceMock);
    }

    @Test
    public void findById() {
        Mockito.when(taskDaoMock.findById(any())).thenReturn(task);
        Mockito.when(taskServiceMock.getTaskDao()).thenReturn(taskDaoMock);
        Mockito.when(taskServiceMock.findById(any())).thenCallRealMethod();
        Task result = (Task) taskServiceMock.findById(Constants.getTaskSample().getTaskId());
        assertEquals(result.getTaskName(), Constants.getTaskSample().getTask().getTaskName());

    }

    @Test
    public void findAll() {
        Mockito.when(taskDaoMock.findAll()).thenReturn(taskList);
        Mockito.when(taskServiceMock.getTaskDao()).thenReturn(taskDaoMock);
        Mockito.when(taskServiceMock.findAll()).thenCallRealMethod();
        List<Task> result = taskDaoMock.findAll();
        assertEquals(result.size(), taskList.size());

    }

}
