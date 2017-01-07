package com.example.grzegorzkokoszka.shopproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetails extends AppCompatActivity {
    private static final String TAG = ProductDetails.class.getSimpleName();
    private static View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getWindow().getDecorView().getRootView();
        Intent intent = getIntent();
        setContentView(R.layout.product_detail);
        String name = intent.getStringExtra("name");
        String disc = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String picture = intent.getStringExtra("image");
        Log.d(TAG, "Picture : " + picture);
        TextView discTV = (TextView) rootView.findViewById(R.id.ProductDetailTextViewDisc);
        discTV.setText(disc);
        TextView priceTV = (TextView) rootView.findViewById(R.id.ProductDetailTextViewPrice);
        priceTV.setText(price);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.ProductDetailimageView);
        //imageView.setImageResource(String.(picture));

        rootView.findViewById(R.id.ProductDetailBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(ProductDetails.this,LayoutActivity.class);
                startActivity(backIntent);
            }
        });

    }

}
