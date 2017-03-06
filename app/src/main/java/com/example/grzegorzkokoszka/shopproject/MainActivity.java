package com.example.grzegorzkokoszka.shopproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xdroid.toaster.Toaster;

public class MainActivity extends AppCompatActivity {

    private String url = "http://projekt-aukcje.herokuapp.com/auth/signin";
    private String urlGetCategories = "http://projekt-aukcje.herokuapp.com/api/categories";
    private String urlGetDeliveries = "http://projekt-aukcje.herokuapp.com/api/deliveryOptions";
    private String urlGetPayments = "http://projekt-aukcje.herokuapp.com/api/paymentOptions";

    private static boolean isInitializatedCategory = false;
    private static boolean isInitializatedDelivery = false;
    private static boolean isInitializatedPayment = false;
    @Override
    public void onBackPressed() {
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginB = (Button) findViewById(R.id.loginButton);
        Button registerB = (Button) findViewById(R.id.registerRedirectButton);
        Button guestB = (Button) findViewById(R.id.btnGuest);
        ImageButton guestImgB = (ImageButton) findViewById(R.id.imgBtnGuest);


        OkHttpClient clientCategory = new OkHttpClient();
        Request requestCategory = new Request.Builder()
                .url(urlGetCategories)
                .get()
                .build();
        clientCategory.newCall(requestCategory)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        if(!isInitializatedCategory) {
                            Log.d(TAG, res);
                            try {
                                JSONArray json = new JSONArray(res);
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject e = json.getJSONObject(i);
                                    User.addCategory(new Category(e.getString("name"), e.getString("id")));
                                }
                                for(int i = 0 ; i < User.getCategories().size(); i++){
                                    Log.d(TAG, "ID : " + User.getCategories().get(i).getId() + " " +" Name : " + User.getCategories().get(i).getName());
                                }
                                isInitializatedCategory = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        OkHttpClient clientPayment = new OkHttpClient();
        Request requestPayment = new Request.Builder()
                .url(urlGetPayments)
                .get()
                .build();
        clientPayment.newCall(requestPayment)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        if(!isInitializatedPayment) {
                            Log.d(TAG, res);
                            try {
                                JSONArray json = new JSONArray(res);
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject e = json.getJSONObject(i);
                                    User.addPayments(new Payment(e.getString("id"),e.getString("name")));
                                }
                                for(int i = 0 ; i < User.getPayments().size(); i++){
                                    Log.d(TAG, "ID : " + User.getPayments().get(i).getId() + " " +" Name : " + User.getPayments().get(i).getName());
                                }
                                isInitializatedPayment = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        OkHttpClient clientDelivery = new OkHttpClient();
        Request requestDelivery = new Request.Builder()
                .url(urlGetDeliveries)
                .get()
                .build();
        clientDelivery.newCall(requestDelivery)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        if(!isInitializatedDelivery) {
                            Log.d(TAG, res);
                            try {
                                JSONArray json = new JSONArray(res);
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject e = json.getJSONObject(i);
                                    User.addDeliveries(new Delivery(e.getString("id"),e.getString("name")));
                                }
                                for(int i = 0 ; i < User.getDeliveries().size(); i++){
                                    Log.d(TAG, "ID : " + User.getDeliveries().get(i).getId() + " " +" Name : " + User.getDeliveries().get(i).getName());
                                }
                                isInitializatedDelivery = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText loginT = (EditText) findViewById(R.id.loginText);
                EditText passwordT = (EditText) findViewById(R.id.passwordText);
                if(!loginT.getText().toString().isEmpty() && !passwordT.getText().toString().isEmpty()){
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", loginT.getText().toString())
                            .add("password", passwordT.getText().toString())
                            .build();
                    Log.d(TAG, loginT.getText().toString() + " " +passwordT.getText().toString());
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    client.newCall(request)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(final Call call, IOException e) {
                                    // Error

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // For the example, you can show an error dialog or a toast
                                            // on the main UI thread
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    String res = response.body().string();
                                    Log.d(TAG, res);
                                    try {
                                        JSONObject object = new JSONObject(res);

                                        if(object.has("token")){
                                            User.setIsLogged(true);
                                            User.setJwtToken(object.getString("token"));
                                            try {
                                                JWTUtils.decoded(User.getJwtToken());
                                                User.setId(JWTUtils.getID());
                                                User.getPerson().setPassword(((EditText) findViewById(R.id.passwordText)).getText().toString());
                                                Log.d(TAG, User.getPerson().getPassword());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(MainActivity.this,LayoutActivity.class);
                                            Log.d(TAG,"Logged");
                                            startActivity(intent);
                                        }
                                        if(object.has("message")){
                                            Toaster.toast(object.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                    //// koniec pobiareania danych



                }else{
                    //warning with info
                    User.setIsLogged(false);
                    Log.d(TAG,"Wrong Input");
                }
            }
        });



        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterForm.class);
                startActivity(intent);
            }
        });


        guestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LayoutActivity.class);
                startActivity(intent);
            }
        });
        guestImgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LayoutActivity.class));
            }
        });

    }

}
