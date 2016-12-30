package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.app.Fragment;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private static final int DispalyLimit = 10;
    private static int ProductsQuantity = 50;
    private static int value = 0;
    private boolean querySelected = false;
    private static boolean sortAsc = false;
    private static boolean sortDesc = false;
    private static View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_guest02, container, false);


        myOrientationEventListener
                = new OrientationEventListener(rootView.getContext(), SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if(querySelected){
                    DisplayData(rootView,getActivity());
                }
                Log.d(TAG, "Rotacja : " + orientation);
                Log.d(TAG, "Query Selected " + querySelected);
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
                if(querySelected == true && ProductsQuantity >0) {
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
                if (querySelected == true && ProductsQuantity > 0) {
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
                if(querySelected == true && value > 0) {
                    value--;
                    DisplayData(rootView,getActivity());
                }
                Log.d(TAG, "Value : " + value);
                Log.d(TAG, "Query Selected " + querySelected);
            }
        });
        rootView.findViewById(R.id.NextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(querySelected == true && value < ProductsQuantity/DispalyLimit) {
                    value++;
                    DisplayData(rootView,getActivity());
                }
                Log.d(TAG, "Value : " + value);
                Log.d(TAG, "Query Selected " + querySelected);
            }
        });


        ((SearchView)rootView.findViewById(R.id.Search)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()) {
                    querySelected = true;
                }else querySelected = false;
                Log.d(TAG,"Query Selected " + querySelected);
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
        for(int i = DispalyLimit*pageNum ; i < DispalyLimit+(pageNum*DispalyLimit); i++){
            int troll = rand.nextInt(2);
            Product product = new Product(titles[troll],descriptions[troll],ImageId,prices[troll]);
            products.add(product);
        }
        return products;
    }
}
