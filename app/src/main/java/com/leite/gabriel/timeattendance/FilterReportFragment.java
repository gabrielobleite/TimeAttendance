package com.leite.gabriel.timeattendance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

public class FilterReportFragment extends Fragment {
    DatePicker datePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_report, container, false);

        datePicker = view.findViewById(R.id.datePicker);

        Button btnFilter = view.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String tag = "report";

                String sDate = String.format("%02d/%02d/%04d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());

                // Transition between fragments
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
                ft.replace(R.id.container, ReportFragment.newInstance(2, sDate), tag);
                ft.addToBackStack(tag);
                ft.commit();
            }
        });

        return view;
    }
}