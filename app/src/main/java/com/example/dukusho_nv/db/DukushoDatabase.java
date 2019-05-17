package com.example.dukusho_nv.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dukusho_nv.model.Book;

//@Database(entities = {Book.class}, version = 8)
//public abstract class DukushoDatabase extends RoomDatabase {
//
//    public abstract DukushoDao dukushoDao();
//
//    private static volatile DukushoDatabase INSTANCE;
//
//    public static DukushoDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (DukushoDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            DukushoDatabase.class, "dukusho_database")
//                            .fallbackToDestructiveMigration()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}