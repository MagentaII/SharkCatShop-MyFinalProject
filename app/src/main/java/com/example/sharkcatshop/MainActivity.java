package com.example.sharkcatshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//import android.view.View;

import com.example.sharkcatshop.Login_And_Register.LoginActivity;
import com.example.sharkcatshop.Login_And_Register.User;
import com.example.sharkcatshop.Message.MessageActivity;
import com.example.sharkcatshop.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private Intent intent;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        intent = getIntent();
//        final String userid = intent.getStringExtra("username");
//        Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();

        setSupportActionBar(binding.contentLayout.toolbar); // 要去AndroidManifest.xml設為NoActionBar

        //Navigation Drawer 畫面的切換
        navController = Navigation.findNavController(this, R.id.nav_host);
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment, R.id.shoppingCartFragment)
                .setOpenableLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        binding.contentLayout.fab.setOnClickListener(new View.OnClickListener() {
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

//        if(item.getItemId() == R.id.logoutFragment){
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
    }
}