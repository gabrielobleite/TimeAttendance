package com.leite.gabriel.timeattendance;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_DATE_SEARCH = "date-search";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String sDate = "";
    private OnListFragmentInteractionListener mListener;

    public ReportFragment() {
    }

    public static ReportFragment newInstance(int columnCount, String sDate) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_DATE_SEARCH, sDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            sDate = getArguments().getString(ARG_DATE_SEARCH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        List lst = Database.TimeCheck().loadByDate(sDate);

        recyclerView.setAdapter(new ReportRecyclerViewAdapter(lst, mListener));

        TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText(sDate);

        TextView txtFooter = view.findViewById(R.id.txtFooter);
        int timeMinutes = getTimeSpent(lst);
        String footer = String.format("Hours of work to day: %02d:%02d", timeMinutes / 60, timeMinutes % 60);
        if(lst.size()%2==1)
            footer += "\nThe day have a pending time, without a closure";
        txtFooter.setText(footer);
        return view;
    }

    private int getTimeSpent(List<TimeCheck> lst) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        int timeSpent = 0;
        for (int i = 0; i < lst.size() - 1; i = i + 2) {
            try {
                Date startDate = timeFormat.parse(lst.get(i).getTime());
                Date endDate = timeFormat.parse(lst.get(i+1).getTime());

                timeSpent += endDate.getTime() - startDate.getTime();
            } catch (ParseException e) {
            }
        }
        return timeSpent / 1000 / 60;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(TimeCheck item);
    }
}
