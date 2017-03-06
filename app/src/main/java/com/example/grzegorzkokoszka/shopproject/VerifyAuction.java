package com.example.grzegorzkokoszka.shopproject;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Grzegorz Kokoszka on 2017-02-26.
 */

public class VerifyAuction extends AppCompatActivity {
    private static View rootView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String url = "/api/auctions/:id/boughtChoices";
    private final int deliveryid[] = {R.id.deliveryVerify1,R.id.deliveryVerify2,R.id.deliveryVerify3};
    private final int paymentid[] = {R.id.paymentVerify1,R.id.paymentVerify2};
    private String delivery = "", payment="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.won_auction_form);
        rootView = getWindow().getDecorView().getRootView();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Intent intent = getIntent();

        final String auctionId = intent.getStringExtra("id");
        ((RadioButton)rootView.findViewById(deliveryid[0])).setChecked(true);
        ((RadioButton)rootView.findViewById(paymentid[0])).setChecked(true);
        for(int i = 0 ; i < 3 ; i ++){
            ((RadioButton)rootView.findViewById(deliveryid[i])).setTextSize(12);
            ((RadioButton)rootView.findViewById(deliveryid[i])).setWidth(width/2);
            ((RadioButton)rootView.findViewById(deliveryid[i])).setText(User.getDeliveries().get(i).getName());

        }

        for(int i = 0 ; i < 2 ; i ++){
            ((RadioButton)rootView.findViewById(paymentid[i])).setTextSize(12);
            ((RadioButton)rootView.findViewById(paymentid[i])).setWidth(width/2);
            ((RadioButton)rootView.findViewById(paymentid[i])).setText(User.getPayments().get(i).getName());
        }
        rootView.findViewById(R.id.CancelBtnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyAuction.this,LayoutActivity.class);
                User.setCurrentPageFragemnt(2);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.AddBtnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i < 2 ; i ++){
                    if(((RadioButton)rootView.findViewById(paymentid[i])).isChecked())
                        payment = ((RadioButton)rootView.findViewById(paymentid[i])).getText().toString();
                }
                for(int i = 0 ; i < 3 ; i ++){
                    if(((RadioButton)rootView.findViewById(deliveryid[i])).isChecked())
                        delivery = ((RadioButton)rootView.findViewById(deliveryid[i])).getText().toString();
                }
                payment = "["+User.getPaymentId(payment)+"]";
                delivery = "["+User.getDeliveryId(delivery)+"]";
                Log.d(TAG, "onClick: " + payment + " " + delivery);
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("deliveryOption",delivery)
                        .add("paymentOption",payment)
                        .build();
                Request request = new Request.Builder()
                        .url("http://projekt-aukcje.herokuapp.com/api/auctions/"+auctionId+"/boughtChoices")
                        .addHeader("authorization","Bearer " + User.getJwtToken())
                        .post(formBody)
                        .build();

                client.newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: fafafafafafafaf");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.d(TAG, "onResponse: " + response.body().string());
                                Intent intent = new Intent(VerifyAuction.this,LayoutActivity.class);
                                User.setCurrentPageFragemnt(2);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}
