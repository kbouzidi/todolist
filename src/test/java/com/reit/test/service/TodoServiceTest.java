package com.reit.test.service;


import com.reit.dao.TodoDao;
import com.reit.model.Todo;
import com.reit.service.TodoService;
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
public class TodoServiceTest {

    @Mock
    TodoDao todoDaoMock;

    @Spy
    List<Todo> todoList = new ArrayList<>();

    @Mock
    TodoService todoServiceMock;

    @Captor
    ArgumentCaptor<Todo> captor;

    @Captor
    ArgumentCaptor<Long> captorLong;

    Todo todo;

    @Mock
    Session sessionMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        todoList = Constants.getTodoList(todoList);
        todo = new Todo(Long.decode("1"), Constants.user, Constants.desc, Constants.state);
    }

    @Test
    public void add() {
        doNothing().when(todoDaoMock).add(any());
        todoServiceMock.add(todoList.get(0));
        verify(todoServiceMock, times(1)).add(captor.capture());
        assertEquals(captor.getValue().getAuthor(), todoList.get(0).getAuthor());
    }


    @Test
    public void update() {
        doNothing().when(todoDaoMock).update(any());
        todoServiceMock.update(todoList.get(0));
        verify(todoServiceMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(todoDaoMock);
        assertEquals(captor.getValue().getAuthor(), todoList.get(0).getAuthor());
    }

    @Test
    public void delete() {
        doNothing().when(todoDaoMock).delete(any());
        todoServiceMock.delete(todoList.get(0).getId());
        verify(todoServiceMock, times(1)).delete(captorLong.capture());
        Mockito.verifyNoMoreInteractions(todoServiceMock);

    }


    @Test
    public void deleteAll() {
        doNothing().when(todoDaoMock).deleteAll();
        Mockito.when(todoDaoMock.findAll()).thenReturn(todoList);
        todoServiceMock.deleteAll();
        verify(todoServiceMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void findById() {
        Mockito.when(todoDaoMock.findById(any())).thenReturn(todo);
        Mockito.when(todoServiceMock.todoDao()).thenReturn(todoDaoMock);
        Mockito.when(todoServiceMock.findById(Mockito.any())).thenCallRealMethod();
        Todo result = (Todo) todoServiceMock.findById(todoList.get(0).getId());
        assertEquals(result.getAuthor(), todo.getAuthor());

    }


    @Test
    public void findAll() {
        Mockito.when(todoDaoMock.findAll()).thenReturn(todoList);
        Mockito.when(todoServiceMock.todoDao()).thenReturn(todoDaoMock);
        Mockito.when(todoServiceMock.findAll()).thenCallRealMethod();
        List<Todo> result = todoDaoMock.findAll();
        assertEquals(result.size(), todoList.size());

    }

}
