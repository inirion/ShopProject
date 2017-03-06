package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.SeekBar;


import org.json.JSONException;
import org.json.JSONObject;



import java.io.File;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xdroid.toaster.Toaster;

/**
 * Created by Grzegorz Kokoszka on 2017-02-21.
 */

public class userAddProduct extends Activity {
    private static View rootView;
    private final int categoryid[] = {R.id.category1,R.id.category2,R.id.category3,R.id.category4,R.id.category5,R.id.category6,R.id.category7,R.id.category8,R.id.category9};
    private final int deliveryid[] = {R.id.delivery1,R.id.delivery2,R.id.delivery3};
    private final int paymentid[] = {R.id.payment1,R.id.payment2};
    private Button btn;
    private File f;
    private static final int CAMERA_REQUEST = 1888;
    private static String categories = "";
    private static String delivery = "";
    private static String payment = "";
    private boolean canBeSend = false;
    private static Bitmap photoBmp = null;
    private static Date expireDate,startDate;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String url = "http://projekt-aukcje.herokuapp.com/api/auctions";
    @Override
    public void onBackPressed() {
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photoBmp = (Bitmap) data.getExtras().get("data");
            ((ImageView)rootView.findViewById(R.id.ImageCF)).setImageBitmap(photoBmp);
        }
    }
    private static Date addMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        rootView = getWindow().getDecorView().getRootView();
        btn = (Button) findViewById(R.id.AuctionPictureCF);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        final EditText tv=(EditText)findViewById(R.id.SeekBarTextCF);
        tv.setFocusable(false);
        tv.setEnabled(false);
        tv.setCursorVisible(false);
        tv.setKeyListener(null);
        tv.setBackgroundColor(Color.TRANSPARENT);
        ((SeekBar)rootView.findViewById(R.id.seekBarCF)).setMax(13);
        ((SeekBar)rootView.findViewById(R.id.seekBarCF)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: " + progress);
                tv.setText("Liczba Dni : " + String.valueOf(progress + 1));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                rootView.findViewById(R.id.AuctionDescCF).clearFocus();
                rootView.findViewById(R.id.AuctionNameCF).clearFocus();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rootView.findViewById(R.id.CancelBtnCF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.setCurrentPageFragemnt(2);
                startActivity(new Intent(userAddProduct.this,LayoutActivity.class));

            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        for(int i = 0 ; i < 9 ; i ++){
            ((CheckBox)rootView.findViewById(categoryid[i])).setTextSize(12);
            ((CheckBox)rootView.findViewById(categoryid[i])).setWidth(width/2);
            ((CheckBox)rootView.findViewById(categoryid[i])).setText(User.getCategories().get(i).getName());
            ((CheckBox)rootView.findViewById(categoryid[i])).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootView.findViewById(R.id.AuctionDescCF).clearFocus();
                    rootView.findViewById(R.id.AuctionNameCF).clearFocus();
                }
            });

        }

        for(int i = 0 ; i < 3 ; i ++){
            ((CheckBox)rootView.findViewById(deliveryid[i])).setTextSize(12);
            ((CheckBox)rootView.findViewById(deliveryid[i])).setWidth(width/2);
            ((CheckBox)rootView.findViewById(deliveryid[i])).setText(User.getDeliveries().get(i).getName());
            ((CheckBox)rootView.findViewById(deliveryid[i])).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootView.findViewById(R.id.AuctionDescCF).clearFocus();
                    rootView.findViewById(R.id.AuctionNameCF).clearFocus();
                }
            });
        }

        for(int i = 0 ; i < 2 ; i ++){
            ((CheckBox)rootView.findViewById(paymentid[i])).setTextSize(12);
            ((CheckBox)rootView.findViewById(paymentid[i])).setWidth(width/2);
            ((CheckBox)rootView.findViewById(paymentid[i])).setText(User.getPayments().get(i).getName());
            ((CheckBox)rootView.findViewById(paymentid[i])).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootView.findViewById(R.id.AuctionDescCF).clearFocus();
                    rootView.findViewById(R.id.AuctionNameCF).clearFocus();
                }
            });
        }


        rootView.findViewById(R.id.AddBtnCF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = ((EditText)rootView.findViewById(R.id.AuctionNameCF)).getText().toString();
                String Desc = ((EditText)rootView.findViewById(R.id.AuctionDescCF)).getText().toString();
                if(!Title.isEmpty() && !Desc.isEmpty()){
                    categories = "[";
                    int Catcounter = 0;
                    for(int i = 0 ; i < categoryid.length ; i ++) {
                        if(((CheckBox)rootView.findViewById(categoryid[i])).isChecked()){
                            Catcounter++;
                            categories += User.getCategoryId((String) ((CheckBox)rootView.findViewById(categoryid[i])).getText()) + ",";
                        }
                    }
                    categories = categories.substring(0,categories.length()-1);
                    categories+="]";

                    Log.d(TAG, "onClick: " + categories);


                    delivery = "[";
                    int Delcounter = 0;
                    for(int i = 0 ; i < deliveryid.length ; i ++) {
                        if(((CheckBox)rootView.findViewById(deliveryid[i])).isChecked()){
                            Delcounter++;
                            delivery += User.getDeliveryId((String) ((CheckBox)rootView.findViewById(deliveryid[i])).getText()) + ",";
                        }
                    }
                    delivery = delivery.substring(0,delivery.length()-1);
                    delivery+="]";

                    Log.d(TAG, "onClick: Delivery " + delivery);



                    payment = "[";
                    int Paycounter = 0;
                    for(int i = 0 ; i < paymentid.length ; i ++) {
                        if(((CheckBox)rootView.findViewById(paymentid[i])).isChecked()){
                            Paycounter++;
                            payment += User.getPaymentId((String) ((CheckBox)rootView.findViewById(paymentid[i])).getText()) + ",";
                        }
                    }
                    payment = payment.substring(0,payment.length()-1);
                    payment+="]";

                    Log.d(TAG, "onClickPayment: " + payment);
                    if(Catcounter != 0 && Delcounter != 0 && Paycounter != 0)
                    canBeSend = true;
                }
                if(canBeSend){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    startDate = new Date();
                    String text = tv.getText().toString();
                    String date = text.substring(text.indexOf(":")+2,text.length());

                    //expireDate = addDays(startDate,Integer.parseInt(date));
                    expireDate = addMinutesToDate(Integer.parseInt(date),startDate);
                    String startDateText = sdf.format(startDate);
                    String endDateText = sdf.format(expireDate);
                    Log.d(TAG, "onClick: " + startDateText + " " + endDateText);
                    OkHttpClient client = new OkHttpClient();

                    RequestBody formBody = new FormBody.Builder()
                            .add("started", startDateText)
                            .add("finishes",endDateText)
                            .add("name",Title)
                            .add("startPrice","1")
                            .add("description",Desc)
                            .add("deliveryOption",delivery)
                            .add("paymentOption",payment)
                            .add("categoryIds",categories)
                            .add("deliveryCost", "10")
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("authorization","Bearer " + User.getJwtToken())
                            .post(formBody)
                            .build();

                    client.newCall(request)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    String res = response.body().string();
                                    Log.d(TAG, "onResponse: "+ res);

                                    try {
                                        JSONObject message = new JSONObject(res);
                                        if(message.has("message")){
                                            Toaster.toast(message.getString("message"));

                                        }else{
                                            Log.d(TAG, res);
                                            Intent intent = new Intent(userAddProduct.this,LayoutActivity.class);
                                            User.setCurrentPageFragemnt(2);
                                            startActivity(intent);
                                        }
                                        canBeSend = false;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    if(photoBmp!= null) {
                        String imageString = ImageUtil.convert(photoBmp);

                        OkHttpClient clientt = new OkHttpClient();

                        RequestBody formBodyy = new FormBody.Builder()
                                .add("image", imageString)
                                .add("type", "base64")
                                .build();
                        Request requestt = new Request.Builder()
                                .url("https://api.imgur.com/3/image")
                                .addHeader("Authorization:","fe8b2e7f7415e63")
                                .post(formBodyy)
                                .build();

                        clientt.newCall(requestt).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String res = response.body().string();
                                Log.d(TAG, "onResponsePicture: " + res);
                            }
                        });
                    }

                }else{
                    Toaster.toast("Fill all data !!");
                }




                }
            });

    }
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }



}
