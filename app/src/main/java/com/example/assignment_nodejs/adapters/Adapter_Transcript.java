package com.example.assignment_nodejs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.models.Transcript;

import java.util.List;

public class Adapter_Transcript extends RecyclerView.Adapter<Adapter_Transcript.ViewHolder> {
    private Context context;
    private List<Transcript> transcriptList;

    public Adapter_Transcript(Context context, List<Transcript> transcriptList) {
        this.context = context;
        this.transcriptList = transcriptList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_transcript,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transcript transcript = transcriptList.get(position);
        holder.tvStatus.setText(transcript.getStatus());
        holder.tvScores.setText(String.valueOf(transcript.getScores()));
        holder.tvID.setText(transcript.getSubject().getSubjectID());
        holder.tvName.setText(transcript.getSubject().getName());
    }

    @Override
    public int getItemCount() {
        return transcriptList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvID, tvName, tvStatus, tvScores;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = (TextView) itemView.findViewById(R.id.rTranscript_tvID);
            tvName = (TextView) itemView.findViewById(R.id.rTranscript_tvName);
            tvStatus = (TextView) itemView.findViewById(R.id.rTranscript_tvStatus);
            tvScores = (TextView) itemView.findViewById(R.id.rTranscript_tvScores);
        }
    }
}
