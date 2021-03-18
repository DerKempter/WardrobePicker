package com.bebs.wardrobepicker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOutfit(Outfit outfit);

    @Delete
    void deleteOutfit(Outfit outfit);

    @Update
    void updateOutfit(Outfit outfit);

    @Query("SELECT * FROM outfits ORDER BY created DESC")
    LiveData<List<Outfit>> getAllOutfits();

    @Query("DELETE FROM outfits")
    void deleteAll();

    @Query("SELECT * FROM outfits WHERE name = :name")
    LiveData<List<Outfit>> filterByName(String name);

    @Query("SELECT * FROM outfits WHERE pullover = :pullover")
    LiveData<List<Outfit>> filterByPullover(int pullover);

    @Query("SELECT * FROM outfits WHERE skirt = :skirt")
    LiveData<List<Outfit>> filterBySkirt(int skirt);

    @Query("SELECT * FROM outfits WHERE dress = :dress")
    LiveData<List<Outfit>> filterByDress(int dress);

    @Query("SELECT * FROM outfits WHERE pants = :pants")
    LiveData<List<Outfit>> filterByPants(int pants);

    @Query("SELECT * FROM outfits WHERE shorts = :shorts")
    LiveData<List<Outfit>> filterByShorts(int shorts);

    @Query("SELECT * FROM outfits WHERE jumpsuit = :jumpsuit")
    LiveData<List<Outfit>> filterByJumpsuit(int jumpsuit);

    @Query("SELECT * FROM outfits WHERE jacket = :jacket")
    LiveData<List<Outfit>> filterByJacket(int jacket);

    @Query("SELECT * FROM outfits WHERE shoes = :shoes")
    LiveData<List<Outfit>> filterByShoes(int shoes);

    @Query("SELECT * FROM outfits WHERE jewelery = :jewelery")
    LiveData<List<Outfit>> filterByJewelery(int jewelery);

    @Query("SELECT * FROM outfits WHERE belt = :belt")
    LiveData<List<Outfit>> filterByBelt(int belt);

    @Query("SELECT * FROM outfits WHERE shirt = :shirt")
    LiveData<List<Outfit>> filterByShirt(int shirt);

    @Query("SELECT * FROM outfits WHERE hoodie = :hoodie")
    LiveData<List<Outfit>> filterByHoodie(int hoodie);

    @Query("SELECT * FROM outfits WHERE crop_top = :crop_top")
    LiveData<List<Outfit>> filterByCropTop(int crop_top);
}
