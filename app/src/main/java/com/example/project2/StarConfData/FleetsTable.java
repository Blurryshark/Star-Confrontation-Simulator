package com.example.project2.StarConfData;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;

@Entity(tableName = AppDataBase.FLEETS_TABLE,
        primaryKeys = {"mUserTableId", "mFleetTableId"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "mUserLogId", childColumns = "mUserTableId"),
                @ForeignKey(entity = Fleet.class, parentColumns = "mFleetId", childColumns = "mFleetTableId"),
                @ForeignKey(entity = Admiral.class, parentColumns = "mAdmiralId", childColumns = "mAdmiralTableId"),
                @ForeignKey(entity = Ship.class, parentColumns = "mShipLogId", childColumns = "mShipTableId")
        })
public class FleetsTable {
    public int mUserTableId;
    public int mFleetTableId;
    public int mAdmiralTableId;
    public int mShipTableId;

    FleetsTable(){}
    public FleetsTable (final int userId, final int fleetIt){
        this.mUserTableId = userId;
        this.mFleetTableId = fleetIt;
    }

    public int getUserTableId() {
        return mUserTableId;
    }

    public void setUserTableId(int userTableId) {
        mUserTableId = userTableId;
    }

    public int getFleetTableId() {
        return mFleetTableId;
    }

    public void setFleetTableId(int fleetTableId) {
        mFleetTableId = fleetTableId;
    }

    public int getAdmiralTableId() {
        return mAdmiralTableId;
    }

    public void setAdmiralTableId(int admiralTableId) {
        mAdmiralTableId = admiralTableId;
    }

    public int getShipTableId() {
        return mShipTableId;
    }

    public void setShipTableId(int shipTableId) {
        mShipTableId = shipTableId;
    }
}
