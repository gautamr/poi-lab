package com.nrift.finch.domain;

import com.google.common.base.Optional;
import com.nrift.finch.model.Instrument;
import com.nrift.finch.model.InstrumentCrossRef;
import com.nrift.finch.model.InstrumentNameCrossRef;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 27/9/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentRepository implements Repository<Instrument, Integer> {
    // can be injected
    private final EntityManager em;

    public InstrumentRepository(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Instrument> getByPrimaryKey(Integer key) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Instrument> q = cb.createQuery(Instrument.class);
        Root<Instrument> c = q.from(Instrument.class);
        ParameterExpression<Integer> p = cb.parameter(Integer.class);
        q.select(c).where(cb.equal(c.get("pk"), p));
        TypedQuery<Instrument> query = em.createQuery(q);
        query.setParameter(p, key);
        Instrument i = query.getSingleResult();
        if (i == null)
            return Optional.absent();
        else
            return Optional.of(i);
    }

    @Override
    public Collection<Instrument> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Instrument> q = cb.createQuery(Instrument.class);
        Root<Instrument> c = q.from(Instrument.class);
        q.select(c);
        TypedQuery<Instrument> query = em.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Optional<Instrument> cancel(final Integer pk) {
        Optional<Instrument> oi = getByPrimaryKey(pk);
        Instrument i = null;
        if (oi.isPresent())
            i = oi.get();
        else
            return oi;

        // main table
        i.setStatus("CANCEL");

        // cross refs
        Set<InstrumentCrossRef> crefs = i.getCrossRefs();
        for(InstrumentCrossRef cref : crefs) {
            cref.setStatus("CANCEL");
        }

        // name cross refs
        Set<InstrumentNameCrossRef> ncrefs = i.getNameCrossRefs();
        for(InstrumentNameCrossRef ncref : ncrefs) {
            ncref.setStatus("CANCEL");
        }
        return Optional.of(i);
    }
}
