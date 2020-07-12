package com.example.assignment_nodejs.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.adapters.Adapter_News;
import com.example.assignment_nodejs.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private List<News> newsList;
    private Adapter_News adapterNews;
    private Student_Api studentApi;
    public FragmentNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return view;
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.fNews_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        Call<List<News>> call = studentApi.getNewsList();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.code()==200){
                    adapterNews = new Adapter_News(getActivity(),response.body());
                    recyclerView.setAdapter(adapterNews);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });

    }
    private void clg (String s){
        Log.d("log",s);
    }
}
