package com.example.sharkcatshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.sharkcatshop.Login_And_Register.LoginActivity;
import com.example.sharkcatshop.Login_And_Register.User;
import com.example.sharkcatshop.Message.MessageActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View contentLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private AppBarConfiguration appBarConfiguration;
    private Intent intent;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private String CHANNEL_ID = "Coder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**檢查手機版本是否支援通知；若支援則新增"頻道"*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "DemoCode", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }

        initializeVariables();

        setNotification();

        setSupportActionBar(toolbar); // 要去AndroidManifest.xml設為NoActionBar

        //Navigation Drawer 畫面的切換
        navController = Navigation.findNavController(this, R.id.nav_host);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment, R.id.shoppingCartFragment)
                .setOpenableLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                        intent.putExtra("userid",user.getId());
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

    }

    public void initializeVariables(){
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        contentLayout = findViewById(R.id.contentLayout);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);

    }

    //實現AppBar上面的清單按鈕以及返回按鈕
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //顯示appbar右上角的圖案
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    //實現appbar右上角圖案的點擊事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutFragment:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return false;
    }

    private void setNotification(){
        /**建立要嵌入在通知裡的介面*/
        RemoteViews view = new RemoteViews(getPackageName(),R.layout.notification_layout);

        /**初始化Intent，攔截點擊事件*/
        Intent intent = new Intent(MainActivity.this,NotificationReceiver.class);

        /**設置通知內"Hi"這個按鈕的點擊事件(以Intent的Action傳送標籤，標籤為Hi)*/
        intent.setAction("我需要這個!");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this
                ,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        /**設置通知內"Close"這個按鈕的點擊事件(以Intent的Action傳送標籤，標籤為Close)*/
        intent.setAction("Close");
        PendingIntent close = PendingIntent.getBroadcast(MainActivity.this
                ,0,intent, PendingIntent.FLAG_CANCEL_CURRENT);

        /**設置通知內的控件要做的事*/
        /*設置標題*/
        view.setTextViewText(R.id.textView_Title,"最新消息!!");
        /*設置圖片*/
        view.setImageViewResource(
                R.id.imageView_Icon,R.drawable.wild_cat5);

        /*設置"Hi"按鈕點擊事件(綁pendingIntent)*/
        view.setOnClickPendingIntent(R.id.button_Noti_Hi,pendingIntent);
        /*設置"Close"按鈕點擊事件(綁close)*/
        view.setOnClickPendingIntent(R.id.button_Noti_Close,close);

        /**建置通知欄位的內容*/
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                .setSmallIcon(R.drawable.wild_cat5)
                .setContent(view)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(MainActivity.this);
        /**發出通知*/
        notificationManagerCompat.notify(1,builder.build());
    }

}