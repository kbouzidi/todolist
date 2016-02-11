package com.reit.test.dao;

import com.reit.dao.ProjectDaoImpl;
import com.reit.model.Project;
import com.reit.test.utils.Constants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectDaoTest {

    Logger logger = LoggerFactory.getLogger(ProjectDaoTest.class);

    SessionFactory sessionFactoryMock;

    @Mock
    Session sessionMock;


    @Captor
    ArgumentCaptor<Project> captor;


    @Mock
    ProjectDaoImpl ProjectDaoMock;

    @Spy
    List<Project> projectList = new ArrayList<>();

    Project project;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(ProjectDaoMock.getCurrentSession()).thenReturn(sessionMock);
        project = Constants.getProjectSample(null);
        Mockito.spy(project);
    }


    @Test
    public void add() {
        Mockito.when(sessionMock.save(any())).thenReturn(project);
        ProjectDaoMock.add(Constants.getProjectSample(null)); // Project1
        verify(ProjectDaoMock, times(1)).add(captor.capture());
        Mockito.verifyNoMoreInteractions(ProjectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());

    }


    @Test
    public void update() {
        doNothing().when(sessionMock).update(any());
        ProjectDaoMock.update(Constants.getProjectSample(null));
        verify(ProjectDaoMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(ProjectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());
    }

    @Test
    public void delete() {
        doNothing().when(sessionMock).delete(any());
        ProjectDaoMock.delete(Constants.getProjectSample(null));
        verify(ProjectDaoMock, times(1)).delete(captor.capture());
        Mockito.verifyNoMoreInteractions(ProjectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());

    }


    @Test
    public void deleteAll() {
        doNothing().when(sessionMock).delete(any());
        Mockito.when(ProjectDaoMock.findAll()).thenReturn(projectList);
        ProjectDaoMock.deleteAll();
        verify(ProjectDaoMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(ProjectDaoMock);
    }

    @Test
    public void findById() {
        Mockito.when(sessionMock.get(Mockito.<Class>any(), Mockito.any())).thenReturn(project);
        Mockito.when(ProjectDaoMock.findById(Mockito.any())).thenCallRealMethod();
        Project result = (Project) ProjectDaoMock.findById(Constants.getProjectSample(null).getProjectId());
        assertEquals(result.getProjectName(), Constants.getProjectSample(null).getProjectName());

    }


    @Test
    public void findAll() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(sessionMock.createQuery(any())).thenReturn(q);
        Mockito.when(q.list()).thenReturn(projectList);
        Mockito.when(ProjectDaoMock.findAll()).thenCallRealMethod();
        List<Project> result = ProjectDaoMock.findAll();
        assertEquals(result.size(), projectList.size());

    }
}
