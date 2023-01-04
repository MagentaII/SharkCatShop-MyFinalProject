package com.example.sharkcatshop.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sharkcatshop.R;
import com.example.sharkcatshop.Room.StickerCart;
import com.example.sharkcatshop.Room.StickerItem;
import com.example.sharkcatshop.Room.StickerViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private TextView tvName, tvPrice;
    private ImageView stickerImage;
    private Button btnAddCart;
    private StickerItem stickerItem;
    private StickerViewModel stickerViewModel;
    private List<StickerCart> allStickerCartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariables(view);

        getParentFragmentManager().setFragmentResultListener("requestSticker", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                stickerItem = result.getParcelable("sticker");
                tvName.setText(stickerItem.getStickerName());
                tvPrice.setText(String.valueOf(stickerItem.getStickerPrice()));
                Glide.with(getContext()).load(stickerItem.getStickerImage()).into(stickerImage);
            }
        });

        stickerViewModel.getAllStickerLive().observe(getActivity(), new Observer<List<StickerCart>>() {
            @Override
            public void onChanged(List<StickerCart> stickerCarts) {
                allStickerCartList.addAll(stickerCarts);
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertStickerToRoom();
            }
        });

    }

    public void insertStickerToRoom(){
        StickerCart stickerCart = new StickerCart();
        stickerCart.setStickerName(stickerItem.getStickerName());
        stickerCart.setStickerPrice(stickerItem.getStickerPrice());
        stickerCart.setStickerImage(stickerItem.getStickerImage());

//        Toast.makeText(getContext(), stickerCart.getStickerName().toString(), Toast.LENGTH_SHORT).show();

        stickerViewModel.insertSticker(stickerCart);
        Toast.makeText(getContext(), "已加入購物車", Toast.LENGTH_SHORT).show();
    }

    public void initializeVariables(View view){
        allStickerCartList = new ArrayList<>();
        tvName = view.findViewById(R.id.tv_name);
        tvPrice = view.findViewById(R.id.tv_price);
        stickerImage = view.findViewById(R.id.stickerImage);
        btnAddCart = view.findViewById(R.id.btn_addCart);
        stickerViewModel = new ViewModelProvider(this).get(StickerViewModel.class);
    }

//    public interface StickerToCartClickedListeners{
//        void onAddToCartClicked(StickerItem stickerItem);
//    }

}