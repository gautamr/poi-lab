package com.nrift.finch.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: debasishg
 * Date: 24/9/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity()
public class InstrumentType implements Serializable {

    private int pk;
    private String type;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    @Basic()
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InstrumentType{" +
                "pk=" + pk +
                ", type='" + type + '\'' +
                '}';
    }
}
