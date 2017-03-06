package com.example.grzegorzkokoszka.shopproject;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;

import static android.content.ContentValues.TAG;

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
    public void callParentMethod(){
        getActivity().onBackPressed();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callParentMethod();
        final View rootView = inflater.inflate(R.layout.advanced_search_fragment,container,false);
        //todo page logic

        WindowManager wm = (WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        final int categoryid[] = {R.id.category1Search,R.id.category2Search,R.id.category3Search,R.id.category4Search,R.id.category5Search,R.id.category6Search
                ,R.id.category7Search,R.id.category8Search,R.id.category9Search};
        for(int i = 0 ; i < 9 ; i ++){
            ((CheckBox)rootView.findViewById(categoryid[i])).setTextSize(12);
            ((CheckBox)rootView.findViewById(categoryid[i])).setHeight(height/10);
            ((CheckBox)rootView.findViewById(categoryid[i])).setText(User.getCategories().get(i).getName());
        }

        rootView.findViewById(categoryid[0]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[0]),0);
            }
        });
        rootView.findViewById(categoryid[1]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[1]),1);
            }
        });
        rootView.findViewById(categoryid[2]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[2]),2);
            }
        });
        rootView.findViewById(categoryid[3]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[3]),3);
            }
        });
        rootView.findViewById(categoryid[4]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[4]),4);
            }
        });
        rootView.findViewById(categoryid[5]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[5]),5);
            }
        });
        rootView.findViewById(categoryid[6]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[6]),6);
            }
        });
        rootView.findViewById(categoryid[7]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[7]),7);
            }
        });
        rootView.findViewById(categoryid[8]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxUpdate((CheckBox)rootView.findViewById(categoryid[8]),8);
            }
        });

        return rootView;
    }

    private void checkBoxUpdate(CheckBox cb, int id){
        if( cb.isChecked()){
            User.addSelectedCategories(User.getCategories().get(id).getName());
        }else{
            User.deleteSelectedCategory(User.getCategories().get(id).getName());
        }
        if(User.getSelectedCategories().size() >0){
            for(int i = 0 ; i < User.getSelectedCategories().size() ; i++){
                Log.d(TAG, "onClick: " + User.getSelectedCategories().get(i));
            }
        }
    }
}
