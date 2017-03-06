package com.example.grzegorzkokoszka.shopproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xdroid.toaster.Toaster;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class GuestTab03 extends Fragment {
    //Singleton
    public static GuestTab03 newInstance(){
        GuestTab03 fragment = new GuestTab03();
        return fragment;
    }

    public GuestTab03(){

    }
    private static View rootView;
    private static final String TAG = MainActivity.class.getSimpleName();
    public void callParentMethod(){
        getActivity().onBackPressed();
    }
    private String url = "https://projekt-aukcje.herokuapp.com/api/users";
    private boolean canBeSend = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callParentMethod();
        rootView = inflater.inflate(R.layout.register_form,container,false);
        rootView.findViewById(R.id.CancelBtn).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.RegisterBtn).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.RegisterBtnGuest).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.LoginBtnGuest).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.LoginBtnGuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.reset();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.RegisterBtnGuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FirstName = ((EditText)rootView.findViewById(R.id.FirstNameRF)).getText().toString();
                String LastName = ((EditText)rootView.findViewById(R.id.LastNameRF)).getText().toString();
                String Email = ((EditText)rootView.findViewById(R.id.EmailRF)).getText().toString();
                String Password = ((EditText)rootView.findViewById(R.id.PasswordRF)).getText().toString();
                String BankAccount = ((EditText)rootView.findViewById(R.id.BankAccountRF)).getText().toString();
                String City = ((EditText)rootView.findViewById(R.id.CityNameRF)).getText().toString();
                String Street = ((EditText)rootView.findViewById(R.id.Street)).getText().toString();
                String Phone = ((EditText)rootView.findViewById(R.id.PhoneNumberRF)).getText().toString();
                String HouseNumber = ((EditText)rootView.findViewById(R.id.HouseNumberRF)).getText().toString();
                String PostalCode = ((EditText)rootView.findViewById(R.id.PostalCodeRF)).getText().toString();
                if(!BankAccount.trim().isEmpty()){
                    if(CardValidator.validate(BankAccount)){
                        Log.d(TAG, "onClick: " + CardValidator.getCardVendor(BankAccount));
                    }
                }
                if(!FirstName.trim().isEmpty() && !LastName.trim().isEmpty()&& !Email.trim().isEmpty()
                        && !Password.trim().isEmpty()&& !BankAccount.trim().isEmpty()&&!City.trim().isEmpty()&& !Street.trim().isEmpty()
                        && !HouseNumber.trim().isEmpty()&& !PostalCode.trim().isEmpty()) {
                    Pattern phonep = Pattern.compile("^(?:[0+]48)?\\d{9}$");
                    Matcher phonem = phonep.matcher(Phone);
                    boolean phoneOK = false;
                    if(phonem.matches()){
                        Log.d(TAG, FirstName+LastName+Email+Password+BankAccount+City+Street+HouseNumber+PostalCode);
                        Log.d(TAG, "Posłane");
                        phoneOK = true;
                    }else{
                        phoneOK = false;
                        Toaster.toast("Zły format numeru telefonu!");
                    }

                    Pattern postalp = Pattern.compile("^\\d{5}$");
                    Matcher postalm = postalp.matcher(PostalCode);
                    boolean postalOK = false;
                    if(postalm.matches()){
                        Log.d(TAG, FirstName+LastName+Email+Password+BankAccount+City+Street+HouseNumber+PostalCode);
                        Log.d(TAG, "Posłane");
                        postalOK = true;
                    }else{
                        postalOK = false;
                        Toaster.toast("Zły format kodu pocztowego!");
                    }
                    boolean houseOK = false;
                    if(Integer.parseInt(HouseNumber) > 250){
                        Toaster.toast("Zły numer domu!");
                    }else houseOK = true;
                    if(postalOK && phoneOK && houseOK) canBeSend = true;


                }
                if(canBeSend) {
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
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
                            .url(url)
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

                                    try {
                                        JSONObject message = new JSONObject(res);
                                        if(message.has("message")){
                                            Toaster.toast(message.getString("message"));

                                        }else{
                                            Log.d(TAG, res);
                                            Intent intent = new Intent(getActivity(),MainActivity.class);
                                            startActivity(intent);
                                        }
                                        canBeSend = false;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                }else{
                    Toaster.toast("Wypełnij wszystkie dane!");
                }

            }
        });
        return rootView;
    }
}
