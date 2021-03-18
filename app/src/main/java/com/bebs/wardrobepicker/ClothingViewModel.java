package com.bebs.wardrobepicker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClothingViewModel extends AndroidViewModel {

    private ClothingRepository mClothingRepository;
    private OutfitRepository mOutfitRepository;

    private final LiveData<List<Clothing>> mAllClothes;
    private final LiveData<List<Outfit>> mAllOutfits;

    public ClothingViewModel (Application application) {
        super(application);
        mClothingRepository = new ClothingRepository(application);
        mOutfitRepository = new OutfitRepository(application);
        mAllClothes = mClothingRepository.getAllClothes();
        mAllOutfits = mOutfitRepository.getAllOutfits();
    }



    public LiveData<List<Clothing>> getAllClothes(){ return mAllClothes; }

    public void insert(Clothing clothing) { mClothingRepository.insert(clothing); }

    public void updateClothing(Clothing clothing) { mClothingRepository.updateClothing(clothing);}

    public void deleteClothing(Clothing clothing) {mClothingRepository.delete(clothing);}

    public Clothing getById(int uuid) throws ExecutionException, InterruptedException {return mClothingRepository.getById(uuid);}

    public List<Clothing> getBySeason(ArrayList<String> season) throws ExecutionException, InterruptedException {return mClothingRepository.getBySeason(season);}

    public List<Clothing> getLaundry(boolean laundry) throws ExecutionException, InterruptedException {return mClothingRepository.getLaundry(laundry);}

    public List<Clothing> getByClothing(ArrayList<Integer> typeOfClothing) throws ExecutionException, InterruptedException {return mClothingRepository.getByClothing(typeOfClothing);}

    public List<Clothing> getWithFullFilter(ArrayList<String> season, boolean laundry, ArrayList<Integer> typeOfClothing) throws ExecutionException, InterruptedException {return mClothingRepository.getWithFullFilter(season, laundry, typeOfClothing);}

    public void insertSeason(Season season) {mClothingRepository.insertSeason(season);}



    public LiveData<List<Outfit>> getAllOutfits() { return mAllOutfits; }

    public void insert(Outfit outfit) {mOutfitRepository.insert(outfit);}

    public void updateOutfit(Outfit outfit) {mOutfitRepository.updateOutfit(outfit);}

    public void deleteOutfit(Outfit outfit) {mOutfitRepository.deleteOutfit(outfit);}

    public LiveData<List<Outfit>> filterByName(String name) throws ExecutionException, InterruptedException {return mOutfitRepository.filterByName(name);}

    public LiveData<List<Outfit>> filterByPullover(int pullover) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByPullover(pullover);}

    public LiveData<List<Outfit>> filterBySkirt(int skirt) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterBySkirt(skirt);}

    public LiveData<List<Outfit>> filterByDress(int dress) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByDress(dress);}

    public LiveData<List<Outfit>> filterByPants(int pants) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByPants(pants);}

    public LiveData<List<Outfit>> filterByShorts(int shorts) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByShorts(shorts);}

    public LiveData<List<Outfit>> filterByJumpsuit(int jumpsuit) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByJumpsuit(jumpsuit);}

    public LiveData<List<Outfit>> filterByShoes(int shoes) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByShoes(shoes);}

    public LiveData<List<Outfit>> filterByJewelery(int jewelery) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByJewelery(jewelery);}

    public LiveData<List<Outfit>> filterByBelt(int belt) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByBelt(belt);}

    public LiveData<List<Outfit>> filterByShirt(int shirt) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByShirt(shirt);}

    public LiveData<List<Outfit>> filterByHoodie(int hoodie) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByHoodie(hoodie);}

    public LiveData<List<Outfit>> filterByCropTop(int crop_top) throws  ExecutionException, InterruptedException {return mOutfitRepository.filterByCropTop(crop_top);}
}
