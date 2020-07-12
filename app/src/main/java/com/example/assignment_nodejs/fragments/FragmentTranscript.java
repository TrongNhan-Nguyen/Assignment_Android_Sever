package com.example.assignment_nodejs.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.activities.MainActivity;
import com.example.assignment_nodejs.adapters.Adapter_Transcript;
import com.example.assignment_nodejs.models.Subject;
import com.example.assignment_nodejs.models.Transcript;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTranscript extends Fragment {
    private RecyclerView recyclerView;
    private Adapter_Transcript adapterTranscript;
    private View view;
    private Student_Api studentApi;
    public FragmentTranscript() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transcript, container, false);
        initView();
        return view;
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.fTranscript_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),layoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);

        Call<String> call = studentApi.getTranscript(MainActivity.STUDENT.get_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200){
                    try {
                        List<Transcript> transcriptList = new ArrayList<>();
                        JSONArray array = new JSONArray(response.body());
                        for (int i = 0; i<array.length(); i++){
                            JSONObject transcriptJson = array.getJSONObject(i);
                            String nameSubject = (transcriptJson.getJSONObject("subject").getString("name"));
                            String subjectID = (transcriptJson.getJSONObject("subject").getString("subjectID"));
                            String status = transcriptJson.getString("status");
                            float scores = Float.parseFloat(transcriptJson.getString("scores"));
                            Subject subject = new Subject(nameSubject,subjectID);
                            Transcript transcript = new Transcript(status,scores,subject);
                            transcriptList.add(transcript);
                        }
                        adapterTranscript = new Adapter_Transcript(getActivity(),transcriptList);
                        recyclerView.setAdapter(adapterTranscript);
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
    private void clg(String s){
        Log.d("log",s);
    }
}
