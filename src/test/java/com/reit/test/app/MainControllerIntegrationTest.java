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

    Project projectAdded;

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
    public void _add1User() {
        String toJson = gson.toJson(Constants.getUserSample(null));
        TestResponse res = request("POST", "/user/add", toJson);
        assertEquals(200, res.status);

    }

    @Test
    public void _1_Provisioning() {
        // add User
        String toJson = gson.toJson(Constants.getUserSample(null));
        TestResponse res = request("POST", "/user/add", toJson);
        assertEquals(200, res.status);
        User userAdded = gson.fromJson(res.body, User.class);

        // add Project
        toJson = gson.toJson(Constants.getProjectSample(null));
        res = request("POST", "/project/add", toJson);
        assertEquals(200, res.status);
        assertNotNull(res.getBody());
        Project projectAdded = gson.fromJson(res.body, Project.class);

        // add Task
        Task task = Constants.getTaskSample();
        task.setProject(projectAdded);
        task.setCreatedBy(userAdded);
        toJson = gson.toJson(task);
        res = request("POST", "/task/add", toJson);
        assertEquals(200, res.status);


    }


    //@Test
    public void _deleteAllUser() {
        TestResponse res = request("DELETE", "/user/" + Constants.getUserSample(two.toString()).getUserName(), null);
        assertEquals(200, res.status);
    }

   // @Test
    public void _deleteTask() {
        String toJson = gson.toJson(Constants.getTaskSample2());
        TestResponse res = request("POST", "/task/add", toJson);
        assertEquals(200, res.status);
    }

    //@Test
    public void _deleteProject() {
        TestResponse res = request("DELETE", "/project/" + Constants.getProjectSample(null).getProjectName(), null);
        assertEquals(200, res.status);
    }


    @Test
    public void _deleteZAllProject() {
        TestResponse res = request("DELETE", "/projects/all", null);
        assertEquals(200, res.status);
    }

    @Test
    public void _deleteZAllUser() {
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
