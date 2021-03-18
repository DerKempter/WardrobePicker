package com.bebs.wardrobepicker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@TypeConverters(Converters.class)
@Entity(tableName = "outfits")
public class Outfit implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "size")
    private int size;

    @ColumnInfo(name = "created")
    private java.util.Date created;

    @ColumnInfo(name = "pullover")
    private int pullover;

    @ColumnInfo(name = "skirt")
    private int skirt;

    @ColumnInfo(name = "dress")
    private int dress;

    @ColumnInfo(name = "pants")
    private int pants;

    @ColumnInfo(name = "shorts")
    private int shorts;

    @ColumnInfo(name = "jumpsuit")
    private int jumpsuit;

    @ColumnInfo(name = "jacket")
    private int jacket;

    @ColumnInfo(name = "shoes")
    private int shoes;

    @ColumnInfo(name = "jewelery")
    private int jewelery;

    @ColumnInfo(name = "belt")
    private int belt;

    @ColumnInfo(name = "shirt")
    private int shirt;

    @ColumnInfo(name = "hoodie")
    private int hoodie;

    @ColumnInfo(name = "crop_top")
    private int crop_top;

    public Outfit(int uid, String name, int pullover, int skirt, int dress, int pants, int shorts, int jumpsuit, int jacket, int shoes, int jewelery, int belt, int shirt, int hoodie, int crop_top) {
        this.uid = uid;
        this.name = name;
        this.pullover = pullover;
        this.skirt = skirt;
        this.dress = dress;
        this.pants = pants;
        this.shorts = shorts;
        this.jumpsuit = jumpsuit;
        this.jacket = jacket;
        this.shoes = shoes;
        this.jewelery = jewelery;
        this.belt = belt;
        this.shirt = shirt;
        this.hoodie = hoodie;
        this.crop_top = crop_top;
    }

    @Ignore
    public Outfit(int uid, String name) {
        this.name = name;
    }

    @Ignore
    public Outfit() {
    }

    protected Outfit(Parcel in) {
        uid = in.readInt();
        name = in.readString();
        size = in.readInt();
        created = new Date(in.readLong());
        pullover = in.readInt();
        skirt = in.readInt();
        dress = in.readInt();
        pants = in.readInt();
        shorts = in.readInt();
        jumpsuit = in.readInt();
        jacket = in.readInt();
        shoes = in.readInt();
        jewelery = in.readInt();
        belt = in.readInt();
        shirt = in.readInt();
        hoodie = in.readInt();
        crop_top = in.readInt();
    }

    public static final Creator<Outfit> CREATOR = new Creator<Outfit>() {
        @Override
        public Outfit createFromParcel(Parcel in) {
            return new Outfit(in);
        }

        @Override
        public Outfit[] newArray(int size) {
            return new Outfit[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    public Date getCreated() { return created; }

    public void setCreated(Date created) { this.created = created; }

    public int getPullover() { return pullover; }

    public void setPullover(int pullover) { this.pullover = pullover; }

    public int getSkirt() { return skirt; }

    public void setSkirt(int skirt) { this.skirt = skirt; }

    public int getDress() { return dress; }

    public void setDress(int dress) { this.dress = dress; }

    public int getPants() { return pants; }

    public void setPants(int pants) { this.pants = pants; }

    public int getShorts() { return shorts; }

    public void setShorts(int shorts) { this.shorts = shorts; }

    public int getJumpsuit() { return jumpsuit; }

    public void setJumpsuit(int jumpsuit) { this.jumpsuit = jumpsuit; }

    public int getJacket() { return jacket; }

    public void setJacket(int jacket) { this.jacket = jacket; }

    public int getShoes() { return shoes; }

    public void setShoes(int shoes) { this.shoes = shoes; }

    public int getJewelery() { return jewelery; }

    public void setJewelery(int jewelery) { this.jewelery = jewelery; }

    public int getBelt() { return belt; }

    public void setBelt(int belt) { this.belt = belt; }

    public int getShirt() { return shirt; }

    public void setShirt(int shirt) { this.shirt = shirt; }

    public int getHoodie() { return hoodie; }

    public void setHoodie(int hoodie) { this.hoodie = hoodie; }

    public int getCrop_top() { return crop_top; }

    public void setCrop_top(int crop_top) { this.crop_top = crop_top; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(name);
        dest.writeInt(size);
        dest.writeLong(created.getTime());
        dest.writeInt(pullover);
        dest.writeInt(skirt);
        dest.writeInt(dress);
        dest.writeInt(pants);
        dest.writeInt(shorts);
        dest.writeInt(jumpsuit);
        dest.writeInt(jacket);
        dest.writeInt(shoes);
        dest.writeInt(jewelery);
        dest.writeInt(belt);
        dest.writeInt(shirt);
        dest.writeInt(hoodie);
        dest.writeInt(crop_top);
    }

    public List<Integer> getClothes(){
        List<Integer> clothes = new ArrayList<>();

        clothes.add(getPullover());
        clothes.add(getSkirt());
        clothes.add(getDress());
        clothes.add(getPants());
        clothes.add(getHoodie());
        clothes.add(getBelt());
        clothes.add(getCrop_top());
        clothes.add(getShirt());
        clothes.add(getJumpsuit());
        clothes.add(getJewelery());
        clothes.add(getShorts());
        clothes.add(getJacket());
        clothes.add(getShoes());

        return clothes;
    }
}
