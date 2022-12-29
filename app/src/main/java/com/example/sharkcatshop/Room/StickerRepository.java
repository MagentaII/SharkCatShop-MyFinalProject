package com.example.sharkcatshop.Room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StickerRepository {

    private StickerDatabase stickerDatabase;
    private StickerDao stickerDao;
    private LiveData<List<StickerCart>> allStickerLive;

    private Executor executor = Executors.newSingleThreadExecutor();

    public StickerRepository(Context context) {
        stickerDatabase = StickerDatabase.getDatabase(context.getApplicationContext());
        stickerDao = stickerDatabase.getStickerDao();
        allStickerLive = stickerDao.getAllStickers();
    }

    public LiveData<List<StickerCart>> getAllStickerLive() {
        return allStickerLive;
    }

    public void insertSticker(StickerCart... stickerCarts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                stickerDao.insertSticker(stickerCarts);
            }
        });
    }

    public void updateSticker(StickerCart... stickerCarts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                stickerDao.updateSticker(stickerCarts);
            }
        });
    }

    public void deleteSticker(StickerCart... stickerCarts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                stickerDao.deleteSticker(stickerCarts);
            }
        });
    }

    public void deleteAllStickers(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                stickerDao.deleteAllStickers();
            }
        });
    }


}
