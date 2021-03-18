package com.bebs.wardrobepicker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Clothing.class, Outfit.class, Season.class}, version = 4, exportSchema = true)
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
                            DatabaseClothing.class, "clothing_Database").addCallback(sRoomDatabaseCallback).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build();
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

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'season' ('id' INTEGER NOT NULL, 'spring' INTEGER NOT NULL, 'summer' INTEGER NOT NULL, 'autumn' INTEGER NOT NULL, 'winter' INTEGER NOT NULL, 'spook' INTEGER NOT NULL, PRIMARY KEY('id'))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'Clothes' RENAME COLUMN 'season' TO 'seasonId'");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'ClothesSeason' (" +
                    "'uid' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "'clothId' INTEGER NOT NULL, " +
                    "'season' TEXT NOT NULL)");
            database.execSQL("INSERT INTO 'ClothesSeason' ('clothId','season')" +
                    "SELECT uid,'seasonId' " +
                    "FROM Clothes");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'ClothesNewTemp' (" +
                    "'id'INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "type_of_clothing INTEGER NOT NULL, " +
                    "clothing_name TEXT, " +
                    "description TEXT,  " +
                    "seasonId INTEGER NOT NULL, " +
                    "in_laundry INTEGER, " +
                    "TypeList TEXT)");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'ClothesNew' (" +
                    "uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "type_of_clothing INTEGER NOT NULL, " +
                    "clothing_name TEXT, " +
                    "description TEXT,  " +
                    "seasonId INTEGER, " +
                    "in_laundry INTEGER, " +
                    "TypeList TEXT, " +
                    "FOREIGN KEY('seasonId') REFERENCES 'Season'('id') ON UPDATE NO ACTION ON DELETE CASCADE )");
            database.execSQL("INSERT INTO 'ClothesNew' ('type_of_clothing','clothing_name','description','in_laundry','TypeList') " +
                    "SELECT type_of_clothing,clothing_name,description,in_laundry,TypeList" +
                    "FROM Clothes");
            database.execSQL("DROP TABLE Clothes");
            database.execSQL("ALTER TABLE ClothesNewTemp RENAME TO Clothes");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO 'Clothes' (type_of_clothing,clothing_name,description,seasonId,in_laundry,TypeList) " +
                    "SELECT type_of_clothing,clothing_name,description,seasonId,in_laundry,TypeList " +
                    "FROM ClothesNew");
            database.execSQL("UPDATE Clothes " +
                    "SET seasonId = (SELECT SeasonId FROM ClothesSeason WHERE Clothes.uid = ClothesSeason.clothId)");

        }
    };
}
