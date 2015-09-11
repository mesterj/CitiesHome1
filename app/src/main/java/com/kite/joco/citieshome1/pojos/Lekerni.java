package com.kite.joco.citieshome1.pojos;

import com.orm.SugarRecord;

/**
 * Created by Mester József on 2015.09.11..
 */
public class Lekerni extends SugarRecord<Lekerni> {

    String irsz;

    public String getIrsz() {
        return irsz;
    }

    public void setIrsz(String irsz) {
        this.irsz = irsz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lekerni lekerni = (Lekerni) o;

        return !(irsz != null ? !irsz.equals(lekerni.irsz) : lekerni.irsz != null);

    }

    @Override
    public int hashCode() {
        return irsz != null ? irsz.hashCode() : 0;
    }
}
