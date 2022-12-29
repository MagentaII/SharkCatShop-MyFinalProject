package com.example.sharkcatshop.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StickerDao {

    @Insert
    void insertSticker(StickerCart... stickerCarts);

    @Update
    void updateSticker(StickerCart... stickerCarts);

    @Delete
    void deleteSticker(StickerCart... stickerCarts);

    @Query("SELECT * FROM sticker_table")
    LiveData<List<StickerCart>> getAllStickers();

    @Query("DELETE FROM sticker_table")
    void deleteAllStickers();
}
