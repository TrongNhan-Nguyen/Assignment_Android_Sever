package com.example.assignment_nodejs.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnClear, btnLogin, btnRegister,btnGetImg;
    private ImageView img;
    private Student_Api studentApi;
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
        btnRegister = (Button) findViewById(R.id.login_btnRegister);
//        btnGetImg = (Button) findViewById(R.id.login_btnGetImg);
//        img = (ImageView) findViewById(R.id.login_img);
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
                HashMap<String,String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",password);
                Call<Student> call = studentApi.login(map);
                if (email.equals("") && password.equals("")){
                     toast("Please, fill up all field");
                     return;
                }
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        switch (response.code()){
                            case 200:
                                toast("Login Successfully");
                                clg(response.body().toString());
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Register");
            }
        });
//        Test upload
//        btnGetImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toast("Set image");
//                String url = "http://10.0.2.2:3000/uploads/1594302090018-Tree.png";
//                Picasso.get().load(url).into(img);
//            }
//        });
    }

    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void clg(String s){
        Log.d("Log",s);
    }

}
