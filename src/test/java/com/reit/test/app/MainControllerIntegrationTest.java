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
package com.reit.test.app;

import com.google.gson.Gson;
import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;
import com.reit.test.utils.Constants;

import static com.reit.test.utils.Constants.projectName;
import static com.reit.test.utils.Constants.two;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.AfterClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.utils.IOUtils;

/**
 * @author kbouzidi
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainControllerIntegrationTest {

    static Logger logger = LoggerFactory.getLogger(MainControllerIntegrationTest.class);
    Gson gson = new Gson();


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
    public void _Provisioning() {
        // add User
        String toJson = gson.toJson(Constants.getUserSample(null));
        TestResponse res = request("POST", "/user/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        User userAdded1 = gson.fromJson(res.body, User.class);


        toJson = gson.toJson(Constants.getUserSample(two.toString()));
        res = request("POST", "/user/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        User userAdded2 = gson.fromJson(res.body, User.class);

        // add Project1
        toJson = gson.toJson(Constants.getProjectSample(null));
        res = request("POST", "/project/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        Project project1 = gson.fromJson(res.body, Project.class);

        // add Project2
        toJson = gson.toJson(Constants.getProjectSample(two.toString()));
        res = request("POST", "/project/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        Project project2 = gson.fromJson(res.body, Project.class);

        // add Task
        Task task1 = Constants.getTaskSample();
        task1.setProject(project1);
        task1.setCreatedBy(userAdded1);
        toJson = gson.toJson(task1);
        res = request("POST", "/task/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        task1 = gson.fromJson(res.body, Task.class);


        // add Task SecondTask
        Task task2 = Constants.getTaskSample();
        task2.setTaskName("TASK2");
        task2.setProject(project2);
        task2.setCreatedBy(userAdded1);
        toJson = gson.toJson(task2);
        res = request("POST", "/task/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        task2 = gson.fromJson(res.body, Task.class);

        // Update task state
        res = request("PUT", "/task/" + task1.getTaskId() + "/DONE", null);
        assertEquals(200, res.status);
        assertNotNull(res.body);


        // Assigne task1 to USER1
        res = request("PUT", "/task/assign/" + task1.getTaskId() + "/"+userAdded2.getUserId(), null);
        assertEquals(200, res.status);
        assertNotNull(res.body);
        
        
        // DELETE task1
        res = request("DELETE", "/task/" + task1.getTaskId(), toJson);
        assertEquals(200, res.status);
        assertNotNull(res.body);

        // DELETE PROJECT1
        res = request("DELETE", "/project/" + project1.getProjectId(), null);
        assertEquals(200, res.status);
        assertNotNull(res.body);

        // DELETE USER1
        res = request("DELETE", "/user/" + userAdded1.getUserId(), null);
        assertEquals(200, res.status);
        assertNotNull(res.body);


    }


    @Test
    public void delete_AllTasks() {
        TestResponse res = request("DELETE", "/tasks/all", null);
        assertEquals(200, res.status);
    }

    @Test
    public void deleteAllProject() {
        TestResponse res = request("DELETE", "/projects/all", null);
        assertEquals(200, res.status);
    }

    @Test
    public void deleteAllUser() {
        TestResponse res = request("DELETE", "/users/all", null);
        assertEquals(200, res.status);
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
