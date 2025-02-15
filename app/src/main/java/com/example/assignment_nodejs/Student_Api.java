package com.example.assignment_nodejs;

import com.example.assignment_nodejs.models.Inbox;
import com.example.assignment_nodejs.models.News;
import com.example.assignment_nodejs.models.Student;
import com.example.assignment_nodejs.models.Subject;
import com.example.assignment_nodejs.models.Transcript;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Student_Api {
    //    @Headers({"Accept: application/json"})
    //    GET
    @GET("/student")
    Call<List<News>> getNewsList();
    @GET("/student/news/{newsID}")
    Call<News> getNews(@Path(value = "newsID") String newsID);
    @GET("/student/transcript")
    Call<String> getTranscript(@Query("userID") String userID);
    @GET("/student/schedule")
    Call<String> getSchedule(@Query("userID") String userID);
    @GET("/student/profile")
    Call<Student> getProfile(@Query("studentID") String studentID);
    @GET("/student/registration")
    Call<List<Subject>> getRegistration();
    @GET("/student/email")
    Call<List<Inbox>> getInboxList(@Query("userID") String userID);
    @GET("/student/email/{emailID}")
    Call<String> getInbox(@Path("emailID") String emailID,@Query("userID") String userID);

    //    Post
    @POST("/login")
    Call<Student> login(@Body HashMap<String, String> map);

    @Multipart
    @POST("/student/profile")
    Call<String> updatePass(
            @Query("studentID") String studentID,
            @Part("currentPass")RequestBody currentPass,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part image);

    @POST("/student/registration")
    Call<String> registration(@Query("studentID") String studentID, @Body HashMap<String,String> map);
}
