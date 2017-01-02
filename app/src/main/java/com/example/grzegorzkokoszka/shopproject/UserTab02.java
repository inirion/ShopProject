package com.example.grzegorzkokoszka.shopproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class UserTab02 extends Fragment {
    //Singleton
    private User user;
    public static UserTab02 newInstance(){
        UserTab02 fragment = new UserTab02();
        return fragment;
    }

    public UserTab02(){

    }
    private TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user02,container,false);
        //todo page logic
        text = (TextView) rootView.findViewById(R.id.Imie);
        text.setText(User.getPerson().getName());



        return rootView;
    }
}
