package com.bebs.wardrobepicker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OutfitRepository {

    private OutfitDao mOutfitDao;
    private LiveData<List<Outfit>> mAllOutfits;

    OutfitRepository(Application application) {
        DatabaseClothing db = DatabaseClothing.getDatabase(application);
        mOutfitDao = db.outfitDao();
        mAllOutfits = mOutfitDao.getAllOutfits();
    }

    LiveData<List<Outfit>> getAllOutfits() {return mAllOutfits;}

    void insert(Outfit outfit){
        DatabaseClothing.databaseWriteExecutor.execute(() -> {
            mOutfitDao.insertOutfit(outfit);
        });
    }

    void updateOutfit(Outfit outfit){
        DatabaseClothing.databaseWriteExecutor.execute(() -> {
            mOutfitDao.updateOutfit(outfit);
        });
    }

    void deleteOutfit(Outfit outfit){
        DatabaseClothing.databaseWriteExecutor.execute(() -> {
            mOutfitDao.deleteOutfit(outfit);
        });
    }

    LiveData<List<Outfit>> filterByName(String name) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByName(name);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByPullover(int pullover) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByPullover(pullover);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterBySkirt(int skirt) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterBySkirt(skirt);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByDress(int dress) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByDress(dress);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByPants(int pants) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByPants(pants);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByShorts(int shorts) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByShorts(shorts);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByJumpsuit(int jumpsuit) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByJumpsuit(jumpsuit);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByShoes(int shoes) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByShoes(shoes);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByJewelery(int jewelery) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByJewelery(jewelery);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByBelt(int belt) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByBelt(belt);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByShirt(int shirt) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByShirt(shirt);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByHoodie(int hoodie) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByHoodie(hoodie);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

    LiveData<List<Outfit>> filterByCropTop(int crop_top) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Outfit>>> callable = new Callable<LiveData<List<Outfit>>>() {
            @Override
            public LiveData<List<Outfit>> call() throws Exception {
                return mOutfitDao.filterByCropTop(crop_top);
            }
        };
        Future<LiveData<List<Outfit>>> future = DatabaseClothing.databaseWriteExecutor.submit(callable);
        return future.get();
    }

}
