package com.example.grzegorzkokoszka.shopproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Grzegorz Kokoszka on 2017-01-02.
 */

public class Person {
    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String BankAccount;
    private String CityName;
    private String StreetName;
    private String Password;
    private String Email;
    private String HouseNumber;
    private String PostalCode;
    public void reset(){
        FirstName = "";
        LastName = "";
        PhoneNumber = "";
        BankAccount = "";
        CityName= "";
        StreetName = "";
        Password = "";
        Email = "";
        HouseNumber = "";
        PostalCode = "";
    }
    public Person(){}

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
}
