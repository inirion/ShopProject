package com.example.grzegorzkokoszka.shopproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Grzegorz Kokoszka on 2017-01-02.
 *
 * User class containing Static fields so whole application can modify content of this class, it also
 * gives easy access to fields when needed
 *
 */

public class User {
    private static Person person;
    private static int Id;
    private static boolean isLogged;

    public User(String Name,String Surname,boolean isLogged){
        person = new Person(Name,Surname);
        this.isLogged = isLogged;
    }
    public User(){
        person = new Person("Brak","Danych");
        this.isLogged = false;
    }

    public static Person getPerson() {
        return person;
    }

    public static void setPerson(Person person) {
        User.person = person;
    }

    public static int getId() {
        return Id;
    }

    public static void setId(int id) {
        Id = id;
    }

    public static boolean isLogged() {
        return isLogged;
    }

    public static void setIsLogged(boolean isLogged) {
        User.isLogged = isLogged;
    }
}
