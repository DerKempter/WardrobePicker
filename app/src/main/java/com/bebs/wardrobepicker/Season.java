package com.bebs.wardrobepicker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "Season")
public class Season implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "spring")
    private int spring;

    @ColumnInfo(name = "summer")
    private int summer;

    @ColumnInfo(name = "autumn")
    private int autumn;

    @ColumnInfo(name = "winter")
    private int winter;

    @ColumnInfo(name = "spook")
    private int spook;

    public Season(int id) {
        this.id = id;
    }

    public Season(int uid, int spring, int summer, int autumn, int winter, int spook) {
        this.id = uid;
        this.spring = spring;
        this.summer = summer;
        this.autumn = autumn;
        this.winter = winter;
        this.spook = spook;
    }

    public Season() {
    }

    public Season(String season) {
        String[] splitSeason = season.split("");
        List<String> stringArray = Arrays.asList(splitSeason);
        if (stringArray.contains("0")){
            this.spring = 1;
        } else {
            this.spring = 0;
        }
        if (stringArray.contains("1")) {
            this.summer = 1;
        } else {
            this.summer = 0;
        }
        if (stringArray.contains("2")) {
            this.autumn = 1;
        } else {
            this.autumn = 0;
        }
        if (stringArray.contains("3")) {
            this.winter = 1;
        } else {
            this.winter = 0;
        }
        if (stringArray.contains("4")) {
            this.spook = 1;
        } else {
            this.spook = 0;
        }
    }

    public Season(int spring, int summer, int autumn, int winter, int spook) {
        this.spring = spring;
        this.summer = summer;
        this.autumn = autumn;
        this.winter = winter;
        this.spook = spook;
    }

    protected Season(Parcel in) {
        id = in.readInt();
        spring = in.readInt();
        summer = in.readInt();
        autumn = in.readInt();
        winter = in.readInt();
        spook = in.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return spring == season.spring &&
                summer == season.summer &&
                autumn == season.autumn &&
                winter == season.winter &&
                spook == season.spook;
    }

    @Override
    public int hashCode() {
        return Objects.hash(spring, summer, autumn, winter, spook);
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

    public int getSpring() {
        return spring;
    }

    public void setSpring(int spring) {
        this.spring = spring;
    }

    public int getSummer() {
        return summer;
    }

    public void setSummer(int summer) {
        this.summer = summer;
    }

    public int getAutumn() {
        return autumn;
    }

    public void setAutumn(int autumn) {
        this.autumn = autumn;
    }

    public int getWinter() {
        return winter;
    }

    public void setWinter(int winter) {
        this.winter = winter;
    }

    public int getSpook() {
        return spook;
    }

    public void setSpook(int spook) {
        this.spook = spook;
    }

    public ArrayList<Integer> getSeasons() {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(getSpring());
        res.add(getSummer());
        res.add(getAutumn());
        res.add(getWinter());
        res.add(getSpook());

        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(spring);
        dest.writeInt(summer);
        dest.writeInt(autumn);
        dest.writeInt(winter);
        dest.writeInt(spook);
    }
}
