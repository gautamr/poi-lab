package com.nrift.finch.domain;

import com.nrift.finch.model.Trade;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 28/9/12
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class TradingService {
    private final EntityManager em;

    public TradingService(final EntityManager em) {
        this.em = em;
    }

    public List<Trade> openTradesForInstrument(final int pk) {
        // just a query : no mutation of retrieved fields
        // hence no transaction boundary for this
        // expectation : it should work in transaction boundaries of the calling functions
        return Collections.emptyList();
    }
}
