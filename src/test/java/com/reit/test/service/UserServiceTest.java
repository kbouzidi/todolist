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

import com.reit.dao.UserDaoImpl;
import com.reit.model.User;
import com.reit.test.dao.UserDaoTest;
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

import static org.junit.Assert.assertNotNull;
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
public class UserServiceTest {

    Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    SessionFactory sessionFactoryMock;

    @Mock
    Session sessionMock;

    @Captor
    ArgumentCaptor<User> captor;

    @Mock
    UserDaoImpl userDaoMock;

    @Spy
    List<User> userList = new ArrayList<>();

    User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(userDaoMock.getCurrentSession()).thenReturn(sessionMock);
        user = Constants.getUserSample(null);
        Mockito.spy(user);
    }

    @Test
    public void add() {
        Mockito.when(sessionMock.save(Mockito.<Class>any())).thenReturn(new Long("1"));
        Mockito.when(userDaoMock.add(Mockito.any())).thenCallRealMethod();
        Long result = (Long) userDaoMock.add(Constants.getUserSample(null));
        assertNotNull(result.longValue());
        assertEquals(result.intValue(), 1);

    }

    @Test
    public void update() {
        doNothing().when(sessionMock).update(any());
        userDaoMock.update(Constants.getUserSample(null));
        verify(userDaoMock, times(1)).update(captor.capture());
        Mockito.verifyNoMoreInteractions(userDaoMock);
        assertEquals(captor.getValue().getUserName(), Constants.getUserSample(null).getUserName());
    }

    @Test
    public void delete() {
        doNothing().when(sessionMock).delete(any());
        userDaoMock.delete(Constants.getUserSample(null));
        verify(userDaoMock, times(1)).delete(captor.capture());
        Mockito.verifyNoMoreInteractions(userDaoMock);
        assertEquals(captor.getValue().getUserName(), Constants.getUserSample(null).getUserName());

    }

    @Test
    public void deleteAll() {
        doNothing().when(sessionMock).delete(any());
        Mockito.when(userDaoMock.findAll()).thenReturn(userList);
        userDaoMock.deleteAll();
        verify(userDaoMock, times(1)).deleteAll();
        Mockito.verifyNoMoreInteractions(userDaoMock);
    }

    @Test
    public void findById() {
        Mockito.when(sessionMock.get(Mockito.<Class>any(), Mockito.any())).thenReturn(user);
        Mockito.when(userDaoMock.findById(Mockito.any())).thenCallRealMethod();
        User result = (User) userDaoMock.findById(Constants.getUserSample(null).getUserId());
        assertEquals(result.getUserName(), Constants.getUserSample(null).getUserName());

    }

    @Test
    public void findAll() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(sessionMock.createQuery(any())).thenReturn(q);
        Mockito.when(q.list()).thenReturn(userList);
        Mockito.when(userDaoMock.findAll()).thenCallRealMethod();
        List<User> result = userDaoMock.findAll();
        assertEquals(result.size(), userList.size());

    }
}
