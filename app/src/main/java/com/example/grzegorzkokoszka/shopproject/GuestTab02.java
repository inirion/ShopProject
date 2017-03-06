package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class GuestTab02 extends Fragment {
    //Singleton
    public static GuestTab02 newInstance() {
        GuestTab02 fragment = new GuestTab02();
        return fragment;
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    public GuestTab02() {

    }
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private static final int DispalyLimit = 5;
    private static boolean sortAscPrice = false;
    private static boolean sortDescPrice = false;
    private static boolean sortAscTime = false;
    private static boolean sortDescTime = false;
    private Context ctx;
    private static View rootView = null;
    private String SORT = "";
    private static DisplayMetrics displaymetrics = new DisplayMetrics();
    public void callParentMethod(){
        getActivity().onBackPressed();
    }
    public String timeConvert(long time) {
        short dni = (short)(time/24/60);
        short godziny = (short)(time/60%24);
        short minuty = (short)(time%60);
        String doKonca = "";
        if(dni == 0)doKonca+=dni +" dni ";;
        if(dni > 0){
            if(dni == 1){
                doKonca+=dni +" dzień ";
            }else if(dni > 1){
                doKonca+=dni +" dni ";
            }
        }
        if(godziny == 0)doKonca+=godziny +" godzin ";;
        if(godziny > 0){
            if(godziny == 1){
                doKonca+=godziny +" godzina ";
            }else if(godziny > 1 && godziny<10 ){
                doKonca+=godziny +" godziny ";
            }else if(godziny >=10){
                doKonca+=godziny +" godzin ";
            }
        }
        if(minuty == 0)doKonca+=minuty +" minut";;
        if(minuty > 0){
            if(minuty == 1){
                doKonca+=minuty +" minuta";
            }else if(minuty > 1 && minuty<10 ){
                doKonca+=minuty +" minuty";
            }else if(minuty >=10){
                doKonca+=minuty +" minut";
            }
        }
        return doKonca;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callParentMethod();
        ctx = getActivity().getApplicationContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rootView= inflater.inflate(R.layout.fragment_search, container, false);
        rootView.findViewById(R.id.Search).clearFocus();
        Intent intent = getActivity().getIntent();

        if(User.isRefresh()) {
            try {
                Log.d(TAG, "onCreateView: asdasdasdasdasdasd");
                User.clearProducts();
                if(User.isQuerySelected())
                GetDataFromServer("");
                User.setRefresh(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(!User.isQuerySelected()) {
            rootView.findViewById(R.id.SortByTimeBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.SortByTimeImgBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
            ((Activity)rootView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        }else{
            try {
                GetDataFromServer("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        rootView.findViewById(R.id.SortByTimeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.isQuerySelected() == true && Product.getCount() >0) {
                    if (sortAscTime) {
                        sortAscTime = !sortAscTime;
                        sortDescTime = true;
                    } else {
                        sortAscTime = !sortAscTime;
                        sortDescTime = false;
                    }
                    Log.d(TAG, "Sorted Asc: " + sortAscTime);
                    Log.d(TAG, "Sorted Desc: " + sortDescTime);
                    try {
                        User.clearProducts();
                        GetDataFromServer("finishes "+(sortAscTime ? "Asc":"Desc"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rootView.findViewById(R.id.SortByTimeImgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.isQuerySelected() == true && Product.getCount() >0) {
                    if (sortAscTime) {
                        sortAscTime = !sortAscTime;
                        sortDescTime = true;
                    } else {
                        sortAscTime = !sortAscTime;
                        sortDescTime = false;
                    }
                    Log.d(TAG, "Sorted Asc: " + sortAscTime);
                    Log.d(TAG, "Sorted Desc: " + sortDescTime);
                    try {
                        User.clearProducts();
                        GetDataFromServer("finishes "+(sortAscTime ? "Asc":"Desc"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        rootView.findViewById(R.id.prevBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getPageNum() > 1) {
                    User.setPageNum(User.getPageNum()-1);
                    DisplayData(rootView, (Activity) rootView.getContext());
                }
                Log.d(TAG, "Value : " + User.getPageNum());
                Log.d(TAG, "Query Selected " + User.isQuerySelected());
            }
        });
        rootView.findViewById(R.id.NextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getPageNum() <= Product.getCount()/DispalyLimit) {
                    User.setPageNum(User.getPageNum()+1);
                    DisplayData(rootView, (Activity) rootView.getContext());
                }
                Log.d(TAG, "Value : " + User.getPageNum());
                Log.d(TAG, "Query Selected " + User.isQuerySelected());
            }
        });

        ((Button)rootView.findViewById(R.id.SearchBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = ((EditText)rootView.findViewById(R.id.Search)).getText().toString();
                hideKeyboardFrom(ctx,getView());
                if(!query.trim().isEmpty()){
                    User.setQueryText(query);
                    User.setQuerySelected(true);
                    User.clearProducts();
                    try {
                        GetDataFromServer("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(rootView.findViewById(R.id.SortByTimeBtn).getVisibility() == View.INVISIBLE) {
                        rootView.findViewById(R.id.SortByTimeBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.SortByTimeImgBtn).setVisibility(View.VISIBLE);
                    }
                    Log.d(TAG,"Query Selected " + User.isQuerySelected());
                    Log.d(TAG,"Value : " + User.getPageNum());
                }else{
                    User.setQueryText(query);
                    User.setQuerySelected(true);
                    User.clearProducts();
                    rootView.findViewById(R.id.SortByTimeBtn).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.SortByTimeImgBtn).setVisibility(View.VISIBLE);
                    try {
                        User.clearProducts();
                        GetDataFromServer("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG,"Query Selected " + User.isQuerySelected());
                    Log.d(TAG,"Value : " + User.getPageNum());
                }
            }
        });


        return rootView;
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    public void DisplayData(View rootView, Activity activity){
        if(Product.getCount() > DispalyLimit) {
            if (User.getPageNum() == 1) {
                rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
            }
            if (User.getPageNum() > Product.getCount()/DispalyLimit) {
                rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
            }
            if(User.getPageNum() > 1 && User.getPageNum() <= Product.getCount() / DispalyLimit){
                rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
            }
        }else{
            rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
        }
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ProductsView);
        adapter = new ProductAdapter(activity, fillData(User.getPageNum()),0);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
    public void GetDataFromServer(String sort) throws JSONException {

        String cat = User.getSelectedCategoriesIds();
        OkHttpClient client = new OkHttpClient();
        Log.d(TAG, "http://projekt-aukcje.herokuapp.com/api/auctions?nameSearch=" + User.getQueryText()+ "&orderBy="+sort+"&categorySearch="+cat);
        Request request = new Request.Builder()
                .url("http://projekt-aukcje.herokuapp.com/api/auctions?nameSearch=" + User.getQueryText()+ "&orderBy="+sort+"&categorySearch="+cat)
                .get()
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        try {
                            Log.d(TAG, "onResponseSearch: " + res);
                            JSONArray json = new JSONArray(res);
                            if(json != null) {
                                for(int i = 0 ; i < json.length() ; i++) {
                                    JSONObject e = json.getJSONObject(i);
                                    JSONObject ee = e.getJSONObject("topBid");
                                    float price = Float.parseFloat(ee.getString("value"));
                                    String highestBitter = ee.getString("authorId");
                                    String title = e.getString("name");
                                    String desc = e.getString("description");
                                    String author = e.getString("authorId");
                                    String auction = e.getString("id");
                                    String finish = e.getString("finishes");
                                    String finished = e.getString("finished");
                                    int index = finish.indexOf('T');
                                    finish = finish.substring(0,index) +" " + finish.substring(index+1,finish.length()-5);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date startDate = new Date();
                                    Date endDate;
                                    long time = 0;
                                    try {
                                        endDate= sdf.parse(finish);
                                        time = getDateDiff(startDate,endDate,TimeUnit.MINUTES);
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
                                    Log.d(TAG, "Time to end: " + timeConvert(time));
                                    int picture = 1;
                                    User.addProduct(new Product(title,desc,picture,price,author,auction,highestBitter,timeConvert(time),finished.equals("true") ? true:false));
                                }
                                Product.setCount(json.length());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DisplayData(rootView, (Activity) rootView.getContext());
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public static List<Product> fillData(int pageNum) {
        List<Product> products = new ArrayList<>();
        Log.d(TAG, "DisplayLimit: " + DispalyLimit + " pageNum " + pageNum + " Max " + Product.getCount());
        for(int j = DispalyLimit*(pageNum-1) ; j < DispalyLimit+((pageNum-1)*DispalyLimit) && j < Product.getCount(); j++){
            Product product = new Product(User.getProducts().get(j).getName(),
                    User.getProducts().get(j).getDescription(),User.getProducts().get(j).getPicture(),
                    User.getProducts().get(j).getPrice(),User.getProducts().get(j).getAuthorId(),User.getProducts().get(j).getAuctionId(),
                    User.getProducts().get(j).getHighestBitterId(),User.getProducts().get(j).getTime(),User.getProducts().get(j).isFinished());
            products.add(product);
        }
        return products;
    }
}
