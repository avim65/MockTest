package com.ed.android.mocktask.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public class Employee extends RealmObject implements Parcelable {

    private int id;
    private String name;
    private int age;
    private String address;
    private String department;
    @LinkingObjects("employees")
    private final RealmResults<Company> company = null;


    public Employee() {

    }

    public Employee(int id, String name, int age, String address, String dept) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.department = dept;

    }


    protected Employee(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readInt();
        address = in.readString();
        department = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(address);
        dest.writeString(department);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
