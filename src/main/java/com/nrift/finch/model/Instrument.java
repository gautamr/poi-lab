package com.nrift.finch.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 24/9/12
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity()
public class Instrument implements Serializable {
    private int pk;
    private InstrumentType type;
    private boolean listed;
    private String status = "NORMAL";  // default value in JPA
    private Set<InstrumentCrossRef> crossRefs;
    private Set<InstrumentNameCrossRef> nameCrossRefs;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name="typePk", nullable=false, updatable=false)
    public InstrumentType getType() {
        return type;
    }

    public void setType(InstrumentType type) {
        this.type = type;
    }

    @Basic()
    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="instrument",targetEntity=InstrumentCrossRef.class,
               fetch=FetchType.EAGER)
    public Set<InstrumentCrossRef> getCrossRefs() {
        return crossRefs;
    }

    public void setCrossRefs(Set<InstrumentCrossRef> crossRefs) {
        this.crossRefs = crossRefs;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="instrument",targetEntity=InstrumentNameCrossRef.class,
               fetch=FetchType.EAGER)
    public Set<InstrumentNameCrossRef> getNameCrossRefs() {
        return nameCrossRefs;
    }


    public void setNameCrossRefs(Set<InstrumentNameCrossRef> nameCrossRefs) {
        this.nameCrossRefs = nameCrossRefs;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "pk=" + pk +
                ", type=" + type +
                ", listed=" + listed +
                ", status=" + status +
                ", crossRefs=" + crossRefs +
                ", nameCrossRefs=" + nameCrossRefs +
                '}';
    }
}
