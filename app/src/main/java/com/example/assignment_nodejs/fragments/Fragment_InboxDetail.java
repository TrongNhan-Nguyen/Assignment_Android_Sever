package com.example.assignment_nodejs.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.activities.LoginActivity;
import com.example.assignment_nodejs.activities.MainActivity;
import com.example.assignment_nodejs.adapters.Adapter_Inbox;
import com.example.assignment_nodejs.models.Inbox;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_InboxDetail extends Fragment {
    private View view;
    private TextView tvTitle, tvContent, tvPubDate;
    private Student_Api api;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__inbox_detail, container, false);
        tvTitle = (TextView) view.findViewById(R.id.fInboxDetail_tvTitle);
        tvContent = (TextView) view.findViewById(R.id.fInboxDetail_tvContent);
        tvPubDate = (TextView) view.findViewById(R.id.fInboxDetail_tvPubDate);
        api = Retrofit_Manager.retrofit.create(Student_Api.class);
        Bundle bundle = getArguments();
        Inbox inbox = (Inbox) bundle.getSerializable("Inbox");
        Call<String> call = api.getInbox(inbox.get_id(), LoginActivity.STUDENT.get_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                clg(response.body());
                setBadgesInbox();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                clg(t.getMessage());
            }
        });
        tvTitle.setText(inbox.getTitle());
        tvContent.setText(inbox.getContent());
        tvPubDate.setText(inbox.getPubDate());
        return view;
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

}