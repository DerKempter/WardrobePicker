package com.bebs.wardrobepicker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Clothing.class, Outfit.class}, version = 1, exportSchema = false)
public abstract class DatabaseClothing extends RoomDatabase {

    public abstract ClothDao clothDao();
    public abstract OutfitDao outfitDao();

    private static volatile DatabaseClothing INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DatabaseClothing getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DatabaseClothing.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseClothing.class, "clothing_Database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ClothDao clothDao = INSTANCE.clothDao();
                clothDao.deleteAll();
                OutfitDao outfitDao = INSTANCE.outfitDao();
                outfitDao.deleteAll();
            });
        }
    };
}
