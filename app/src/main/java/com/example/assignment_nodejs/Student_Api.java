package com.example.assignment_nodejs;

import com.example.assignment_nodejs.models.Student;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Student_Api {
    @Headers({"Accept: application/json"})
    @POST("/login")
    Call<Student> login (@Body HashMap<String,String> map);
}
