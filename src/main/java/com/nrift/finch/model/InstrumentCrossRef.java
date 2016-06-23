package com.nrift.finch.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 24/9/12
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity()
public class InstrumentCrossRef implements Serializable {
    private InstrumentCrossRefKey pk;
    private Instrument instrument;
    private String status = "NORMAL";

    public InstrumentCrossRef(final int codeType, final String code, final Instrument instrument) {
        this.pk = new InstrumentCrossRefKey(codeType, code);
        this.instrument = instrument;
    }

    @EmbeddedId
    public InstrumentCrossRefKey getPk() {
        return pk;
    }

    public void setPk(InstrumentCrossRefKey pk) {
        this.pk = pk;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional=false)
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
        return "InstrumentCrossRef{" +
                "pk=" + pk +
                ", instrument=" + instrument.getPk() +
                '}';
    }

    /**
     * Created with IntelliJ IDEA.
     * User: debasishg
     * Date: 27/9/12
     * Time: 11:33 AM
     * To change this template use File | Settings | File Templates.
     */

    @Embeddable
    public static class InstrumentCrossRefKey implements Serializable {
        private int codeType;
        private String code;

        public InstrumentCrossRefKey() {
        }

        public InstrumentCrossRefKey(int codeType, String code) {
            this.codeType = codeType;
            this.code = code;
        }

        public int getCodeType() {
            return codeType;
        }

        public void setCodeType(int codeType) {
            this.codeType = codeType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InstrumentCrossRefKey that = (InstrumentCrossRefKey) o;

            if (codeType != that.codeType) return false;
            if (code != null ? !code.equals(that.code) : that.code != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = codeType;
            result = 31 * result + (code != null ? code.hashCode() : 0);
            return result;
        }
    }
}
