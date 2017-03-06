package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

/**
 * Created by Grzegorz Kokoszka on 2017-02-21.
 */

public class userEditData extends Activity {

    private static View rootView;
    private static Context ctx;
    private boolean done = false;
    private boolean canBeSend = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String url = "https://projekt-aukcje.herokuapp.com/api/users";
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_data);
        rootView = getWindow().getDecorView().getRootView();
        ctx = rootView.getContext();
        Request request = new Request.Builder()
                .url(url+"/"+User.getId())
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
                        Log.d(TAG, res);
                        try {
                            JSONObject message = new JSONObject(res);
                            User.getPerson().setBankAccount(message.getString("bankAccount"));
                            User.getPerson().setCityName(message.getString("city"));
                            User.getPerson().setEmail(message.getString("email"));
                            User.getPerson().setFirstName(message.getString("firstName"));
                            User.getPerson().setLastName(message.getString("lastName"));
                            User.getPerson().setHouseNumber(message.getString("houseNumber"));
                            User.getPerson().setPhoneNumber(message.getString("tel"));
                            User.getPerson().setPostalCode(message.getString("postalCode"));
                            User.getPerson().setStreetName(message.getString("street"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((EditText) rootView.findViewById(R.id.FirstNameUF)).setText(User.getPerson().getFirstName());
                                    ((EditText) rootView.findViewById(R.id.LastNameUF)).setText(User.getPerson().getLastName());
                                    ((EditText) rootView.findViewById(R.id.EmailUF)).setText(User.getPerson().getEmail());
                                    ((EditText) rootView.findViewById(R.id.PasswordUF)).setText(User.getPerson().getPassword());
                                    ((EditText) rootView.findViewById(R.id.BankAccountUF)).setText(User.getPerson().getBankAccount());
                                    ((EditText) rootView.findViewById(R.id.CityNameUF)).setText(User.getPerson().getCityName());
                                    ((EditText) rootView.findViewById(R.id.StreetUF)).setText(User.getPerson().getStreetName());
                                    ((EditText) rootView.findViewById(R.id.PhoneNumberUF)).setText(User.getPerson().getPhoneNumber());
                                    ((EditText) rootView.findViewById(R.id.HouseNumberUF)).setText(User.getPerson().getHouseNumber());
                                    ((EditText) rootView.findViewById(R.id.PostalCodeUF)).setText(User.getPerson().getPostalCode());
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        canBeSend = false;
                    }
                });


        rootView.findViewById(R.id.CancelBtnUF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.setCurrentPageFragemnt(2);
                startActivity(new Intent(userEditData.this,LayoutActivity.class));
            }
        });
        rootView.findViewById(R.id.UpdateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FirstName = ((EditText)rootView.findViewById(R.id.FirstNameUF)).getText().toString();
                String LastName = ((EditText)rootView.findViewById(R.id.LastNameUF)).getText().toString();
                String Email = ((EditText)rootView.findViewById(R.id.EmailUF)).getText().toString();
                String Password = ((EditText)rootView.findViewById(R.id.PasswordUF)).getText().toString();
                String BankAccount = ((EditText)rootView.findViewById(R.id.BankAccountUF)).getText().toString();
                String City = ((EditText)rootView.findViewById(R.id.CityNameUF)).getText().toString();
                String Street = ((EditText)rootView.findViewById(R.id.StreetUF)).getText().toString();
                String Phone = ((EditText)rootView.findViewById(R.id.PhoneNumberUF)).getText().toString();
                String HouseNumber = ((EditText)rootView.findViewById(R.id.HouseNumberUF)).getText().toString();
                String PostalCode = ((EditText)rootView.findViewById(R.id.PostalCodeUF)).getText().toString();

                if(!FirstName.trim().isEmpty() && !LastName.trim().isEmpty()&& !Email.trim().isEmpty()
                        && !Password.trim().isEmpty()&& !BankAccount.trim().isEmpty()&&!City.trim().isEmpty()&& !Street.trim().isEmpty()
                        && !HouseNumber.trim().isEmpty()&& !PostalCode.trim().isEmpty()) {
                    Log.d(TAG, FirstName+LastName+Email+Password+BankAccount+City+Street+HouseNumber+PostalCode);
                    User.getPerson().setPassword(Password);
                    canBeSend = true;

                }
                if(canBeSend) {
                    final RequestBody formBody = new FormBody.Builder()
                            .add("email", Email)
                            .add("tel", Phone)
                            .add("password", Password)
                            .add("bankAccount", BankAccount)
                            .add("firstName", FirstName)
                            .add("lastName", LastName)
                            .add("street", Street)
                            .add("houseNumber", HouseNumber)
                            .add("city", City)
                            .add("postalCode", PostalCode)
                            .build();
                    Request request = new Request.Builder()
                            .url(url+"/"+User.getId())
                            .addHeader("authorization","Bearer " + User.getJwtToken())
                            .put(formBody)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    String res = response.body().string();

                                    JSONObject message = null;
                                    try {
                                        message = new JSONObject(res);
                                        if(message.has("message")){
                                            Toaster.toast(message.getString("message"));

                                        }else{
                                            Log.d(TAG, res);
                                            User.setCurrentPageFragemnt(2);
                                            Intent intent = new Intent(userEditData.this,LayoutActivity.class);
                                            startActivity(intent);
                                        }
                                        canBeSend = false;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                }else{
                    Toaster.toast("Fill all data !!");
                }
            }
        });
    }
}
