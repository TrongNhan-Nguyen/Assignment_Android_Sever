package com.example.assignment_nodejs.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.assignment_nodejs.R;
import com.example.assignment_nodejs.Retrofit_Manager;
import com.example.assignment_nodejs.Student_Api;
import com.example.assignment_nodejs.activities.LoginActivity;
import com.example.assignment_nodejs.activities.MainActivity;
import com.example.assignment_nodejs.models.Student;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {
    private View view;
    private TextView tvName, tvEmail, tvPhone, tvSex, tvBirthday;
    private TextView tvClass,tvSpecialized;
    private ImageView img,avatar;
    private Button btnChangePass;
    private Student_Api studentApi;
    private String realPath = "";
    private Student student;
    private String url = "http://10.0.2.2:3000/uploads/avatar/";
    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView();
        return view;
    }

    private void initView() {
        studentApi = Retrofit_Manager.retrofit.create(Student_Api.class);
        tvName = (TextView) view.findViewById(R.id.fProfile_tvName);
        tvEmail = (TextView) view.findViewById(R.id.fProfile_tvEmail);
        tvPhone = (TextView) view.findViewById(R.id.fProfile_tvPhone);
        tvSex = (TextView) view.findViewById(R.id.fProfile_tvSex);
        tvBirthday = (TextView) view.findViewById(R.id.fProfile_tvBirthday);
        tvClass = (TextView) view.findViewById(R.id.fProfile_tvClass);
        tvSpecialized = (TextView) view.findViewById(R.id.fProfile_tvSpecialized);
        btnChangePass = (Button) view.findViewById(R.id.fProfile_btnChangePass);
        img = (ImageView) view.findViewById(R.id.fProfile_img);
        Call<Student> call = studentApi.getProfile(LoginActivity.STUDENT.get_id());
        clg(LoginActivity.STUDENT.get_id());


        call.clone().enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.code() == 200){
                    student = response.body();
                    clg(student.get_id());
                    tvName.setText(student.getName());
                    tvBirthday.setText(student.getBirthday());
                    tvClass.setText(student.getStudentClass());
                    tvPhone.setText(student.getPhoneNumber());
                    tvSex.setText(student.getSex());
                    tvSpecialized.setText(student.getSpecialized());
                    tvEmail.setText(student.getEmail());
                    Picasso.get().load(url+student.getImg()).into(img);

                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                clg(t.getMessage());
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangePass();
            }
        });
    }

    private void dialogChangePass(){
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_change_password);
        EditText etCurrent, etNew, etConfirm;
        Button btnSubmit, btnClear, btnCancel;
        etCurrent = (EditText) dialog.findViewById(R.id.dChangePass_etCurrent);
        etNew = (EditText) dialog.findViewById(R.id.dChangePass_etNew);
        etConfirm = (EditText) dialog.findViewById(R.id.dChangePass_etConfirm);
        btnSubmit = (Button) dialog.findViewById(R.id.dChangePass_btnSubmit);
        btnCancel = (Button) dialog.findViewById(R.id.dChangePass_btnCancel);
        btnClear = (Button) dialog.findViewById(R.id.dChangePass_btnClear);
        avatar = (ImageView) dialog.findViewById(R.id.dChangePass_img);
        Picasso.get().load(url+student.getImg()).into(avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etConfirm.setText("");
                etCurrent.setText("");
                etNew.setText("");
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPass  = etCurrent.getText().toString();
                String newPass = etNew.getText().toString();
                String confirmPass = etConfirm.getText().toString();
                File file = new File(realPath);
                RequestBody requestBody;
                if (realPath.isEmpty()){
                    requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"");
                }else {
                    requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                }
                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar",realPath,requestBody);
                RequestBody current = RequestBody.create(MediaType.parse("text/plain"),currentPass);
                RequestBody pass = RequestBody.create(MediaType.parse("text/plain"),newPass);
                Call<String> call = studentApi.updatePass(LoginActivity.STUDENT.get_id(),current,pass,body);

                if (currentPass.isEmpty() || newPass.isEmpty() ||confirmPass.isEmpty()){
                    toast("Please, fill up all field");
                }else if (currentPass.length()<6 || newPass.length()<6 || confirmPass.length() <6){
                    toast("At least 6 character in each filed, please check gain");
                }else if (!newPass.equals(confirmPass)){
                    toast("Your confirm password doesn't match");
                }else {
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200){
                                clg(response.body());
                                dialog.dismiss();
                                toast(response.body());
                            }else if (response.code() == 201){
                                clg(response.body());
                                toast(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }




            }
        });
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && data!=null){
            Uri uri = data.getData();
            avatar.setImageURI(uri);
            realPath = getPathFromUri(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getPathFromUri (Uri uri){
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];
        Uri contentUri = null;
        if ("image".equals(type)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        final String selection = "_id=?";
        final String[] selectionArgs = new String[]{
                split[1]
        };
        return getDataColumn(contentUri, selection, selectionArgs);

    }


    public  String getDataColumn( Uri uri, String selection,String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = getActivity().getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    private void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
    private void clg(String s){
        Log.d("log",s);
    }
}
