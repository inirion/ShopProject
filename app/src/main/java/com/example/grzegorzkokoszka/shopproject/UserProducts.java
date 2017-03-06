package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Grzegorz Kokoszka on 2017-02-21.
 */

public class UserProducts extends Activity {
    private static View rootView;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private static DisplayMetrics displaymetrics = new DisplayMetrics();
    private static final String TAG = MainActivity.class.getSimpleName();
    private String url = "http://projekt-aukcje.herokuapp.com/api/auctions/myPosted";
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
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
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_auctions);
        rootView = getWindow().getDecorView().getRootView();
        try {
            User.clearProducts();
            GetDataFromServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User.setCheckingMyAuctions(true);
        Log.d(TAG, "onCreate: ELOOOOOOO");
        rootView.findViewById(R.id.BackBtnMyAuctions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProducts.this,LayoutActivity.class);
                User.setCheckingMyAuctions(false);
                User.setRefresh(true);
                User.setCurrentPageFragemnt(2);
                startActivity(intent);
            }
        });
    }
    public void DisplayData(View rootView, Activity activity){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ProductsViewUserAuctions);
        adapter = new ProductAdapter(activity, fillData(),2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
    public void GetDataFromServer() throws JSONException {
        User.clearProducts();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer " + User.getJwtToken())
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
                                    int picture = 1;
                                    User.addProduct(new Product(title,desc,picture,price,author,auction,highestBitter,timeConvert(time),finished.equals("true") ? true:false));
                                }
                                Product.setCount(json.length());
                                runOnUiThread(new Runnable() {
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
    public static List<Product> fillData() {
        List<Product> products = new ArrayList<>();
        for(int j = 0 ; j < Product.getCount(); j++){
            Product product = new Product(User.getProducts().get(j).getName(),
                    User.getProducts().get(j).getDescription(),User.getProducts().get(j).getPicture(),
                    User.getProducts().get(j).getPrice(),User.getProducts().get(j).getAuthorId(),User.getProducts().get(j).getAuctionId(),
                    User.getProducts().get(j).getHighestBitterId(),User.getProducts().get(j).getTime(),User.getProducts().get(j).isFinished());
            products.add(product);
        }
        return products;
    }
}
