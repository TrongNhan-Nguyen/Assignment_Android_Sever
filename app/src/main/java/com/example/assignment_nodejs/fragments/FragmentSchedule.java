package com.example.assignment_nodejs.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.activities.MainActivity;
import com.example.assignment_nodejs.adapters.Adapter_Schedule;
import com.example.assignment_nodejs.models.Schedule;
import com.example.assignment_nodejs.models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSchedule extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Student_Api studentApi;
    private Adapter_Schedule adapterSchedule;
    public FragmentSchedule() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initView();
        return view;
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.fSchedule_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),layoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        Call<String> call = studentApi.getSchedule(MainActivity.STUDENT.get_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200){
                    try {
                        List<Schedule> scheduleList = new ArrayList<>();
                        JSONArray array = new JSONArray(response.body());
                        int size = array.length();
                        for (int i = 0; i<size; i++){
                            JSONObject scheduleJson = array.getJSONObject(i);
                            String subjectID = scheduleJson.getString("subjectID");
                            String subjectName = scheduleJson.getString("name");
                            String shift = scheduleJson.getString("shift");
                            String date = scheduleJson.getString("stringDate");
                            Schedule schedule = new Schedule(subjectID,subjectName,shift,date);
                            scheduleList.add(schedule);
                        }
                        adapterSchedule = new Adapter_Schedule(getActivity(),scheduleList);
                        recyclerView.setAdapter(adapterSchedule);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                clg(t.getMessage());
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.menu_search_schedule);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Enter a subject's name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterSchedule!=null){
                    adapterSchedule.getFilter().filter(newText);

                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void clg(String s){
        Log.d("log",s);
    }
}
