package com.example.sharkcatshop.Login_And_Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharkcatshop.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etUserName, etPassword;
    TextView tvLogin;
    Button btnSignUp;
    FirebaseDatabase database;
    DatabaseReference reference;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etUserName = findViewById(R.id.et_userName);
        etPassword = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.tv_login);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users"); // 如果()內為空白，表示從根目錄開始

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                user = new User(name, email, username, password);

                reference.child(username).setValue(user);//指到username的目錄，在底下放入user的值，key=username

                Toast.makeText(SignupActivity.this, "You have signup successfully!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




    }
}