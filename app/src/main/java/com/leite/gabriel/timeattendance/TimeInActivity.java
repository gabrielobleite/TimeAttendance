package com.leite.gabriel.timeattendance;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class TimeInActivity extends AppCompatActivity {
    private TextView txtDate;
    private TextView txtTime;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database.InstantiateDatabase(this);

        setContentView(R.layout.activity_time_in);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get TextViews
        txtTime = findViewById(R.id.txtTime);
        txtDate = findViewById(R.id.txtDate);

        Thread timestampUpdate = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdfData = new SimpleDateFormat(getString(R.string.timein_dateFormat));
                                SimpleDateFormat sdfTime = new SimpleDateFormat(getString(R.string.timein_timeFormat));
                                String dateString = sdfData.format(date);
                                String timeString = sdfTime.format(date);
                                txtDate.setText(dateString);
                                txtTime.setText(timeString);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        timestampUpdate.start();

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                SubmitTime(txtDate.getText().toString(), txtTime.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch(item.getItemId()) {
            case R.id.menu_appointment:
                break;
            case R.id.menu_report:
                intent = new Intent(this, ReportActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    void SubmitTime(String sDate, String sTime){
        TimeCheck timeCheck = new TimeCheck();
        timeCheck.setDate(sDate);
        timeCheck.setTime(sTime);
        Database.TimeCheck().insertAll(timeCheck);
        Toast.makeText(this, "New appointment was made to " + sDate + " at " + sTime, Toast.LENGTH_LONG).show();
    }
}
