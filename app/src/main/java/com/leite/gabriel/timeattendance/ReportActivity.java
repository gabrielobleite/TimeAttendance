package com.leite.gabriel.timeattendance;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity implements ReportFragment.OnListFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, new FilterReportFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch(item.getItemId()) {
            case R.id.menu_appointment:
                intent = new Intent(this, TimeInActivity.class);
                this.startActivity(intent);
                finish();
                break;
            case R.id.menu_report:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onListFragmentInteraction(TimeCheck item){
        String tag = "edit";

        // Transition between fragments
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        ft.replace(R.id.container, EditTimeFragment.newInstance(item), tag);
        ft.addToBackStack(tag);
        ft.commit();
    }
}
