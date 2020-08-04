package com.example.assignment_nodejs.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.activities.MainActivity;
import com.example.assignment_nodejs.fragments.Fragment_InboxDetail;
import com.example.assignment_nodejs.models.Inbox;

import java.util.List;

public class Adapter_Inbox extends RecyclerView.Adapter<Adapter_Inbox.ViewHolder> {
    private Context context;
    private List<Inbox> inboxList;



    public Adapter_Inbox(Context context, List<Inbox> inboxList) {
        this.context = context;
        this.inboxList = inboxList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_inbox,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inbox inbox = inboxList.get(position);
        holder.tvFrom.setText(inbox.getFrom());
        holder.tvContent.setText(inbox.getContent());
        holder.tvPubDate.setText(inbox.getPubDate());
        if (inbox.isActive()){
            holder.tvFrom.setTextColor(Color.parseColor("#4E4D4D"));
            holder.tvContent.setTextColor(Color.parseColor("#4E4D4D"));
            holder.tvPubDate.setTextColor(Color.parseColor("#4E4D4D"));
        }else {
            holder.tvFrom.setTextColor(Color.parseColor("#03DAC5"));
            holder.tvContent.setTextColor(Color.parseColor("#03DAC5"));
            holder.tvPubDate.setTextColor(Color.parseColor("#03DAC5"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_InboxDetail inboxDetail = new Fragment_InboxDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Inbox",inbox);
                inboxDetail.setArguments(bundle);
                ((MainActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frameContainer, inboxDetail)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvFrom, tvContent, tvPubDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFrom = (TextView) itemView.findViewById(R.id.rawInbox_tvFrom);
            tvContent = (TextView) itemView.findViewById(R.id.rawInbox_tvContent);
            tvPubDate = (TextView) itemView.findViewById(R.id.rawInbox_tvPubDate);
        }
    }
}
