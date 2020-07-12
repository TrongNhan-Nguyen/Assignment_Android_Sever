package com.example.assignment_nodejs.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.activities.MainActivity;
import com.example.assignment_nodejs.models.Subject;
import com.example.assignment_nodejs.models.Transcript;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_Registration extends RecyclerView.Adapter<Adapter_Registration.ViewHolder> {
    private Context context;
    private List<Subject> subjectList;
    private int index;

    public Adapter_Registration(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_registration,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.tvStt.setText(String.valueOf(position+1));
        holder.tvID.setText(subject.getSubjectID());
        holder.tvName.setText(subject.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                holder.dialogAdd();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvID, tvName, tvStt, tvAdd;
        private Student_Api studentApi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
            tvID = (TextView) itemView.findViewById(R.id.rawRegistration_tvID);
            tvName = (TextView) itemView.findViewById(R.id.rawRegistration_tvName);
            tvStt = (TextView) itemView.findViewById(R.id.rawRegistration_tvStt);
            tvAdd = (TextView) itemView.findViewById(R.id.rawRegistration_tvAdd);

        }

        private void dialogAdd(){
            Subject subject = subjectList.get(index);
            Dialog dialog = new Dialog(context,android.R.style.Theme_Material_NoActionBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_registration);
            String[] shifts = {"1","2","3","4"};
            String[] blocks = {"1","2"};
            String[] days = {"Even","Odd"};
            TextView tvName, tvID, tvStart, tvEnd;
            Spinner spnShift, spnBlocks, spnDays;
            Button btnSubmit, btnCancel;
            tvName = (TextView) dialog.findViewById(R.id.dRegistration_tvName);
            tvID = (TextView) dialog.findViewById(R.id.dRegistration_tvID);
            tvStart = (TextView) dialog.findViewById(R.id.dRegistration_tvStart);
            tvEnd = (TextView) dialog.findViewById(R.id.dRegistration_tvEnd);
            spnShift = (Spinner) dialog.findViewById(R.id.dRegistration_spnShift);
            spnBlocks = (Spinner) dialog.findViewById(R.id.dRegistration_spnBlock);
            spnDays = (Spinner) dialog.findViewById(R.id.dRegistration_spnDays);
            btnSubmit = (Button) dialog.findViewById(R.id.dRegistration_btnSubmit);
            btnCancel = (Button) dialog.findViewById(R.id.dRegistration_btnCancel);

            ArrayAdapter<String> adapterShift = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,shifts);
            ArrayAdapter<String> adapterBlock = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,blocks);
            ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,days);

            tvName.setText(subject.getName());
            tvID.setText(subject.getSubjectID());
            tvStart.setText("2020-07-01");
            tvEnd.setText("2020-08-01");
            spnShift.setAdapter(adapterShift);
            spnBlocks.setAdapter(adapterBlock);
            spnDays.setAdapter(adapterDays);
            spnBlocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (blocks[position].equals("1")){
                        tvStart.setText("2020-07-01");
                        tvEnd.setText("2020-08-01");
                    }else {
                        tvStart.setText("2020-08-01");
                        tvEnd.setText("2020-09-01");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String subjectID = subject.get_id();
                   String block = spnBlocks.getSelectedItem().toString();
                   String shift = spnShift.getSelectedItem().toString();
                   String days = spnDays.getSelectedItem().toString();
                   String startDate = tvStart.getText().toString();
                   String endDate = tvEnd.getText().toString();
                    HashMap<String,String> map = new HashMap<>();
                    map.put("subject", subjectID);
                    map.put("block", block);
                    map.put("shift", shift);
                    map.put("days", days);
                    map.put("startDate", startDate);
                    map.put("endDate", endDate);
                    Call<String> call = studentApi.registration(MainActivity.STUDENT.get_id(),map);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200){
                                clg(response.body());
                                Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            }else if (response.code() == 201){
                                clg(response.body());
                                Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            clg(t.getMessage());
                        }
                    });
                }
            });


            dialog.show();
        }

        private void clg(String s){
            Log.d("log",s);
        }
    }
}
