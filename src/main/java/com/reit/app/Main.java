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
package com.reit.app;

import com.reit.controller.MainController;
import com.reit.service.ProjectService;
import com.reit.service.TaskService;
import com.reit.service.UserService;
import spark.servlet.SparkApplication;

/**
 * <p>
 * Web server launcher.</p>
 * <h1 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>Target</h1>
 * <lu>
 * <li>{@link TaskService}</li>
 * <li>{@link ProjectService}</li>
 * <li>{@link UserService}</li>
 * </lu>
 */
public class Main implements SparkApplication {

    @Override
    public void init() {
        new MainController(new TaskService(), new ProjectService(), new UserService());
    }
}
