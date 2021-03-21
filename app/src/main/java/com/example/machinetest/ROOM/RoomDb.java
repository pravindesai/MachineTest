package com.example.machinetest.ROOM;

import android.content.Context;

import com.example.qnopytestapp.APIResponse.FormData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FormData.class}, version = 1, exportSchema = true)
public abstract class RoomDb extends RoomDatabase {

    private static RoomDb db = null;

    public static RoomDb getInstance(Context context){
        if (db == null)
            db = Room.databaseBuilder(context, RoomDb.class, "myDB")
                    .allowMainThreadQueries().build();
        return db;

    }

    public abstract RoomDao roomDao();

}
