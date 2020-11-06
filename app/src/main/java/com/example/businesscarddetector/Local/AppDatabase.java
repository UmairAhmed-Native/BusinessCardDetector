package com.example.businesscarddetector.Local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.businesscarddetector.Model.ContactModel;

@Database(entities = ContactModel.class, exportSchema = false, version = DbConstants.dBVersion)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase _appDatabaseInstance;

    public abstract ContactDao contactDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (_appDatabaseInstance == null) {
            _appDatabaseInstance = Room.databaseBuilder(context, AppDatabase.class, DbConstants.dbName)
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return _appDatabaseInstance;
    }
}

