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
}
