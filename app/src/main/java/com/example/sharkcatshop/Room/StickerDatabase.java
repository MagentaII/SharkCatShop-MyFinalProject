package com.example.sharkcatshop.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StickerCart.class}, version = 1, exportSchema = false)
public abstract class StickerDatabase extends RoomDatabase {
    private static StickerDatabase INSTANCE;
    static synchronized StickerDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StickerDatabase.class, "SharkCatShop_database")
                    .build();
        }
        return INSTANCE;
    }

    public abstract StickerDao getStickerDao();


}
