package com.example.grzegorzkokoszka.shopproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class UserTab03 extends Fragment {
    //Singleton
    public static UserTab03 newInstance(){
        UserTab03 fragment = new UserTab03();
        return fragment;
    }

    public UserTab03(){

    }
    public void callParentMethod(){
        getActivity().onBackPressed();
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callParentMethod();
        final View rootView = inflater.inflate(R.layout.user_panel,container,false);

        rootView.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),userEditData.class);
                startActivity(intent);
           }
         });
        rootView.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),userAddProduct.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserProducts.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserBids.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.reset();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
