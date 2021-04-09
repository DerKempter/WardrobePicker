package com.bebs.wardrobepicker;

import android.app.Application;
import android.telecom.Call;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClothingRepository {

    private ClothDao mClothDao;
    private LiveData<List<Clothing>> mAllClothes;

    ClothingRepository(Application application) {
        DatabaseClothing db = DatabaseClothing.getDatabase(application);
        mClothDao = db.clothDao();
        mAllClothes = mClothDao.getAllClothes();
    }

    LiveData<List<Clothing>> getAllClothes(){
        return mAllClothes;
    }

    void insert(Clothing clothing){
        DatabaseClothing.databaseWriteExecutor.execute(()->{
            mClothDao.insertClothing(clothing);
        });
    }

    void updateClothing(Clothing clothing) {
        DatabaseClothing.databaseWriteExecutor.execute(() -> {
            mClothDao.updateClothing(clothing);
        });
    }

    void delete(Clothing clothing) {
        DatabaseClothing.databaseWriteExecutor.execute(() -> {
            mClothDao.delete(clothing);
        });
    }

    Clothing getById(int uuid) throws ExecutionException, InterruptedException {
        Callable<Clothing> callable = new Callable<Clothing>() {
            @Override
            public Clothing call() throws Exception {
                return mClothDao.getById(uuid);
            }
        };
        Future<Clothing> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    List<Clothing> getBySeason(ArrayList<Integer> season) throws ExecutionException, InterruptedException {
        Callable<List<Clothing>> callable = new Callable<List<Clothing>>() {
            @Override
            public List<Clothing> call() throws Exception {
                return mClothDao.getBySeason(season);
            }
        };
        Future<List<Clothing>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    List<Clothing> getLaundry(boolean laundry) throws ExecutionException, InterruptedException {
        Callable<List<Clothing>> callable = new Callable<List<Clothing>>() {
            @Override
            public List<Clothing> call() throws Exception {
                return mClothDao.getLaundry(laundry);
            }
        };
        Future<List<Clothing>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    List<Clothing> getByClothing(ArrayList<Integer> typeOfClothing) throws ExecutionException, InterruptedException {
        Callable<List<Clothing>> callable = new Callable<List<Clothing>>() {
            @Override
            public List<Clothing> call() throws Exception {
                return mClothDao.getByClothing(typeOfClothing);
            }
        };
        Future<List<Clothing>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    List<Clothing> getWithFullFilter(ArrayList<Integer> season, boolean laundry, ArrayList<Integer> typeOfClothing) throws ExecutionException, InterruptedException {
        Callable<List<Clothing>> callable = new Callable<List<Clothing>>() {
            @Override
            public List<Clothing> call() throws Exception {
                return mClothDao.getWithFullFilter(season, laundry, typeOfClothing);
            }
        };
        Future<List<Clothing>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    void insertSeason(Season season){
        DatabaseClothing.databaseWriteExecutor.execute(() -> {
            mClothDao.insertSeason(season);
        });
    }

    List<Integer> getSeasonIdWithGivenSeasons(int spring, int summer, int autumn, int winter, int spook) throws ExecutionException, InterruptedException {
        Callable<List<Integer>> callable = new Callable<List<Integer>>() {
            @Override
            public List<Integer> call() throws Exception {
                return mClothDao.getSeasonIdWithGivenSeasons(spring, summer, autumn, winter, spook);
            }
        };
        Future<List<Integer>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    List<Season> getSeasonById(int id) throws ExecutionException, InterruptedException {
        Callable<List<Season>> callable = new Callable<List<Season>>() {
            @Override
            public List<Season> call() throws Exception {
                return mClothDao.getSeasonById(id);
            }
        };
        Future<List<Season>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    List<Season> getAllSeasons() throws ExecutionException, InterruptedException {
        Callable<List<Season>> callable = new Callable<List<Season>>() {
            @Override
            public List<Season> call() throws Exception {
                return mClothDao.getAllSeasons();
            }
        };
        Future<List<Season>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }
}
