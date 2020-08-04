package com.example.assignment_nodejs.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FragmentInbox extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private Adapter_Inbox adapter;
    private Student_Api api;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inbox, container, false);
        api = Retrofit_Manager.retrofit.create(Student_Api.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.fInbox_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        MenuItem inboxMenu = MainActivity.navigationView.getMenu().findItem(R.id.drawer_inbox);
        TextView number = (TextView) inboxMenu.getActionView();
        number.setGravity(Gravity.CENTER_VERTICAL);
        number.setTypeface(null, Typeface.BOLD);
        number.setTextColor(Color.RED);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);

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
                adapter = new Adapter_Inbox(getActivity(), inboxList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Inbox>> call, Throwable t) {
                clg(t.getMessage());
            }
        });



        return view;
    }

    private void clg(String s) {
        Log.d("log", s);
    }

}