package com.ed.android.mocktask.models;

import android.os.Parcel;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



public class Company extends RealmObject  {

    @PrimaryKey
    private int id;
    private long empCount;
    private String name;
    private String address;
    private int claps;
    private RealmList<Employee> employees = null;

    public Company() {

    }


    public Company(int id, long empCount, String name, String address, int claps, RealmList<Employee> emloyees) {
        this.id = id;
        this.empCount = empCount;
        this.name = name;
        this.address = address;
        this.claps = claps;
        this.employees = emloyees;
    }


    protected Company(Parcel in) {
        id = in.readInt();
        empCount = in.readLong();
        name = in.readString();
        address = in.readString();
        claps = in.readInt();
    }



    public RealmList<Employee> getEmloyees() {
        return employees;
    }

    public void setEmloyees(RealmList<Employee> emloyees) {
        this.employees = emloyees;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEmpCount() {
        return empCount;
    }

    public void setEmpCount(long empCount) {
        this.empCount = empCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClaps() {
        return claps;
    }

    public void setClaps(int claps) {
        this.claps = claps;
    }
}
