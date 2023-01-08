package com.example.sharkcatshop.Login_And_Register;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharkcatshop.MainActivity;
import com.example.sharkcatshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginDialogFragment extends DialogFragment {

    private Button btnLogin;
    private TextView tvSignUp;
    private EditText etEmail, etPassword;
    private CheckBox checkBoxRemember;

    private FirebaseAuth firebaseAuth;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);
        builder.setView(view);

        initializeVariables(view);

        output(); //先執行我寫的副程式，可以先參考

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();


                SharedPreferences user =getActivity().getSharedPreferences("signup",MODE_PRIVATE);
                String Email=user.getString("account","");
                String Password=user.getString("password",""); //取得之前註冊好的資料


                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "All fileds are required", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        input(); ///登入成功的話才執行這個副程式
                                    }else{
                                        Toast.makeText(getContext(), "Authentication failed!!", Toast.LENGTH_SHORT).show();
                                        etEmail.setText(""); //清空
                                        etPassword.setText("");
                                    }
                                }
                            });
                }
                dialog.dismiss();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;

    }

    public void initializeVariables(View view){
        firebaseAuth = FirebaseAuth.getInstance();
        btnLogin = view.findViewById(R.id.btn_login);
        tvSignUp = view.findViewById(R.id.tv_signup);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        checkBoxRemember = view.findViewById(R.id.checkBoxRemember);
    }

    //用sharePreferences記住帳密，https://ithelp.ithome.com.tw/articles/10248424

    private void input() {

        SharedPreferences user = getActivity().getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        if (checkBoxRemember.isChecked()) { //如果記住密碼那個選項有打勾就把這次成功登入的資料儲存起來
            edit.putString("account2",etEmail.getText().toString());
            edit.putString("password2",etPassword.getText().toString());
            edit.putBoolean("remember",true);  //把我自己設的remember狀態設為true

        } else {
            edit.remove("account2"); //若上次沒打勾就把它清除記住的資料
            edit.remove("password2");
            edit.putBoolean("remember",false);
        }
        edit.commit();
    }

    private void output(){
        SharedPreferences user =getActivity().getSharedPreferences("remember",MODE_PRIVATE);
        String account2=user.getString("account2","");  //若沒取得就是沒任何東西 " "
        String password2=user.getString("password2","");
        boolean remember=user.getBoolean("remember",false);
        //在input()我是寫成若打勾則讓它remember設為true，所以若沒取到則是false

        etEmail.setText(account2); //有取到先前給予的資料的話就會顯示出來
        etPassword.setText(password2);
        checkBoxRemember.setChecked(remember);//並且利用remember的狀態判斷上次是否打勾，有的話就顯示打勾
    }

}
