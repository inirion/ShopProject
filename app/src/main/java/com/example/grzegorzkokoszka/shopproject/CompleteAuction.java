package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xdroid.toaster.Toaster;

import static android.content.ContentValues.TAG;

/**
 * Created by Grzegorz Kokoszka on 2017-02-27.
 */

public class CompleteAuction extends Activity {
    private boolean eligible = false;
    private boolean callEnd = false;
    private String status= "";
    private String AuctionId = "";

    public CompleteAuction(String AuctionId) {
        this.AuctionId = AuctionId;
    }

    public boolean getEligible(){return eligible;}
    public boolean getCallEnd(){return callEnd;}

    public void  isEligible(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://projekt-aukcje.herokuapp.com/api/auctions/"+AuctionId+"/buyerChoices")
                .addHeader("authorization","Bearer " + User.getJwtToken())
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG, "onResponseFromClass: " + res);
                try {
                    JSONObject message = new JSONObject(res);
                    if(message.has("message")){
                        callEnd = true;
                        eligible = false;

                    }else {
                        eligible = true;
                        callEnd = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
