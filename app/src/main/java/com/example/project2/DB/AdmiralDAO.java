package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.StarConfData.Admiral;
import com.example.project2.StarConfData.User;

import java.util.List;

@Dao
public interface AdmiralDAO {

    @Insert
    void insert(Admiral... admirals);

    @Delete
    void delete(Admiral... admirals);

    @Update
    void update(Admiral... admirals);

    @Query("SELECT * FROM " + AppDataBase.ADMIRAL_TABLE)
    List<Admiral> getAdmirals ();
    @Query("SELECT * FROM " + AppDataBase.ADMIRAL_TABLE + " WHERE mAdmiralId=:admiralId")
    Admiral getAdmiralById (int admiralId);
}
