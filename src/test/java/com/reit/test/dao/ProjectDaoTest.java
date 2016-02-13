package com.reit.test.dao;

import com.reit.dao.ProjectDaoImpl;
import com.reit.model.Project;
import com.reit.test.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
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
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
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
    ProjectDaoImpl projectDaoMock;

    @Spy
    List<Project> projectList = new ArrayList<>();

    Project project;

    Long projectId = new Long("1");

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
        projectDaoMock.add(Constants.getProjectSample(null)); // Project1
        verify(projectDaoMock, times(1)).add(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());

    }


    @Test
    public void update() {
        doNothing().when(sessionMock).update(any());
        projectDaoMock.update(Constants.getProjectSample(null));
        verify(projectDaoMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());
    }

    @Test
    public void delete() {
        doNothing().when(sessionMock).delete(any());
        projectDaoMock.delete(Constants.getProjectSample(null));
        verify(projectDaoMock, times(1)).delete(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());

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


    @Test
    public void findByProjectName() {
        Criteria criteria = Mockito.mock(Criteria.class);
        Mockito.when(sessionMock.createCriteria(Mockito.<Class>any())).thenReturn(criteria);
        Mockito.when(criteria.add(any())).thenReturn(criteria);
        Mockito.when(criteria.uniqueResult()).thenReturn(project);
        Mockito.when(projectDaoMock.findByProjectName(Mockito.any())).thenCallRealMethod();
        Project result = (Project) projectDaoMock.findByProjectName(anyString());
        assertEquals(result.getProjectName(), Constants.getProjectSample(null).getProjectName());

    }

    @Test
    public void unlinkProjectsToUser() {
        doNothing().when(projectDaoMock).unlinkProject(anyList());
        doNothing().when(sessionMock).update(any());
        projectDaoMock.update(Constants.getProjectSample(null));
        verify(projectDaoMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(projectDaoMock);
        assertEquals(captor.getValue().getProjectDescription(), Constants.getProjectSample(null).getProjectDescription());

    }
}
