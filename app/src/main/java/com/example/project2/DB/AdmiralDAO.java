package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.project2.StarConfData.Admiral;
import com.example.project2.StarConfData.User;

@Dao
public interface AdmiralDAO {

    @Insert
    void insert(Admiral... admirals);

    @Delete
    void delete(Admiral... admirals);

    @Update
    void update(Admiral... admirals);

}
