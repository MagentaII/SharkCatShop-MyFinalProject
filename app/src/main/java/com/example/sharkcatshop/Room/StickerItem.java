package com.example.sharkcatshop.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

public class StickerItem implements Parcelable{

    private String stickerName;
    private int stickerPrice;
    private int stickerImage;

    public StickerItem(String stickerName, int stickerPrice, int stickerImage) {
        this.stickerName = stickerName;
        this.stickerPrice = stickerPrice;
        this.stickerImage = stickerImage;
    }

    protected StickerItem(Parcel in) {
        stickerName = in.readString();
        stickerPrice = in.readInt();
        stickerImage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stickerName);
        dest.writeInt(stickerPrice);
        dest.writeInt(stickerImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StickerItem> CREATOR = new Creator<StickerItem>() {
        @Override
        public StickerItem createFromParcel(Parcel in) {
            return new StickerItem(in);
        }

        @Override
        public StickerItem[] newArray(int size) {
            return new StickerItem[size];
        }
    };

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
