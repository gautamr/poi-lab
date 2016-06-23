package com.nrift.finch.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 24/9/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity()
public class InstrumentNameCrossRef implements Serializable {
    private InstrumentNameCrossRefKey pk;
    private Instrument instrument;
    private String status = "NORMAL";

    public InstrumentNameCrossRef(final String nameType, final String name, final Instrument instrument) {
        this.pk = new InstrumentNameCrossRefKey(nameType, name);
        this.instrument = instrument;
    }

    @EmbeddedId
    public InstrumentNameCrossRefKey getPk() {
        return pk;
    }

    public void setPk(InstrumentNameCrossRefKey pk) {
        this.pk = pk;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name="instrumentPk",referencedColumnName="pk")
    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InstrumentNameCrossRef{" +
                "pk=" + pk +
                ", instrument=" + instrument.getPk() +
                '}';
    }

    /**
     * Created with IntelliJ IDEA.
     * User: debasishg
     * Date: 27/9/12
     * Time: 11:39 AM
     * To change this template use File | Settings | File Templates.
     */

    @Embeddable
    public static class InstrumentNameCrossRefKey implements Serializable {
        private String nameType;
        private String name;

        public InstrumentNameCrossRefKey() {
        }

        public InstrumentNameCrossRefKey(String nameType, String name) {
            this.nameType = nameType;
            this.name = name;
        }

        public String getNameType() {
            return nameType;
        }

        public void setNameType(String nameType) {
            this.nameType = nameType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InstrumentNameCrossRefKey that = (InstrumentNameCrossRefKey) o;

            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (nameType != null ? !nameType.equals(that.nameType) : that.nameType != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = nameType != null ? nameType.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }
}
