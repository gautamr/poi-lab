package com.nrift.finch.domain;

import com.google.common.base.Optional;
import com.nrift.finch.model.Trade;

import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 28/9/12
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TradeRepository implements Repository<Trade, Integer> {
    @Override
    public Optional<Trade> getByPrimaryKey(Integer key) {
        return Optional.absent();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Trade> getAll() {
        return Collections.emptyList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Optional<Trade> cancel(Integer key) {
        return Optional.absent();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
