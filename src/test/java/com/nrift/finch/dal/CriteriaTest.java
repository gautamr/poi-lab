package com.nrift.finch.dal;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.nrift.finch.model.Instrument;
import com.nrift.finch.model.InstrumentCrossRef;
import com.nrift.finch.model.InstrumentNameCrossRef;
import com.nrift.finch.model.InstrumentType;
import com.nrift.finch.domain.InstrumentRepository;
import com.nrift.finch.domain.InstrumentService;
import junit.framework.TestCase;
import org.junit.Assert;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 21/9/12
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class CriteriaTest extends TestCase {
    private static Logger logger = Logger.getLogger(CriteriaTest.class.getName());

    private EntityManagerFactory factory;

    private EntityManager em;

    private Connection connection;

    public CriteriaTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        try {
            factory = Persistence.createEntityManagerFactory("FinchPersistence");
            em = factory.createEntityManager ( );
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            logger.severe("Exception: " + ex );
            Assert.assertTrue(false);
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        logger.info("Shutting down Hibernate JPA layer.");
        if (em != null) {
            em.close();
        }
        if (factory != null) {
            factory.close();
        }
        logger.info("Stopping in-memory HSQL database.");
        try {
            connection.createStatement().execute("SHUTDOWN");
        } catch (Exception ex) {}
    }

    public void testInstrumentTypeQuery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(InstrumentType.class);
        Root<InstrumentType> tree = cq.from(InstrumentType.class);
        cq.select(tree);

        Query query = em.createQuery(cq);
        @SuppressWarnings ("unchecked")
        List<InstrumentType> queryResult = query.getResultList();

        Assert.assertNotNull(queryResult);
        System.out.println("size = " + queryResult.size());
        System.out.println("res = " + queryResult);
        Assert.assertEquals(queryResult.size(), 2);
    }

    public void testInstrumentInsert() {
        Instrument i = new Instrument();
        InstrumentType it = new InstrumentType();
        it.setPk(1);
        it.setType("equity");
        i.setType(it);
        i.setListed(true);

        InstrumentCrossRef cr1 = new InstrumentCrossRef(1, "D-123", i);
        InstrumentCrossRef cr2 = new InstrumentCrossRef(2, "E-123", i);

        InstrumentNameCrossRef ncr = new InstrumentNameCrossRef("english", "google", i);

        Set<InstrumentCrossRef> crs = new HashSet();
        crs.add(cr1);
        crs.add(cr2);
        i.setCrossRefs(crs);

        Set<InstrumentNameCrossRef> ncrs = new HashSet();
        ncrs.add(ncr);
        i.setNameCrossRefs(ncrs);

        InstrumentService is = new InstrumentService(em);
        Optional<Instrument> oi = is.createInstrument(i);
        if (oi.isPresent()) i = oi.get();
        else fail("Failed to create instrument from " + i);

        // fetch all
        Collection<Instrument> instruments = is.getAllInstruments();
        Assert.assertEquals(instruments.size(), 1);

        // fetch by primary key
        Instrument instrument = is.getByPrimaryKey(1).get();
        Assert.assertNotNull(instrument);
        Assert.assertEquals(instrument.getPk(), 1);

        // cancel
        Optional<Instrument> oci = is.cancelInstrument(1);
        Instrument ci = null;
        if (oci.isPresent()) ci = oci.get();
        else fail("must be cancellable");

        // check if cancel done
        Assert.assertEquals(ci.getStatus(), "CANCEL");

        Assert.assertEquals(Iterables.all(ci.getCrossRefs(),
            new Predicate<InstrumentCrossRef>() {
                @Override
                public boolean apply(InstrumentCrossRef instrumentCrossRef) {
                return instrumentCrossRef.getStatus().equals("CANCEL");
                }
            }), true);

        Assert.assertEquals(Iterables.all(ci.getNameCrossRefs(),
            new Predicate<InstrumentNameCrossRef>() {
                @Override
                public boolean apply(InstrumentNameCrossRef instrumentNameCrossRef) {
                    return instrumentNameCrossRef.getStatus().equals("CANCEL");
                }
            }), true);

        Assert.assertEquals(is.getByPrimaryKey(1).get().getStatus(), "CANCEL");
        Assert.assertEquals(Iterables.all(is.getByPrimaryKey(1).get().getCrossRefs(),
            new Predicate<InstrumentCrossRef>() {
                @Override
                public boolean apply(InstrumentCrossRef instrumentCrossRef) {
                    return instrumentCrossRef.getStatus().equals("CANCEL");
                }
            }), true);
    }
}
