package com.e.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.e.driver.R;
import com.e.driver.utils.SamsPrefs;

public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView mobile,emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
     /*   fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header=navigationView.getHeaderView(0);
        mobile=header.findViewById(R.id.navTextViewMobile);
        emailid=header.findViewById(R.id.nav_textViewEmail);

        mobile.setText(SamsPrefs.getString(getApplicationContext(),"mobileNumber"));
        emailid.setText(SamsPrefs.getString(getApplicationContext(),"emailId"));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_mybooking,
                R.id.nav_newbooking,
                R.id.nav_prime_member,
                R.id.nav_rateus, R.id.nav_share, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId()==R.id.nav_share){
                    Toast.makeText(DashboardActivity.this, "Share", Toast.LENGTH_SHORT).show();
                }
                if (destination.getId()==R.id.nav_mybooking){
                    Toast.makeText(DashboardActivity.this, "My Booking", Toast.LENGTH_SHORT).show();
                }

                if (destination.getId()==R.id.nav_newbooking){
                Intent intent = new Intent(DashboardActivity.this,BookRequestActivity.class);
                startActivity(intent);
                }
                if (destination.getId()==R.id.nav_rateus){

                    Toast.makeText(DashboardActivity.this, "Rate us", Toast.LENGTH_SHORT).show();
                }

                if (destination.getId()==R.id.nav_prime_member){

                    Toast.makeText(DashboardActivity.this, "Prime Member", Toast.LENGTH_SHORT).show();
                }

                if (destination.getId()==R.id.nav_help){

                    Toast.makeText(DashboardActivity.this, "Help", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
