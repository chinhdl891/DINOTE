package com.example.dinote.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dinote.model.Dinote;
import com.example.dinote.model.Tag;
import com.example.dinote.model.TagConverter;
import com.example.dinote.model.TimeRemind;

@Database(entities = {Tag.class, Dinote.class, TimeRemind.class},version = 1)
@TypeConverters(TagConverter.class)
public abstract class DinoteDataBase extends RoomDatabase {
    private static final String DB_NAME = "qr_gen.db";
    private static DinoteDataBase instance;
    public static synchronized DinoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DinoteDataBase.class, DB_NAME)
                    .allowMainThreadQueries().
                            build();
        }
        return instance;
    }
    public abstract TagDAO tagDAO();
    public abstract DinoteDAO dinoteDAO();
    public abstract TimeRemindDAO timeRemindDAO();
}
