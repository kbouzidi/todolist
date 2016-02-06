package com.reit.dao;


import java.util.ArrayList;
import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.InjectMocks;

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


import com.reit.model.Todo;
import com.reit.service.TodoService;


@RunWith(MockitoJUnitRunner.class)
public class TodoDaoTest {
    Logger logger = LoggerFactory.getLogger(TodoDaoTest.class);

    SessionFactory sessionFactoryMock;

    @Mock
    Session sessionMock;


    @Captor
    ArgumentCaptor<Todo> captor;


    @Mock
    TodoDao todoDaoMock;

    @InjectMocks
    TodoService todoService;

    @Spy
    List<Todo> todoList = new ArrayList<>();

    Todo todo;

    @Before
    public void setUp() {
        todo = new Todo(Long.decode("1"), user, desc, state);
        MockitoAnnotations.initMocks(this);
        todoList = getTodoList();
        sessionMock = Mockito.mock(Session.class);
        sessionFactoryMock = Mockito.mock(SessionFactory.class);
        Mockito.when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        Mockito.when(todoDaoMock.getCurrentSession()).thenReturn(sessionMock);
    }


    @Test
    public void add() {
        Mockito.when(sessionMock.save(any())).thenReturn(todo);
        todoDaoMock.add(todoList.get(0));
        verify(todoDaoMock, times(1)).add(captor.capture());
        verifyNoMoreInteractions(todoDaoMock);
        assertEquals(captor.getValue().getAuthor(), todoList.get(0).getAuthor());

    }


    @Test
    public void update() {
        doNothing().when(sessionMock).update(any());
        todoDaoMock.update(todoList.get(0));
        verify(todoDaoMock, times(1)).update(captor.capture());
        verifyNoMoreInteractions(todoDaoMock);
        assertEquals(captor.getValue().getAuthor(), todoList.get(0).getAuthor());
    }

    @Test
    public void delete() {
        doNothing().when(sessionMock).delete(any());
        todoDaoMock.delete(todoList.get(0));
        verify(todoDaoMock, times(1)).delete(captor.capture());
        verifyNoMoreInteractions(todoDaoMock);
        assertEquals(captor.getValue().getAuthor(), todoList.get(0).getAuthor());

    }


    @Test
    public void deleteAll() {
        doNothing().when(sessionMock).delete(any());
        Mockito.when(todoDaoMock.findAll()).thenReturn(todoList);
        todoDaoMock.deleteAll();
        verify(todoDaoMock, times(1)).deleteAll();
        verifyNoMoreInteractions(todoDaoMock);
    }

    @Test
    public void findById() {
        Mockito.when(sessionMock.get(Mockito.<Class>any(), Mockito.any())).thenReturn(todo);
        Mockito.when(todoDaoMock.findById(Mockito.any())).thenCallRealMethod();
        Todo result = (Todo) todoDaoMock.findById(todoList.get(0).getId());
        assertEquals(result.getAuthor(), todoList.get(0).getAuthor());

    }


    @Test
    public void findAll() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(sessionMock.createQuery(any())).thenReturn(q);
        Mockito.when(q.list()).thenReturn(todoList);
        Mockito.when(todoDaoMock.findAll()).thenCallRealMethod();
        List<Todo> result = todoDaoMock.findAll();
        assertEquals(result.size(), todoList.size());

    }

    String user = "User";
    String desc = "This is a Task";
    String state = EStates.STARTED.getValue();

    public List<Todo> getTodoList() {
        Todo t1 = new Todo(Long.decode("1"), user, desc, state);
        Todo t2 = new Todo(Long.decode("2"), user + "2", desc + "2", state);
        todoList.add(t1);
        todoList.add(t2);
        return todoList;
    }
}
