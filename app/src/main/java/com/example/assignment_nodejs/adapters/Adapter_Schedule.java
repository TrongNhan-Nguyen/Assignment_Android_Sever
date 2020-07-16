package com.example.assignment_nodejs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.models.Schedule;
import com.example.assignment_nodejs.models.Transcript;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter_Schedule extends RecyclerView.Adapter<Adapter_Schedule.ViewHolder> implements Filterable {
    private Context context;
    private List<Schedule> scheduleList;
    private List<Schedule> allSchedule;

    public Adapter_Schedule(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.allSchedule = new ArrayList<>(scheduleList);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_schedule,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.tvID.setText(schedule.getSubjectID());
        holder.tvName.setText(schedule.getSubjectName());
        holder.tvDate.setText(schedule.getDate());
        holder.tvShift.setText(schedule.getShift());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Schedule> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filterList.addAll(allSchedule);
            }else {
                for (Schedule schedule: allSchedule){
                    if (schedule.getSubjectName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterList.add(schedule);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            scheduleList.clear();
            scheduleList.addAll((Collection<? extends Schedule>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvID, tvName, tvDate, tvShift;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = (TextView) itemView.findViewById(R.id.rawSchedule_tvID);
            tvName = (TextView) itemView.findViewById(R.id.rawSchedule_tvName);
            tvDate = (TextView) itemView.findViewById(R.id.rawSchedule_tvDate);
            tvShift = (TextView) itemView.findViewById(R.id.rawSchedule_tvShift);
        }
    }
}
