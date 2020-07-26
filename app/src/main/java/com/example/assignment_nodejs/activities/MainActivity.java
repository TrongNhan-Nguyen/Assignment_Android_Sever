package com.example.assignment_nodejs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.fragments.FragmentNews;
import com.example.assignment_nodejs.fragments.FragmentProfile;
import com.example.assignment_nodejs.fragments.FragmentRegistration;
import com.example.assignment_nodejs.fragments.FragmentSchedule;
import com.example.assignment_nodejs.fragments.FragmentTranscript;
import com.example.assignment_nodejs.models.Student;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private  Student student = LoginActivity.STUDENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runtimePermission();
        getSupportActionBar().setTitle("Student Management System");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        navigationView = (NavigationView) findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        replaceFragment(new FragmentNews());
        showInfo();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_news:
                replaceFragment(new FragmentNews());
                break;
            case R.id.drawer_registration:
                replaceFragment(new FragmentRegistration());
                break;
            case R.id.drawer_schedule:
                replaceFragment(new FragmentSchedule());
                break;
            case R.id.drawer_transcript:
                replaceFragment(new FragmentTranscript());
                break;
            case R.id.drawer_profile:
                replaceFragment(new FragmentProfile());
                break;
            case R.id.drawer_exit:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }
    private void showInfo(){
        if (student != null){
            String url = "http://10.0.2.2:3000/uploads/avatar/";
            View headerDrawer = navigationView.getHeaderView(0);
            ImageView img = (ImageView) headerDrawer.findViewById(R.id.header_img);
            TextView tvName = headerDrawer.findViewById(R.id.header_tvName);
            TextView tvEmail = headerDrawer.findViewById(R.id.header_tvEmail);
            tvName.setText(student.getName());
            tvEmail.setText(student.getEmail());
            Picasso.get().load(url+student.getImg()).into(img);
        }
    }
    private void runtimePermission(){
        Dexter.withContext(this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()){
                    clg("All permissions granted");
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    private void clg(String s){
        Log.d("log",s);
    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
