package com.example.sharkcatshop.Home;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.sharkcatshop.R;
import com.example.sharkcatshop.Room.StickerCart;
import com.example.sharkcatshop.Room.StickerItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.StickerClickedListeners {

    private List<StickerItem> allstickerList;
    private ImageSlider imageSlider;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariables(view);

        imageSlide();

//        progress();

        setStickerItemList();

        homeAdapter.setAllStickerList(allstickerList);
        recyclerView.setAdapter(homeAdapter);


    }

    public void initializeVariables(View view){
        allstickerList = new ArrayList<>();
        imageSlider = view.findViewById(R.id.imageSlider);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeAdapter(getContext(), this);
    }

    public void setStickerItemList(){
        allstickerList.add(new StickerItem("Lv1 Wild Cat", 62, R.drawable.wild_cat1));
        allstickerList.add(new StickerItem("Lv2 Wild Cat", 38, R.drawable.wild_cat2));
        allstickerList.add(new StickerItem("Lv3 Wild Cat", 95, R.drawable.wild_cat3));
        allstickerList.add(new StickerItem("Lv4 Wild Cat", 26, R.drawable.wild_cat4));
        allstickerList.add(new StickerItem("Lv5 Wild Cat", 83, R.drawable.wild_cat5));
        allstickerList.add(new StickerItem("Lv6 Wild Cat", 46, R.drawable.wild_cat6));
        allstickerList.add(new StickerItem("Lv7 Wild Cat", 16, R.drawable.wild_cat7));
        allstickerList.add(new StickerItem("Lv8 Wild Cat", 36, R.drawable.wild_cat8));
        allstickerList.add(new StickerItem("Lv1 Shark", 36, R.drawable.shark1));
    }

    //首頁banner
    public void imageSlide(){
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    //轉圈圈等待
    public void progress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCardClicked(StickerItem StickerItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("sticker", StickerItem);
        getParentFragmentManager().setFragmentResult("requestSticker", bundle);
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_homeFragment_to_detailFragment);
    }
}