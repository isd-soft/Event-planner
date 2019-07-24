package com.inther.eventplaner.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private int id;

    public JwtResponse(String jwttoken, int id) {
        this.jwttoken = jwttoken;
        this.id =id;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public int getId() {
        return this.id;
    }
}
