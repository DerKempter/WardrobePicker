package com.bebs.wardrobepicker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Season")
public class Season implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "spring")
    private boolean spring;

    @ColumnInfo(name = "summer")
    private boolean summer;

    @ColumnInfo(name = "autumn")
    private boolean autumn;

    @ColumnInfo(name = "winter")
    private boolean winter;

    @ColumnInfo(name = "spook")
    private boolean spook;

    public Season(int uid, boolean spring, boolean summer, boolean autumn, boolean winter, boolean spook) {
        this.id = uid;
        this.spring = spring;
        this.summer = summer;
        this.autumn = autumn;
        this.winter = winter;
        this.spook = spook;
    }

    public Season() {
    }

    public Season(boolean spring, boolean summer, boolean autumn, boolean winter, boolean spook) {
        this.spring = spring;
        this.summer = summer;
        this.autumn = autumn;
        this.winter = winter;
        this.spook = spook;
    }

    protected Season(Parcel in) {
        id = in.readInt();
        spring = in.readByte() != 0;
        summer = in.readByte() != 0;
        autumn = in.readByte() != 0;
        winter = in.readByte() != 0;
        spook = in.readByte() != 0;
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSpring() {
        return spring;
    }

    public void setSpring(boolean spring) {
        this.spring = spring;
    }

    public boolean isSummer() {
        return summer;
    }

    public void setSummer(boolean summer) {
        this.summer = summer;
    }

    public boolean isAutumn() {
        return autumn;
    }

    public void setAutumn(boolean autumn) {
        this.autumn = autumn;
    }

    public boolean isWinter() {
        return winter;
    }

    public void setWinter(boolean winter) {
        this.winter = winter;
    }

    public boolean isSpook() {
        return spook;
    }

    public void setSpook(boolean spook) {
        this.spook = spook;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (spring ? 1 : 0));
        dest.writeByte((byte) (summer ? 1 : 0));
        dest.writeByte((byte) (autumn ? 1 : 0));
        dest.writeByte((byte) (winter ? 1 : 0));
        dest.writeByte((byte) (spook ? 1 : 0));
    }
}
