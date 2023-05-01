package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.StarConfData.Ship;

@Dao
public interface StarShipDAO {

    @Insert
    void insert (Ship... ships);

    @Delete
    void delete (Ship... ships);

    @Update
    void update (Ship... ships);
    @Query("SELECT * FROM " + AppDataBase.SHIP_TABLE + " WHERE mShipType = :shipType")
    Ship getShipByShipType (String shipType);

}
