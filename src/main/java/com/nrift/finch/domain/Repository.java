package com.nrift.finch.domain;

import com.google.common.base.Optional;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 27/9/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Repository<T, PK> {
    Optional<T> getByPrimaryKey(final PK key);
    Collection<T> getAll();
    Optional<T> cancel(PK key);
}
