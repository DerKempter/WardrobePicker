package com.bebs.wardrobepicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Clothing.class, Outfit.class, Season.class}, version = 6, exportSchema = true)
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
                            DatabaseClothing.class, "clothing_Database").addCallback(sRoomDatabaseCallback).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6).build();
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
            database.execSQL("CREATE TABLE 'season' ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'spring' INTEGER DEFAULT -1, 'summer' INTEGER DEFAULT -1, 'autumn' INTEGER DEFAULT -1, 'winter' INTEGER DEFAULT -1, 'spook' INTEGER DEFAULT -1)");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // database.execSQL("ALTER TABLE 'Clothes' RENAME COLUMN 'season' TO 'seasonId'");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'ClothesSeason' (" +
                    "'uid' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "'clothId' INTEGER NOT NULL, " +
                    "'season' TEXT NOT NULL)");
            database.execSQL("INSERT INTO 'ClothesSeason' ('clothId','season')" +
                    "SELECT uid,season " +
                    "FROM Clothes");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'ClothesNewTemp' (" +
                    "uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
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
            database.execSQL("INSERT INTO 'ClothesNew'(uid,type_of_clothing,clothing_name,description,in_laundry,TypeList) " +
                    "SELECT uid,type_of_clothing,clothing_name,description,in_laundry,TypeList " +
                    "FROM Clothes");
            database.execSQL("DROP TABLE Clothes");
            database.execSQL("ALTER TABLE ClothesNewTemp RENAME TO Clothes");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO 'Clothes' (type_of_clothing,clothing_name,description,seasonId,in_laundry,TypeList) " +
                    "SELECT type_of_clothing,clothing_name,description,type_of_clothing,in_laundry,TypeList " +
                    "FROM ClothesNew");
            Cursor cursor = database.query("SELECT * FROM Clothes");
            Cursor secondCursor = database.query("SELECT * FROM ClothesSeason");
            ArrayList<Season> seasonsToBeAdded = new ArrayList<>();
            while (cursor.moveToNext()) {
                secondCursor.moveToNext();
                int Sid = cursor.getInt(0);
                String seasonString = secondCursor.getString(2);
                Season season = new Season(seasonString);
                int tempId = Sid;
                boolean isInList = false;
                for (Season tempSeason : seasonsToBeAdded) {
                    if (tempSeason.equals(season)) {
                        tempId = seasonsToBeAdded.indexOf(tempSeason);
                        isInList = true;
                        break;
                    }
                }
                if (!isInList) {
                    season.setId(seasonsToBeAdded.size());
                    tempId = season.getId();
                    seasonsToBeAdded.add(season);
                }
                if (seasonsToBeAdded.size() == 0) {seasonsToBeAdded.add(season);}
                
                database.execSQL("UPDATE Clothes SET seasonId = :tempId WHERE uid = :Sid");
            }

            Cursor othererCursor = database.query("SELECT * FROM Clothes");
            ArrayList<Integer> springValues= new ArrayList<>();
            while (othererCursor.moveToNext()) {
                springValues.add(othererCursor.getInt(4));
            }

            Integer id, spring, summer, autumn, winter, spook;

            ContentValues cv = new ContentValues();
            for (Season season : seasonsToBeAdded) {
                cv.clear();
                cv.put("'id'", season.getId());
                cv.put("'spring'", season.getSpring());
                cv.put("'summer'", season.getSummer());
                cv.put("'autumn'", season.getAutumn());
                cv.put("'winter'", season.getWinter());
                cv.put("'spook'", season.getSpook());
                database.insert("Season", OnConflictStrategy.ABORT, cv);
            }
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `SeasonNew` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `spring` INTEGER NOT NULL DEFAULT -1, `summer` INTEGER NOT NULL DEFAULT -1, `autumn` INTEGER NOT NULL DEFAULT -1, `winter` INTEGER NOT NULL DEFAULT -1, `spook` INTEGER NOT NULL DEFAULT -1)");

            Cursor cursor = database.query("SELECT * FROM season");
            ArrayList<Integer> springValues= new ArrayList<>();
            while (cursor.moveToNext()) {
                springValues.add(cursor.getInt(1));
            }

            database.execSQL("INSERT INTO 'SeasonNew'(id,spring,summer,autumn,winter,spook) " +
                    "SELECT id,spring,summer,autumn,winter,spook " +
                    "FROM season");

            database.execSQL("DROP TABLE season");
            database.execSQL("ALTER TABLE SeasonNew RENAME TO Season");
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5,6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE ClothesNew");
            database.execSQL("CREATE TABLE IF NOT EXISTS 'ClothesNew' (" +
                    "uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "type_of_clothing INTEGER NOT NULL, " +
                    "clothing_name TEXT, " +
                    "description TEXT,  " +
                    "seasonId INTEGER NOT NULL, " +
                    "in_laundry INTEGER, " +
                    "imagePath TEXT, " +
                    "TypeList TEXT, " +
                    "FOREIGN KEY('seasonId') REFERENCES 'Season'('id') ON UPDATE NO ACTION ON DELETE CASCADE )");

            database.execSQL("INSERT INTO 'ClothesNew'(type_of_clothing,clothing_name,description,seasonId,in_laundry,TypeList) " +
                    "SELECT type_of_clothing,clothing_name,description,seasonId,in_laundry,TypeList " +
                    "FROM Clothes");
            database.execSQL("DROP TABLE Clothes");
            database.execSQL("ALTER TABLE ClothesNew RENAME TO Clothes");
        }
    };
}
