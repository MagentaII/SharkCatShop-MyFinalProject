package com.example.sharkcatshop.ShoppingCart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sharkcatshop.Home.DetailFragment;
import com.example.sharkcatshop.Home.HomeAdapter;
import com.example.sharkcatshop.R;
import com.example.sharkcatshop.Room.StickerCart;
import com.example.sharkcatshop.Room.StickerItem;
import com.example.sharkcatshop.Room.StickerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private RecyclerView recyclerView;
    private StickerViewModel stickerViewModel;
    private ShoppingCartAdapter shoppingCartAdapter;
    private Button btnDelete;

    public static ShoppingCartFragment newInstance() {
        return new ShoppingCartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariables(view);

        stickerViewModel.getAllStickerLive().observe(getActivity(), new Observer<List<StickerCart>>() {
            @Override
            public void onChanged(List<StickerCart> stickerCarts) {
                shoppingCartAdapter.setAllStickerCartList(stickerCarts);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stickerViewModel.deleteAllStickers();
            }
        });



    }
    public void initializeVariables(View view){
        btnDelete = view.findViewById(R.id.btn_delete);
        shoppingCartAdapter = new ShoppingCartAdapter(getContext());
        stickerViewModel = new ViewModelProvider(this).get(StickerViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(shoppingCartAdapter);
    }



}