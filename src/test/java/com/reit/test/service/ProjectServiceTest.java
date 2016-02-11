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

import com.reit.dao.ProjectDaoImpl;
import com.reit.model.Project;
import com.reit.test.dao.ProjectDaoTest;
import com.reit.test.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kbouzidi
 */
public class ProjectServiceTest {
    Logger logger = LoggerFactory.getLogger(ProjectDaoTest.class);

    SessionFactory sessionFactoryMock;

    @Mock
    Session sessionMock;

    @Captor
    ArgumentCaptor<Project> captor;

    @Mock
    ProjectDaoImpl projectDaoMock;

    @Spy
    List<Project> projectList = new ArrayList<>();

    Project project;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(projectDaoMock.getCurrentSession()).thenReturn(sessionMock);
        project = Constants.getProjectSample(null);
        Mockito.spy(project);
    }

    @Test
    public void add() {
        Mockito.when(sessionMock.save(any())).thenReturn(project);
        projectDaoMock.add(Constants.getProjectSample(null)); // task1
        verify(projectDaoMock, times(1)).add(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectName(), Constants.getProjectSample(null).getProjectName());

    }

    @Test
    public void update() {
        doNothing().when(sessionMock).update(any());
        projectDaoMock.update(Constants.getProjectSample(null));
        verify(projectDaoMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectName(), Constants.getProjectSample(null).getProjectName());
    }

    @Test
    public void delete() {
        doNothing().when(sessionMock).delete(any());
        projectDaoMock.delete(Constants.getProjectSample(null));
        verify(projectDaoMock, times(1)).delete(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectName(), Constants.getProjectSample(null).getProjectName());

    }

    @Test
    public void deleteAll() {
        doNothing().when(sessionMock).delete(any());
        Mockito.when(projectDaoMock.findAll()).thenReturn(projectList);
        projectDaoMock.deleteAll();
        verify(projectDaoMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(projectDaoMock);
    }

    @Test
    public void findById() {
        Mockito.when(sessionMock.get(Mockito.<Class>any(), Mockito.any())).thenReturn(project);
        Mockito.when(projectDaoMock.findById(Mockito.any())).thenCallRealMethod();
        Project result = (Project) projectDaoMock.findById(Constants.getProjectSample(null).getProjectId());
        assertEquals(result.getProjectName(), Constants.getProjectSample(null).getProjectName());

    }

    @Test
    public void findAll() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(sessionMock.createQuery(any())).thenReturn(q);
        Mockito.when(q.list()).thenReturn(projectList);
        Mockito.when(projectDaoMock.findAll()).thenCallRealMethod();
        List<Project> result = projectDaoMock.findAll();
        assertEquals(result.size(), projectList.size());

    }
}
