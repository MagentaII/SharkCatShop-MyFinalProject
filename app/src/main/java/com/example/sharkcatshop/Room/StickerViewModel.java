package com.example.sharkcatshop.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StickerViewModel extends AndroidViewModel {

    private StickerRepository stickerRepository;

    public StickerViewModel(@NonNull Application application) {
        super(application);

        stickerRepository = new StickerRepository(application);
    }

    public LiveData<List<StickerCart>> getAllStickerLive() {
        return stickerRepository.getAllStickerLive();
    }

    public void insertSticker(StickerCart... stickerCarts){
        stickerRepository.insertSticker(stickerCarts);
    }

    public void updateSticker(StickerCart... stickerCarts){
        stickerRepository.updateSticker(stickerCarts);
    }

    public void deleteSticker(StickerCart... stickerCarts){
        stickerRepository.deleteSticker();
    }

    public void deleteAllStickers(){
        stickerRepository.deleteAllStickers();
    }

}
