package com.leite.gabriel.timeattendance;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditTimeFragment extends Fragment {
    private static final String ARG_TIME_CHECK_ID = "time-check-id";

    private EditTimeViewModel mViewModel;
    private TimePicker timePicker;
    private TextView txtDate;
    private Button btnDelete;
    private Button btnSave;

    private TimeCheck timeCheck;

    public static EditTimeFragment newInstance(TimeCheck timeCheck) {

        EditTimeFragment fragment = new EditTimeFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TIME_CHECK_ID, timeCheck.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            long id = getArguments().getLong(ARG_TIME_CHECK_ID);
            timeCheck = Database.TimeCheck().load(id);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_time_fragment, container, false);

        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        txtDate = view.findViewById(R.id.txtDate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnSave = view.findViewById(R.id.btnSave);

        txtDate.setText(timeCheck.getDate());

        timePicker.setHour(Integer.parseInt(timeCheck.getTime().substring(0,2)));
        timePicker.setMinute(Integer.parseInt(timeCheck.getTime().substring(3,5)));
        timePicker.setIs24HourView(true);

        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                SaveChanges(timePicker.getHour(), timePicker.getMinute());
            }
        });

        btnDelete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                DeleteTimeCheck();
            }
        });

        return view;
    }

    private void DeleteTimeCheck() {
        Database.TimeCheck().delete(timeCheck);
        getFragmentManager().popBackStack();
    }

    private void SaveChanges(int hour, int minute) {
        timeCheck.setTime(String.format("%02d:%02d", hour, minute));
        Database.TimeCheck().update(timeCheck);
        getFragmentManager().popBackStack();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditTimeViewModel.class);
        // TODO: Use the ViewModel
    }

}
