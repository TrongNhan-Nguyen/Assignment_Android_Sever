package com.example.assignment_nodejs.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.models.News;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_NewsDetail extends Fragment {
    private View view;
    private ImageView img;
    private TextView tvTitle, tvDesc, tvTime;
    private Student_Api studentApi;

    public Fragment_NewsDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        initView();
        return view;
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        Bundle bundle = getArguments();
        News news = (News) bundle.getSerializable("news");
        img = (ImageView) view.findViewById(R.id.fDetail_img);
        tvTitle = (TextView) view.findViewById(R.id.fDetail_tvTitle);
        tvDesc = (TextView) view.findViewById(R.id.fDetail_tvDesc);
        tvTime = (TextView) view.findViewById(R.id.fDetail_tvTime);
        //  Cách 1: Truyền dữ liệu từ adpter sang
//        if (news != null) {
//            String url = "http://10.0.2.2:3000/uploads/" + news.getImg();
//            Picasso.get().load(url).into(img);
//            tvTitle.setText(news.getTitle());
//            tvDesc.setText(news.getTitle());
//            tvTime.setText(news.getTime());
//        }
        //  Cách 2 : Lấy dữ liệu trực tiếp từ sever
        if (news != null) {
            String newsID = news.get_id();
            Call<News> call = studentApi.getNews(newsID);
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                   if (response.code() == 200){
                       String url = "http://10.0.2.2:3000/uploads/" + response.body().getImg();
                       Picasso.get().load(url).into(img);
                       tvTitle.setText(response.body().getTitle());
                       tvDesc.setText(response.body().getTitle());
                       tvTime.setText(response.body().getTime());
                   }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
