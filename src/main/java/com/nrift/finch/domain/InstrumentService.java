package com.nrift.finch.domain;

import com.google.common.base.Optional;
import com.nrift.finch.model.Instrument;
import com.nrift.finch.model.Trade;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 27/9/12
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentService {
    private static Logger logger = Logger.getLogger(InstrumentService.class.getName());

    // to be injected
    private final EntityManager em;
    private final InstrumentRepository repo;
    private final TradingService ts;

    public InstrumentService(EntityManager em) {
        this.em = em;
        this.repo = new InstrumentRepository(em);
        this.ts = new TradingService(em);
    }

    public Optional<Instrument> createInstrument(final Instrument i) {
        EntityTransaction t = null;
        try {
            t = em.getTransaction();
            t.begin();
            em.persist(i);
            em.flush();
            em.refresh(i);
            t.commit();
            return Optional.of(i);
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
            return Optional.absent();
        }
    }

    public Collection<Instrument> getAllInstruments() {
        return repo.getAll();
    }

    public Optional<Instrument> getByPrimaryKey(Integer key) {
        return repo.getByPrimaryKey(key);
    }

    public Optional<Instrument> cancelInstrument(final int pk) {
        EntityTransaction userTransaction = null;
        Instrument i = null;
        try {
            // in real life this will be declarative w/ Spring annotation
            userTransaction = em.getTransaction();
            userTransaction.begin();

            // check if instrument cancellable
            List<Trade> openTrades = ts.openTradesForInstrument(pk);
            if (!openTrades.isEmpty()) {
                logger.info("Open trades exist for instrument pk = " + pk + " hence cannot be cancelled");
                return Optional.absent();
            }

            // do cancel
            Optional<Instrument> oi = repo.cancel(pk);
            if (oi.isPresent()) i = oi.get();
            else throw new RuntimeException("Instrument not found for cancellation");

            em.flush();
            em.refresh(i);
            return Optional.of(i);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (i != null) userTransaction.commit();
            else userTransaction.rollback();
        }
    }
}
