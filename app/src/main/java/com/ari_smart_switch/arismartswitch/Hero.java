package com.ari_smart_switch.arismartswitch;


import java.io.Serializable;

/**
 * Created by Belal on 9/9/2017.
 */

class Hero {
    private int id;
    private String status;
    private String devLabel;
    private String cur;


    public Hero(int id, String status, String devLabel, String cur) {
        this.id = id;
        this.status = status;
        this.devLabel = devLabel;
        this.cur = cur;




    }

    public int getId() {
        return id;
    }

    public String getName() {
        return status;
    }

    public String getDevLabel() {
        return devLabel;
    }

    public String getCur() {
        return cur;
    }






}