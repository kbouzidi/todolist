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

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T>
 * @param <Id>
 */
public interface IGenericDao<T, Id extends Serializable> {

    /**
     * Add <code>Entity</code> associated to:
     * <ul>
     * <li>the model <code>{@link Project}</code></li>
     * <li>the model <code>{@link Task}</code></li>
     * <li>the model <code>{@link User}</code></li>
     * </ul>
     *
     * @param entity
     * <ul>
     * <li><code>{@link Project}</code></li>
     * <li><code>{@link Task}</code></li>
     * <li><code>{@link User}</code></li>
     * </ul>
     *
     */
    public void add(T entity);

    /**
     * Update <code>Entity</code> associated to:
     * <ul>
     * <li>the model <code>{@link Project}</code></li>
     * <li>the model <code>{@link Task}</code></li>
     * <li>the model <code>{@link User}</code></li>
     * </ul>
     *
     * @param entity
     * <ul>
     * <li><code>{@link Project}</code></li>
     * <li><code>{@link Task}</code></li>
     * <li><code>{@link User}</code></li>
     * </ul>
     *
     */
    public void update(T entity);

    /**
     * Find by ID <code>Entity</code> associated to:
     * <ul>
     * <li>the model <code>{@link Project}</code></li>
     * <li>the model <code>{@link Task}</code></li>
     * <li>the model <code>{@link User}</code></li>
     * </ul>
     *
     * @param id {@link Serializable}
     * @return entity
     * <ul>
     * <li><code>{@link Project}</code></li>
     * <li><code>{@link Task}</code></li>
     * <li><code>{@link User}</code></li>
     * </ul>
     *
     */
    public T findById(Id id);

    /**
     * Delete <code>Entity</code> associated to:
     * <ul>
     * <li>the model <code>{@link Project}</code></li>
     * <li>the model <code>{@link Task}</code></li>
     * <li>the model <code>{@link User}</code></li>
     * </ul>
     *
     * @param entity
     * <ul>
     * <li><code>{@link Project}</code></li>
     * <li><code>{@link Task}</code></li>
     * <li><code>{@link User}</code></li>
     * </ul>
     *
     */
    public void delete(T entity);

    /**
     * Find all <code>Entity</code> associated to:
     *
     * @return {@link List} of entities
     * <ul>
     * <li><code>{@link Project}</code></li>
     * <li><code>{@link Task}</code></li>
     * <li><code>{@link User}</code></li>
     * </ul>
     *
     */
    public List<T> findAll();

    /**
     * Delete all <code>Entites</code> associated to:
     * <ul>
     * <li>the model <code>{@link Project}</code></li>
     * <li>the model <code>{@link Task}</code></li>
     * <li>the model <code>{@link User}</code></li>
     * </ul>
     */
    public void deleteAll();

}
