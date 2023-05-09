package com.example.project2.DB;


import static com.example.project2.DB.AppDataBase.ADMIRAL_TABLE;
import static com.example.project2.DB.AppDataBase.FLEETS_TABLE;
import static com.example.project2.DB.AppDataBase.FLEET_TABLE;
import static com.example.project2.DB.AppDataBase.SHIP_TABLE;
import static com.example.project2.DB.AppDataBase.USER_TABLE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Update;

import com.example.project2.StarConfData.Admiral;
import com.example.project2.StarConfData.FleetsTable;
import com.example.project2.StarConfData.Ship;
import com.example.project2.StarConfData.User;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.DB.AppDataBase;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FleetsTableDAO {

    @Insert
    void insert (FleetsTable... FleetsTables);

    @Update
    void update (FleetsTable... fleetsTables);

    @Delete
    void delete (FleetsTable... fleetsTables);

    @Query("SELECT * FROM " + USER_TABLE + " INNER JOIN " + FLEETS_TABLE + " ON mUserLogId=mUserTableId "
            + "WHERE mFleetTableId=:mFleetLogId")
    User getUserFromFleet(final int mFleetLogId);

    @Query ("SELECT * FROM " + FLEET_TABLE + " INNER JOIN " + FLEETS_TABLE + " ON mFleetId=mFleetTableId WHERE "
            + "mUserTableId=:mUserLogId")
    List<Fleet> getFleetFromUser(final int mUserLogId);

    @Query("SELECT * FROM " + ADMIRAL_TABLE + " INNER JOIN " + FLEETS_TABLE + " ON mAdmiralId=mAdmiralTableId WHERE "
            + "mFleetTableId=:mFleetId")
    Admiral getAdmiralFromFleet(final int mFleetId);

    @Query("SELECT * FROM " + SHIP_TABLE + " INNER JOIN " + FLEETS_TABLE + " ON mShipLogId=mShipTableId "
            + "WHERE mFleetTableId=:mFleetId")
    List<Ship> getShipsByFleetId(final int mFleetId);

    @Query("SELECT * FROM " + FLEET_TABLE + " INNER JOIN " + FLEETS_TABLE + " ON mFleetId=mFleetTableId " +
            "WHERE mShipTableId=:mShipLogId")
    List<Fleet> getFleetsByShipType(final int mShipLogId);

}
