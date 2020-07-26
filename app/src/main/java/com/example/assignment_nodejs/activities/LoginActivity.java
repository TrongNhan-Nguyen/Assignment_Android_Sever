package com.example.assignment_nodejs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.models.Student;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnClear, btnLogin;
    private Student_Api studentApi;
    public static Student STUDENT = new Student();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        handleEvent();
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        etEmail = (EditText) findViewById(R.id.login_etEmail);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        btnClear = (Button) findViewById(R.id.login_btnClear);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
    }

    private void handleEvent() {

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassword.setText("");
                etEmail.setText((""));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
                Call<Student> call = studentApi.login(map);
                if (email.equals("") && password.equals("")) {
                    toast("Please, fill up all field");
                    return;
                }
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        switch (response.code()) {
                            case 200:
                                toast("Login Successfully");
                                Student student = response.body();
                                STUDENT = student;
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                break;
                            case 201:
                                toast("User not found");
                                break;
                            case 202:
                                toast("Pass incorrect");

                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        toast(t.getMessage());
                        clg(t.getMessage());
                    }
                });
            }
        });


    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void clg(String s) {
        Log.d("Log", s);
    }

}
