package com.example.assignment_nodejs.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.fragments.Fragment_NewsDetail;
import com.example.assignment_nodejs.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_News extends RecyclerView.Adapter<Adapter_News.ViewHolder> {
    private Context context;
    private List<News> newsList;

    public Adapter_News(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.raw_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        String url = "http://10.0.2.2:3000/uploads/news/";
        holder.tvTitle.setText(news.getTitle());
        holder.tvDesc.setText(news.getDesc());
        Picasso.get().load(url+news.getImg()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fragment_NewsDetail detail = new Fragment_NewsDetail();
               Bundle bundle = new Bundle();
               bundle.putSerializable("news",news);
               detail.setArguments(bundle);
                ((FragmentActivity)context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frameContainer, detail)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc;
        private ImageView img;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.rawNews_tvTitle);
            tvDesc = (TextView) itemView.findViewById(R.id.rawNews_tvDesc);
            img = (ImageView) itemView.findViewById(R.id.rawNews_img);
        }
    }

    private void toast (String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    private void clg (String s){
        Log.d("log",s);
    }
}
