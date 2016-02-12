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
package com.reit.dao;

import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

public abstract class AbstractDao {

    private Session currentSession;

    private Transaction currentTransaction;

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Check if it's could ENV
     *
     * @return {@link Boolean}
     */
    private static boolean isCloudEnv() {
        try {
            if (System.getenv("DATABASE_URL") != null) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Configuration for cloud
     *
     * @return
     */
    private static Configuration getConfiguration() {

        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

            System.out.println(dbUrl);

            Configuration configuration = new Configuration()
                    .addPackage("api")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Project.class)
                    .addAnnotatedClass(Task.class)
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url", dbUrl)
                    .setProperty("hibernate.connection.username", username)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.c3p0.max_size", "1")
                    .setProperty("hibernate.c3p0.min_size", "0")
                    .setProperty("hibernate.c3p0.timeout", "5000")
                    .setProperty("hibernate.c3p0.max_statements", "100")
                    .setProperty("hibernate.c3p0.idle_test_period", "300")
                    .setProperty("hibernate.c3p0.acquire_increment", "2")
                    .setProperty("hibernate.format_sql", "true")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            return configuration;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get session factory
     *
     * @return {@link SessionFactory}
     */
    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        if (isCloudEnv()) {
            configuration = getConfiguration();
        }
        configuration.configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    /**
     * Open current session
     *
     * @return {@link Session}
     */
    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    /**
     * Open current session with transaction
     *
     * @return {@link Session}
     */
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    /**
     * Close current session
     */
    public void closeCurrentSession() {
        currentSession.close();
    }

    /**
     * Close current session with transaction
     */
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    /**
     * Get session
     *
     * @return {@link Session}
     */
    public Session getCurrentSession() {
        return currentSession;
    }

}
