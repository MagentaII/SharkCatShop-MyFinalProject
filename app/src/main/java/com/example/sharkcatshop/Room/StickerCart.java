package com.example.sharkcatshop.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sticker_table")
public class StickerCart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "StickerName")
    private String stickerName;
    @ColumnInfo(name = "StickerPrice")
    private int stickerPrice;
    @ColumnInfo(name = "StickerImage")
    private int stickerImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public int getStickerPrice() {
        return stickerPrice;
    }

    public void setStickerPrice(int stickerPrice) {
        this.stickerPrice = stickerPrice;
    }

    public int getStickerImage() {
        return stickerImage;
    }

    public void setStickerImage(int stickerImage) {
        this.stickerImage = stickerImage;
    }
}
