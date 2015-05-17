package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import java.io.Serializable;

/**
 * Created by sreerag.nampoothiri on 11-May-15.
 */
public class Contact implements Serializable {


    private int id;
    private String name;
    private String phoneNumber;
    private byte[] image;

    public Contact() {

    }

    public Contact(int id, String name, String phoneNumber, byte[] image) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public Contact(String name, String phoneNumber, byte[] image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

