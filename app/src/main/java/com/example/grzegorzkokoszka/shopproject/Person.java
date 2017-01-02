package com.example.grzegorzkokoszka.shopproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Grzegorz Kokoszka on 2017-01-02.
 */

public class Person {
    private String Name;
    private String Surname;

    public Person(String Name, String Surname){
        this.Name = Name;
        this.Surname = Surname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }
}
