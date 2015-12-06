package com.dxtre.www.colas.cls;

/**
 * Created by DXtre on 6/12/15.
 */
public class Cola {

    private String idCola;
    private String userName;
    private String time;
    private String state;

    public Cola(String idCola, String userName, String time, String state) {
        this.idCola = idCola;
        this.userName = userName;
        this.time = time;
        this.state = state;
    }

    public String getIdCola() {
        return idCola;
    }

    public void setIdCola(String idCola) {
        this.idCola = idCola;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}