package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class GuestTab02 extends Fragment {
    //Singleton
    public static GuestTab02 newInstance() {
        GuestTab02 fragment = new GuestTab02();
        return fragment;
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    public GuestTab02() {

    }
    private OrientationEventListener myOrientationEventListener;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private static final int DispalyLimit = 5;
    private static int ProductsQuantity = 21;
    private static int value = 0;
    private static boolean sortAsc = false;
    private static boolean sortDesc = false;
    private static View rootView;
    private static DisplayMetrics displaymetrics = new DisplayMetrics();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_guest02, container, false);
        if(!User.isQuerySelected()) {
            Toast.makeText(rootView.getContext(), "Disable Buttons", Toast.LENGTH_LONG).show();
            rootView.findViewById(R.id.SortByPriceBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.SortByPriceImgBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.SortByTimeBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.SortByTimeImgBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
            ((Activity)rootView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        }else{
            DisplayData(rootView,getActivity());
            if(value == 0) {
                rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
            }
            if(value >= ProductsQuantity/DispalyLimit) {
                rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
            }
        }
        myOrientationEventListener
                = new OrientationEventListener(rootView.getContext(), SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if(User.isQuerySelected()){
                    //DisplayData(rootView,getActivity());
                }
                Log.d(TAG, "Rotacja : " + orientation);
                Log.d(TAG, "Query Selected " + User.isQuerySelected());
            }
        };
        if (myOrientationEventListener.canDetectOrientation()){
            Toast.makeText(rootView.getContext(), "Can DetectOrientation", Toast.LENGTH_LONG).show();
            myOrientationEventListener.enable();
        }
        else{
            Toast.makeText(rootView.getContext(), "Can't DetectOrientation", Toast.LENGTH_LONG).show();
        }
        rootView.findViewById(R.id.SortByPriceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.isQuerySelected() == true && ProductsQuantity >0) {
                    if (sortAsc) {
                        sortAsc = !sortAsc;
                        sortDesc = true;
                    } else {
                        sortAsc = !sortAsc;
                        sortDesc = false;
                    }
                    Log.d(TAG, "Sorted Asc: " + sortAsc);
                    Log.d(TAG, "Sorted Desc: " + sortDesc);
                    DisplayData(rootView,getActivity());
                }
            }
        });
        rootView.findViewById(R.id.SortByPriceImgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.isQuerySelected() == true && ProductsQuantity > 0) {
                    if (sortAsc) {
                        sortAsc = !sortAsc;
                        sortDesc = true;
                    } else {
                        sortAsc = !sortAsc;
                        sortDesc = false;
                    }
                    Log.d(TAG, "Sorted Asc: " + sortAsc);
                    Log.d(TAG, "Sorted Desc: " + sortDesc);
                    DisplayData(rootView, getActivity());
                }
            }
        });
        rootView.findViewById(R.id.prevBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.isQuerySelected() == true && value > 0) {
                    value--;
                    if(value == 0){
                        rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
                    }else{
                        rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
                    }
                    DisplayData(rootView,getActivity());
                }
                Log.d(TAG, "Value : " + value);
                Log.d(TAG, "Query Selected " + User.isQuerySelected());
            }
        });
        rootView.findViewById(R.id.NextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.isQuerySelected() == true && value < ProductsQuantity/DispalyLimit) {
                    value++;
                    if(value >= ProductsQuantity/DispalyLimit) {
                        rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
                    }else{
                        rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
                    }

                    DisplayData(rootView,getActivity());
                }
                Log.d(TAG, "Value : " + value);
                Log.d(TAG, "Query Selected " + User.isQuerySelected());
            }
        });
        ((SearchView)rootView.findViewById(R.id.Search)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()) {
                    User.setQuerySelected(true);
                    Toast.makeText(rootView.getContext(), "Enable Buttons", Toast.LENGTH_LONG).show();
                    if(rootView.findViewById(R.id.SortByPriceBtn).getVisibility() == View.INVISIBLE) {
                        rootView.findViewById(R.id.SortByPriceBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.SortByPriceImgBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.SortByTimeBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.SortByTimeImgBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.NextBtn).setVisibility(View.VISIBLE);
                    }
                }else{
                    User.setQuerySelected(false);
                    if(rootView.findViewById(R.id.SortByPriceBtn).getVisibility() == View.VISIBLE) {
                        rootView.findViewById(R.id.SortByPriceBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.SortByPriceImgBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.SortByTimeBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.SortByTimeImgBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
                        rootView.findViewById(R.id.NextBtn).setVisibility(View.INVISIBLE);
                    }
                }
                if(value == 0){
                    rootView.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
                }else{
                    rootView.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
                }
                Log.d(TAG,"Query Selected " + User.isQuerySelected());
                Log.d(TAG,"Value : " + value);
                DisplayData(rootView,getActivity());
                (rootView.findViewById(R.id.Search)).clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    public void DisplayData(View rootView, Activity activity){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ProductsView);
        adapter = new ProductAdapter(activity, getData(value));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    public static List<Product> getData(int pageNum) {
        List<Product> products = new ArrayList<>();
        String[] titles = {"Odkurzacz Trumpa", "But Trumpa", "Perulka trampa"};
        String[] descriptions = {"Wielofunkcyjny odkurzacz zapewniajacy czystosc",
                "But Trumpa wysmienite do biegania",
                "Perulka trampa, jedyna w swoim rodzaju"};
        float[] prices = {20,10,30};
        int ImageId = R.drawable.trump;
        Random rand = new Random();
        for(int i = DispalyLimit*pageNum ; i < DispalyLimit+(pageNum*DispalyLimit) && i < ProductsQuantity; i++){
            Product product = new Product(titles[i%3],descriptions[i%3],ImageId,prices[i%3]);
            products.add(product);
        }
        return products;
    }
}
