package com.ari_smart_switch.arismartswitch;


import java.io.Serializable;

/**
 * Created by Belal on 9/9/2017.
 */

class Hero {
    private int id;
    private String status;


    public Hero(int id, String status) {
        this.id = id;
        this.status = status;


    }

    public int getId() {
        return id;
    }

    public String getName() {
        return status;
    }




}