package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xdroid.toaster.Toaster;

public class ProductDetails extends AppCompatActivity {
    private static final String TAG = ProductDetails.class.getSimpleName();
    private static View rootView;
    private String payments ="";
    private String auctionFinished = "";
    private String deliveries ="";
    private boolean callEnd = false;
    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rootView = getWindow().getDecorView().getRootView();
        setContentView(R.layout.product_detail);
        WindowManager wm = (WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        rootView.findViewById(R.id.ScrollLayoutDetails).getLayoutParams().height = height;
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        final String auction = intent.getStringExtra("auction");
        String time = intent.getStringExtra("time");
        String disc = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        final String highestBitterId = intent.getStringExtra("highestBitId");
        final String authorId = intent.getStringExtra("author");
        //String picture = String.valueOf(intent.getStringExtra("image"));


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://projekt-aukcje.herokuapp.com/api/auctions/"+auction)
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
                            JSONObject resultObject = new JSONObject(res);
                            auctionFinished = resultObject.getString("finished");
                            Log.d(TAG, "onResponse: " + res);
                            JSONArray jsondel = resultObject.getJSONArray("possiblePayments");
                            if(jsondel != null) {
                                for (int i = 0; i < jsondel.length(); i++) {
                                    JSONObject e = jsondel.getJSONObject(i);
                                    JSONObject ee = e.getJSONObject("PaymentOption");
                                    Log.d(TAG, "Typ: " + ee.getString("name"));
                                    payments+=ee.getString("name") + ", ";
                                }
                            }
                            JSONArray jsonpay = resultObject.getJSONArray("possibleDeliveries");
                            if(jsonpay != null) {
                                for (int i = 0; i < jsonpay.length(); i++) {
                                    JSONObject e = jsonpay.getJSONObject(i);
                                    JSONObject ee = e.getJSONObject("DeliveryOption");
                                    Log.d(TAG, "Typ: " + ee.getString("name"));
                                    deliveries+=ee.getString("name") + ", ";
                                }
                            }
                            callEnd = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
        while(true) {
            if (callEnd) {
                ((TextView) rootView.findViewById(R.id.Czas)).setText("Czas do końca : " + time);
                ((TextView) rootView.findViewById(R.id.Przelewy)).setText("Dostępne rodzaje opłaty :");
                ((TextView) rootView.findViewById(R.id.Dostawy)).setText("Dostępne rodzaje dostawy :");
                ((TextView) rootView.findViewById(R.id.TitleDetails)).setText(name);
                ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setClickable(true);
                ((EditText) rootView.findViewById(R.id.AuctionDetailsBid)).setVisibility(View.VISIBLE);

                deliveries = deliveries.substring(0, deliveries.length() - 2);
                payments = payments.substring(0, payments.length() - 2);
                ((TextView) rootView.findViewById(R.id.RodzajeOplat)).setText(payments);
                ((TextView) rootView.findViewById(R.id.RodzajeDostaw)).setText(deliveries);
                if (!User.isLogged()) {
                    rootView.findViewById(R.id.AuctionDetailsBid).setVisibility(View.INVISIBLE);
                    ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Zaloguj");
                }
                if (User.isLogged()) {
                    if (authorId.equals(User.getId())) {
                        if (auctionFinished.equals("true")) {
                            ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Wyślij");
                            ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setClickable(true);
                        } else if(auctionFinished.equals("false")){
                            ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Twoja aukcja");
                            ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setClickable(false);
                        }
                        ((EditText) rootView.findViewById(R.id.AuctionDetailsBid)).setVisibility(View.INVISIBLE);
                    } else {
                        rootView.findViewById(R.id.AuctionDetailsBid).setVisibility(View.VISIBLE);
                        ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Licytuj");
                    }
                    if (highestBitterId.equals(User.getId())) {
                        if (auctionFinished.equals("true") && !authorId.equals(User.getId())) {
                            ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Dostawa");
                        } else if(auctionFinished.equals("false")){
                            if (authorId.equals(User.getId()))
                                ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Twoja aukcja");
                            else
                                ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setText("Twój bid");
                        }
                        ((Button) rootView.findViewById(R.id.ProductDetailBuyBtn)).setClickable(false);
                        ((EditText) rootView.findViewById(R.id.AuctionDetailsBid)).setVisibility(View.INVISIBLE);
                    }
                }
                Log.d(TAG, "run: " + auctionFinished);




                Log.d(TAG, "Details" + User.getId() + " " + authorId);
                callEnd = false;
                break;
            }
        }
        ((Button)rootView.findViewById(R.id.ProductDetailBuyBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)rootView.findViewById(R.id.ProductDetailBuyBtn)).getText().equals("Zaloguj")){
                    User.reset();
                    Intent backIntent = new Intent(ProductDetails.this,MainActivity.class);
                    startActivity(backIntent);
                }else if(((Button)rootView.findViewById(R.id.ProductDetailBuyBtn)).getText().equals("Licytuj") &&
                        !((EditText)rootView.findViewById(R.id.AuctionDetailsBid)).getText().toString().trim().isEmpty()){
                    OkHttpClient client = new OkHttpClient();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date startDate = new Date();
                    User.setRefresh(true);
                    String startDateText = sdf.format(startDate);
                    Log.d(TAG, "onClick: Value " + ((EditText)rootView.findViewById(R.id.AuctionDetailsBid)).getText().toString() + " Auction ID " + auction);
                    FormBody formBody = new FormBody.Builder()
                            .add("value",((EditText)rootView.findViewById(R.id.AuctionDetailsBid)).getText().toString())
                            .add("authorId",User.getId())
                            .add("createdAt", startDateText)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://projekt-aukcje.herokuapp.com/api/auctions/"+auction+"/bids")
                            .addHeader("authorization","Bearer " + User.getJwtToken())
                            .post(formBody)
                            .build();
                    client.newCall(request)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String res = response.body().string();
                                        Log.d(TAG, "Sialalalalsdlasldalsdlasldlasldlasldlasd: " + res + " My bids : " +User.isCheckingMyBids() + " MyAuction : " +User.isCheckingMyAuctions() );
                                    JSONObject message = null;
                                    try {
                                        message = new JSONObject(res);
                                        if(message.has("message")){
                                            Toaster.toast(message.getString("message"));

                                        }else{
                                            Log.d(TAG, res);
                                            Intent backIntent = null;
                                            if(User.isCheckingMyBids()){
                                                Log.d(TAG, "onClick: KURWA");
                                                backIntent = new Intent(ProductDetails.this,UserBids.class);
                                                User.setRefresh(true);
                                                startActivity(backIntent);
                                            }
                                            if(User.isCheckingMyAuctions()){
                                                Log.d(TAG, "onClick: DZIALA");
                                                backIntent = new Intent(ProductDetails.this,UserProducts.class);
                                                User.setRefresh(true);
                                                startActivity(backIntent);
                                            }if(!User.isCheckingMyBids() && !User.isCheckingMyAuctions() ){
                                                Log.d(TAG, "onResponse: Layoit");
                                                backIntent = new Intent(ProductDetails.this,LayoutActivity.class);
                                                User.setRefresh(true);
                                                startActivity(backIntent);
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }



                                }
                            });


                } else if (((Button)rootView.findViewById(R.id.ProductDetailBuyBtn)).getText().equals("Dostawa")) {
                    Intent backIntent = null;
                    backIntent = new Intent(ProductDetails.this,VerifyAuction.class);
                    backIntent.putExtra("id",auction);
                    startActivity(backIntent);
                }
                else if (((Button)rootView.findViewById(R.id.ProductDetailBuyBtn)).getText().equals("Wyślij")) {
                    CompleteAuction completaion = new CompleteAuction(auction);
                    completaion.isEligible();
                    while(true){
                        if(completaion.getCallEnd()){
                            Log.d(TAG, "onClick: " + completaion.getEligible());
                            break;
                        }
                    }

                }
            }
        });
        //Log.d(TAG, "Picture : " + picture);
        TextView discTV = (TextView) rootView.findViewById(R.id.ProductDetailTextViewDisc);
        discTV.setText(disc);
        TextView priceTV = (TextView) rootView.findViewById(R.id.ProductDetailTextViewPrice);
        priceTV.setText(price);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.ProductDetailimageView);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        //imageView.setImageResource(String.(picture));

        rootView.findViewById(R.id.ProductDetailBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = null;
                if(User.isCheckingMyBids()){
                    backIntent = new Intent(ProductDetails.this,UserBids.class);
                    User.setRefresh(true);
                    startActivity(backIntent);
                }
                if(User.isCheckingMyAuctions()){
                    backIntent = new Intent(ProductDetails.this,UserProducts.class);
                    User.setRefresh(true);
                    startActivity(backIntent);
                }if(!User.isCheckingMyBids() && !User.isCheckingMyAuctions() ){
                    backIntent = new Intent(ProductDetails.this,LayoutActivity.class);
                    User.setRefresh(true);
                    startActivity(backIntent);
                }


            }
        });

    }

}
