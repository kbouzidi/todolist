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
package com.reit.test;

import com.google.gson.Gson;
import com.reit.dao.EStates;
import com.reit.model.Todo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.AfterClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.utils.IOUtils;

/**
 * @author kbouzidi
 */
public class MainControllerIntegrationTest {

    static Logger logger = LoggerFactory.getLogger(MainControllerIntegrationTest.class);

    Todo todo;
    Gson gson = new Gson();
    String user = "User";
    String desc = "This is a Task";
    String state = EStates.STARTED.getValue();

    @BeforeClass
    public static void setUp() {
        MainTest.main(null);
        logger.info("SERVER LAUNCHED !");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
            fail("Sending request failed: " + ex.getMessage());
        }
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void healthTest() {
        TestResponse res = request("GET", "/ping", null);
        logger.debug(res.getBody());
        assertEquals(200, res.getStatus());
        assertEquals("pong", res.getBody());
    }

    @Test
    public void addTodo() {
        todo = new Todo(Long.decode("1"), user, desc, state);
        String toJson = gson.toJson(todo);
        TestResponse res = request("POST", "/add", toJson);
        assertEquals(200, res.status);
    }

    // @Test
    public void getTodos() {
        TestResponse res = request("GET", "/todos", null);
        List<Map<String, Objects>> todoList = res.getTodoList();
        assertEquals(200, res.status);
        assertEquals(user, todoList.get(0).get("author"));
        assertEquals(desc, todoList.get(0).get("description"));
        assertEquals(state, todoList.get(0).get("state"));
    }

    @Test
    public void updateTodo() {
        final String DONE = "DONE";
        todo = new Todo(Long.decode("2"), user, desc, state);
        String toJson = gson.toJson(todo);
        TestResponse res = request("POST", "/add", toJson);
        assertEquals(200, res.status);
        todo = res.getTodo();
        todo.setState(DONE);
        String jsonObject = gson.toJson(todo);

        res = request("PUT", "/update", jsonObject);
        assertEquals(200, res.status);
        assertEquals(DONE, res.getTodo().getState());
    }

    @Test
    public void deleteTodo() {
        TestResponse res = request("GET", "/todos", null);
        List<Map<String, Objects>> todoList = res.getTodoList();
        assertEquals(200, res.status);
        Map<String, Objects> result = todoList.get(0);
        Double value = Double.parseDouble(String.valueOf(result.get("id")));
        res = request("DELETE", "/delete/" + value.toString(), null);
        assertEquals(200, res.status);
        assertNotNull(res.getBody());
    }

    private TestResponse request(String method, String path, String data) {
        HttpURLConnection connection;
        try {
            // Listening to embedded Spark Jetty server port 4567
            URL url = new URL("http://localhost:4567" + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if (data != null) {
                connection.getOutputStream().write(data.getBytes());
            }

            if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
                String body = IOUtils.toString(connection.getInputStream());
                return new TestResponse(connection.getResponseCode(), body);
            } else {
                String body = IOUtils.toString(connection.getErrorStream());
                return new TestResponse(connection.getResponseCode(), body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

}
