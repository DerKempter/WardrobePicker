package com.bebs.wardrobepicker;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import androidx.core.content.res.TypedArrayUtils;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(tableName = "Clothes")
public class Clothing implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "type_of_clothing")
    private int Type;

    @ColumnInfo(name = "clothing_name")
    private String Name;

    @ColumnInfo(name = "description")
    private String Description;

    @ColumnInfo(name = "seasonId")
    private int Season;

    @ColumnInfo(name = "in_laundry")
    private Boolean dirty = false;

    @TypeConverters(Converters.class)
    private ArrayList<String> TypeList;

    public Clothing(int type, String name, int Season){
        this.Type = type;
        this.Name = name;
        this.Season = Season;
        this.populateTypeList();
    }

    public Clothing(int uid, int Type, String Name, String Description, int Season, Boolean dirty, ArrayList<String> TypeList){
        this.uid = uid;
        this.Type = Type;
        this.Name = Name;
        this.Description = Description;
        this.Season = Season;
        this.dirty = dirty;
        this.TypeList = TypeList;
        populateTypeList();
    }


    protected Clothing(Parcel in) {
        uid = in.readInt();
        Type = in.readInt();
        Name = in.readString();
        Description = in.readString();
        int[] ints = {1, 2, 3};
        ArrayList<Integer> intList = new ArrayList<Integer>(ints.length);
        for (int i : ints)
        {
            intList.add(i);
        }
        Season = in.readInt();
        byte tmpDirty = in.readByte();
        dirty = tmpDirty == 0 ? null : tmpDirty == 1;
        TypeList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeInt(Type);
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeInt(Season);
        dest.writeByte((byte) (dirty == null ? 0 : dirty ? 1 : 2));
        dest.writeStringList(TypeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Clothing> CREATOR = new Creator<Clothing>() {
        @Override
        public Clothing createFromParcel(Parcel in) {
            return new Clothing(in);
        }

        @Override
        public Clothing[] newArray(int size) {
            return new Clothing[size];
        }
    };

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setSeason(ArrayList<Integer> Season) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : Season) {
            sb.append(i);
        }
        this.Season = 1;
    }

    public int getSeason() {return this.Season;}

    public ArrayList<Integer> getSeasonList() {
        ArrayList<Integer> res = new ArrayList<>();
        //String[] stringSplit = this.Season.split("");
        //for (String part : stringSplit) {
        //    res.add(Integer.parseInt(part));
        //}
        return res;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTypeString() {
        return this.TypeList.get(this.Type);
    }

    public int getType(){
        return this.Type;
    }

    public void populateTypeList(){
        ArrayList<String> resList = new ArrayList<>();
        String[] tempList = {"Pullover", "Skirt", "Dress", "Pants", "Shorts", "Jumpsuit",
                            "Jacket", "Shoes", "Jewelery", "Belt", "Shirt", "Hoodie", "Crop-Top"};
        resList.addAll(Arrays.asList(tempList));
        this.TypeList = resList;
    }

    public void setTypeList(ArrayList<String> typeList) {
        TypeList = typeList;
    }

    public ArrayList<String> getTypeList() {
        return TypeList;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "uid=" + uid +
                ", Type=" + Type +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", Season=" + Season +
                ", dirty=" + dirty +
                ", TypeList=" + TypeList +
                '}';
    }
}


