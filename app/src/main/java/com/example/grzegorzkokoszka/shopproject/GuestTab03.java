package com.example.grzegorzkokoszka.shopproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

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
    private static Button BirthDay;
    private static View rootView;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.register_form,container,false);
        //todo page logic
        WindowManager wm = (WindowManager) rootView.getContext().getSystemService(rootView.getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        rootView.findViewById(R.id.CancelBtn).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.RegisterBtn).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.RegisterBtnGuest).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.CancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "Click", Toast.LENGTH_LONG).show();
            }
        });
        rootView.findViewById(R.id.BirthDateDialogBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(fragmentManager, "datePicker");
            }
        });
        return rootView;
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            BirthDay = (Button) rootView.findViewById(R.id.BirthDateDialogBtn);
            Log.d(TAG, "Year : " + year);
            Log.d(TAG, "month : " + month);
            Log.d(TAG, "day : " + day);
            BirthDay.setText(Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year));
        }

    }


    public void showDatePickerDialog(View v) {


    }
}
