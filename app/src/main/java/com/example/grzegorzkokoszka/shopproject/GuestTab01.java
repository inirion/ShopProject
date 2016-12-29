package com.example.grzegorzkokoszka.shopproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class GuestTab01 extends Fragment {
    //Singleton
    public static GuestTab01 newInstance(){
        GuestTab01 fragment = new GuestTab01();
        return fragment;
    }

    public GuestTab01(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guest01,container,false);
        //todo page logic

        return rootView;
    }
}
