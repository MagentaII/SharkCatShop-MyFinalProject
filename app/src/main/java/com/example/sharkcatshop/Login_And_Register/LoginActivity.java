package com.example.sharkcatshop.Login_And_Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sharkcatshop.MainActivity;
import com.example.sharkcatshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button btnWelcome;
    ImageView imageView;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeVariables();

        btnWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    public void initializeVariables(){
        btnWelcome = findViewById(R.id.btn_welcome);
        imageView = findViewById(R.id.imageView);
    }

    public void openDialog(){
        DialogFragment dialogFragment = new LoginDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "login");
    }

    // BroadcastReceiver

    @Override
    protected void onResume() {
        super.onResume();
        // 註冊mConnReceiver，並用IntentFilter設置接收的事件類型為網路開關
        this.registerReceiver(mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 解除註冊
        this.unregisterReceiver(mConnReceiver);
    }

    // 建立一個BroadcastReceiver，名為mConnReceiver
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 當使用者開啟或關閉網路時會進入這邊

            // 判斷目前有無網路
            if(isNetworkAvailable()) {
                // 以連線至網路，做更新資料等事情
                Toast.makeText(LoginActivity.this, "有網路!", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.ic_baseline_check_24);
            }
            else {
                // 沒有網路
                Toast.makeText(LoginActivity.this, "沒網路!", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.ic_baseline_close_24);
            }
        }
    };

    // 回傳目前是否已連線至網路
    public boolean isNetworkAvailable()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null &&
                networkInfo.isConnected();
    }

}