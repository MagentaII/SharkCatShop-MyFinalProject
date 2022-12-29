package com.example.sharkcatshop.Login_And_Signup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharkcatshop.MainActivity;
import com.example.sharkcatshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginDialogFragment extends DialogFragment {

    Button btnLogin;
    TextView tvSignUp;
    EditText etUsername, etPassword;
    FirebaseDatabase database;
    DatabaseReference reference;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);
        builder.setView(view);

        btnLogin = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.tv_signup);
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){

                }else{
                    checkUser();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();


        return dialog;

    }

    //驗證Username是否為空白
    public Boolean validateUsername(){
        String val = etUsername.getText().toString();
        if(val.isEmpty()){
            etUsername.setError("Username cannot be empty");
            return false;
        }else{
            etUsername.setError(null);
            return true;
        }
    }

    //驗證Password是否為空白
    public Boolean validatePassword(){
        String val = etPassword.getText().toString();
        if(val.isEmpty()){
            etPassword.setError("Password cannot be empty");
            return false;
        }else{
            etPassword.setError(null);
            return true;
        }
    }

    //確認帳密是否正確 (是不是跟資料庫的一樣)
    public void checkUser(){
        String user_Username = etUsername.getText().toString().trim(); //trim移除空格
        String user_Password = etPassword.getText().toString().trim(); //trim移除空格

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");//從users目錄開始

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();

        //監聽users底下的資料(該資料有排序過，且等於使用者所輸入的Username)
        Query checkUserDatabase = reference.orderByChild("username").equalTo(user_Username); //先根據username進行升冪排序，然後判斷是否等於使用者所輸入的Username

        //開始監聽(接收資料，這個方法只會執行一次而且是立即執行)
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    etUsername.setError(null);
                    //在user_Username/password/底下的資料中取得String的類別
                    String passwordFromDB = snapshot.child(user_Username).child("password").getValue(String.class);

                    //如果這一堆的物件(username)不等於使用者所輸入的Password
                    if(passwordFromDB.equals(user_Password)){
                        etUsername.setError(null);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        etPassword.setError("Invalid Credentials");
                        etPassword.requestFocus();
                    }
                }else{
                    etUsername.setError("User does not exist");
                    etUsername.requestFocus();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }

}
