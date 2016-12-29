package com.example.grzegorzkokoszka.shopproject;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import  android.app.FragmentManager;




/**
 * Created by Grzegorz Kokoszka on 2016-12-26.
 */

public class RegisterForm extends Activity {
    private static Button BirthDay;
    private static View rootView;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);
        rootView = getWindow().getDecorView().getRootView();
    }



    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        // pgrm mark ---- ---- ----- ---- ---- ----- ---- ---- -----  ---- ---- -----

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
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

        FragmentManager fragmentManager = getFragmentManager();

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(fragmentManager, "datePicker");
    }
}
