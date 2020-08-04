package com.example.assignment_nodejs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.fragments.FragmentInbox;
import com.example.assignment_nodejs.fragments.FragmentNews;
import com.example.assignment_nodejs.fragments.FragmentProfile;
import com.example.assignment_nodejs.fragments.FragmentRegistration;
import com.example.assignment_nodejs.fragments.FragmentSchedule;
import com.example.assignment_nodejs.fragments.FragmentTranscript;
import com.example.assignment_nodejs.models.Inbox;
import com.example.assignment_nodejs.models.Student;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    public static NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private Student student = LoginActivity.STUDENT;
    private Student_Api api;
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runtimePermission();
        getSupportActionBar().setTitle("Student Management System");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        api = Retrofit_Manager.retrofit.create(Student_Api.class);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        navigationView = (NavigationView) findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        replaceFragment(new FragmentNews());
        showInfo();
        createNotificationChannel();
        builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notification from Admin")
                .setContentText("Check your inbox for details")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager = NotificationManagerCompat.from(this);
        setBadgesInbox();


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
            case R.id.drawer_inbox:
                replaceFragment(new FragmentInbox());
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
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notify";
            String description = "Notify from Admin";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
    private void setBadgesInbox(){
        MenuItem inboxMenu = MainActivity.navigationView.getMenu().findItem(R.id.drawer_inbox);
        TextView number = (TextView) inboxMenu.getActionView();
        number.setGravity(Gravity.CENTER_VERTICAL);
        number.setTypeface(null, Typeface.BOLD);
        number.setTextColor(Color.RED);
        Call<List<Inbox>> call = api.getInboxList(LoginActivity.STUDENT.get_id());
        call.enqueue(new Callback<List<Inbox>>() {
            @Override
            public void onResponse(Call<List<Inbox>> call, Response<List<Inbox>> response) {
                int count = 0;
                List<Inbox> inboxList = response.body();
                for (Inbox inbox : inboxList) {
                    if (!inbox.isActive()) {
                        count++;
                    }
                }

                if (count>0){
                    notificationManager.notify(1, builder.build());

                }
                number.setText(count + "");
            }

            @Override
            public void onFailure(Call<List<Inbox>> call, Throwable t) {
                clg(t.getMessage());
            }
        });
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
