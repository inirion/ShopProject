package com.example.grzegorzkokoszka.shopproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

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

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private SearchView search;
    private Button prevBtn;
    private Button nextBtn;
    private Button priceSortBtn;
    private static final int DispalyLimit = 2;
    private static int ProductsQuantity = 3;
    private static int value = 0;
    private boolean querySelected = false;
    private static boolean sortAsc = false;
    private static boolean sortDesc = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_guest02, container, false);

        search = (SearchView) rootView.findViewById(R.id.Search);
        prevBtn = (Button) rootView.findViewById(R.id.prevBtn);
        nextBtn = (Button) rootView.findViewById(R.id.NextBtn);
        priceSortBtn = (Button) rootView.findViewById(R.id.SortByPriceBtn);

        priceSortBtn.setOnClickListener(new View.OnClickListener() {
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
        prevBtn.setOnClickListener(new View.OnClickListener() {
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
        nextBtn.setOnClickListener(new View.OnClickListener() {
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

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()) {
                    querySelected = true;
                }else querySelected = false;
                Log.d(TAG,"Query Selected " + querySelected);
                Log.d(TAG,"Value : " + value);
                DisplayData(rootView,getActivity());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return rootView;
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
        for(int i = DispalyLimit*pageNum ; i < DispalyLimit+(pageNum*DispalyLimit) && i < titles.length; i++){
            Product product = new Product(titles[i],descriptions[i],ImageId,prices[i]);
            products.add(product);
        }
        return products;
    }
}
