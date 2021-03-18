package com.bebs.wardrobepicker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Dao
public interface ClothDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertClothing(Clothing clothing);

    @Delete
    void delete(Clothing clothing);

    @Update
    void updateClothing(Clothing clothing);

    @Query("DELETE FROM clothes")
    void deleteAll();

    @Query("SELECT * FROM clothes ORDER BY type_of_clothing ASC")
    LiveData<List<Clothing>> getAllClothes();

    @Query("SELECT * FROM clothes WHERE uid = :uuid ")
    Clothing getById(int uuid);

    @Query("SELECT * FROM clothes WHERE season IN (:season)")
    List<Clothing> getBySeason(ArrayList<String> season);

    @Query("SELECT * FROM clothes WHERE in_laundry = :laundry")
    List<Clothing> getLaundry(boolean laundry);

    @Query("SELECT * FROM clothes WHERE type_of_clothing IN (:typeOfClothing)")
    List<Clothing> getByClothing(ArrayList<Integer> typeOfClothing);

    @Query("SELECT * FROM clothes WHERE season IN (:season) AND in_laundry = :laundry AND type_of_clothing IN (:typeOfClothing)")
    List<Clothing> getWithFullFilter(ArrayList<String> season, boolean laundry, ArrayList<Integer> typeOfClothing);

}
