package com.example.assignment_nodejs.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.adapters.Adapter_Registration;
import com.example.assignment_nodejs.models.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRegistration extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Student_Api studentApi;
    private Adapter_Registration adapterRegistration;
    public FragmentRegistration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        initView();
        return view;
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.fRegistration_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),layoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);

        Call<List<Subject>> call = studentApi.getRegistration();
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if (response.code() == 200){
                    adapterRegistration = new Adapter_Registration(getActivity(),response.body());
                    recyclerView.setAdapter(adapterRegistration);
                }
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                clg(t.getMessage());
            }
        });
    }

    private void clg(String s){
        Log.d("log",s);
    }
}
