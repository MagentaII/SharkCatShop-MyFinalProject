package com.example.sharkcatshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
//import android.view.View;

import com.example.sharkcatshop.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }

    //實現AppBar上面的清單按鈕以及返回按鈕
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}