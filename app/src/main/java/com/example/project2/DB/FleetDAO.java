package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.StarConfData.Fleet;

import java.util.List;

@Dao
public interface FleetDAO {

    @Insert
    void insert (Fleet... fleets);

    @Delete
    void delete (Fleet... fleets);

    @Update
    void update(Fleet... fleets);

    @Query("SELECT * FROM " + AppDataBase.FLEET_TABLE + " WHERE mFleetName = :fleetName")
    void getFleetByFleetName (String fleetName);

    @Query("SELECT * FROM " + AppDataBase.FLEET_TABLE + " ORDER BY mFleetName desc")
    List<Fleet> getFleets ();
}