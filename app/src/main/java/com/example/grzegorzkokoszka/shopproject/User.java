package com.example.grzegorzkokoszka.shopproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.test.PerformanceTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Grzegorz Kokoszka on 2017-01-02.
 *
 * User class containing Static fields so whole application can modify content of this class, it also
 * gives easy access to fields when needed
 *
 */

public class User {
    private static Person person;
    private static List<Category> categories = new ArrayList<>();
    private static List<Delivery> deliveries = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();
    private static List<String> selectedCategories = new ArrayList<>();

    public static int getCurrentPageFragemnt() {
        return currentPageFragemnt;
    }

    public static void setCurrentPageFragemnt(int currentPageFragemnt) {
        User.currentPageFragemnt = currentPageFragemnt;
    }

    private static int currentPageFragemnt;

    public static boolean isCheckingMyAuctions() {
        return checkingMyAuctions;
    }

    public static void setCheckingMyAuctions(boolean checkingMyAuctions) {
        User.checkingMyAuctions = checkingMyAuctions;
    }

    public static boolean isCheckingMyBids() {
        return checkingMyBids;
    }

    public static void setCheckingMyBids(boolean checkingMyBids) {
        User.checkingMyBids = checkingMyBids;
    }

    private static boolean checkingMyAuctions;
    private static boolean checkingMyBids;


    public static boolean isRefresh() {
        return refresh;
    }

    public static void setRefresh(boolean refresh) {
        User.refresh = refresh;
    }

    private static boolean refresh;

    public static List<Product> getProducts() {
        return products;
    }

    public static void addProduct(Product prod) {
        User.products.add(prod);
    }

    public static void clearProducts(){
        products.clear();
    }

    private static List<Product> products = new ArrayList<>();
    private static String Id;
    private static boolean isLogged;
    private static boolean querySelected;
    private static String queryText;
    private static String jwtToken;

    public static int getPageNum() {
        return pageNum;
    }

    public static void setPageNum(int pageNum) {
        User.pageNum = pageNum;
    }

    private static int pageNum;

    public static void reset(){
        selectedCategories.clear();
        Id = "";
        pageNum = 1;
        isLogged = false;
        querySelected = false;
        queryText = "";
        jwtToken = "";
        currentPageFragemnt = 1;
        products.clear();
        person.reset();
    }
    public static List<String> getSelectedCategories(){
        return selectedCategories;
    }

    public static String getSelectedCategoriesIds(){
        String catIds = "";
        if(selectedCategories.size()>0) {
            for (int i = 0; i < selectedCategories.size(); i++) {
                for(int j = 0 ; j < categories.size(); j++){
                    if(categories.get(j).getName().equals(selectedCategories.get(i))){
                        catIds += categories.get(j).getId() + ",";
                    }
                }
            }
            catIds = catIds.substring(0, catIds.length() - 1);
        }
        return catIds;
    }

    public static String getDeliveriesNames(List<String> deliveryIds){
        String delIds = "";
            for (int i = 0; i < deliveryIds.size(); i++) {
                for(int j = 0 ; j < deliveries.size(); j++){
                    if(deliveries.get(j).getId().equals(deliveryIds.get(i))){
                        delIds += deliveries.get(i).getName() + ", ";
                    }
                }
            }
            delIds = delIds.substring(0, delIds.length() - 2);
        return delIds;
    }
    public static String getPaymentNames(List<String> paymentIds){

        String delIds = "";
        for (int i = 0; i < paymentIds.size(); i++) {
            Log.d(TAG, "Payments" + paymentIds.get(i));
            for(int j = 0 ; j < payments.size(); j++){
                if(payments.get(j).getId().equals(paymentIds.get(i))){
                    delIds += payments.get(i).getName() + ", ";
                }
            }
        }

        delIds = delIds.substring(0, delIds.length() - 2);
        return delIds;
    }

    public static void addSelectedCategories(String category){
        selectedCategories.add(category);
    }

    public static void deleteSelectedCategory(String category){
        if(selectedCategories.contains(category)){
            selectedCategories.remove(category);
        }
    }


    public static List<Payment> getPayments() {
        return payments;
    }

    public static void addPayments(Payment pay) {
        User.payments.add(pay);
    }

    public static List<Delivery> getDeliveries() {
        return deliveries;
    }

    public static void addDeliveries(Delivery deli) {
        User.deliveries.add(deli);
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static String getCategoryId(String name) {
        for(int i = 0 ; i < categories.size(); i++){
            if(categories.get(i).getName().equals(name)){
                return categories.get(i).getId();
            }
        }
        return null;
    }
    public static String getDeliveryId(String name) {
        for(int i = 0 ; i < deliveries.size(); i++){
            if(deliveries.get(i).getName().equals(name)){
                return deliveries.get(i).getId();
            }
        }
        return null;
    }
    public static String getPaymentId(String name) {
        for(int i = 0 ; i < payments.size(); i++){
            if(payments.get(i).getName().equals(name)){
                return payments.get(i).getId();
            }
        }
        return null;
    }


    public static void addCategory(Category cat) {
        User.categories.add(cat);
    }
    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String jwtToken) {
        User.jwtToken = jwtToken;
    }


    public static boolean isQuerySelected() {
        return querySelected;
    }

    public static void setQuerySelected(boolean querySelected) {
        User.querySelected = querySelected;
    }

    public User(){
        person = new Person();
        pageNum = 1;
        this.isLogged = false;
        this.refresh = false;
        currentPageFragemnt = 1;
    }

    public static Person getPerson() {
        return person;
    }

    public static void setPerson(Person person) {
        User.person = person;
    }

    public static String getId() {
        return Id;
    }

    public static void setId(String id) {
        Id = id;
    }

    public static boolean isLogged() {
        return isLogged;
    }

    public static void setIsLogged(boolean isLogged) {
        User.isLogged = isLogged;
    }

    public static String getQueryText() {
        return queryText;
    }

    public static void setQueryText(String queryText) {
        User.queryText = queryText;
    }
}
