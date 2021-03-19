package com.example.machinetest.ROOM;

import com.example.qnopytestapp.APIResponse.FormData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RoomDao {

    @Insert
    public void InsertData(FormData formData);

    @Query("select * from FormData")
    public List<FormData> getFormData();


}
