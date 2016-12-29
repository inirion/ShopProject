package com.example.grzegorzkokoszka.shopproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guest03,container,false);
        //todo page logic

        return rootView;
    }
}
