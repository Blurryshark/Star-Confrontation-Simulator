package com.example.project2.StarConfData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;
import com.example.project2.StarConfData.Ships.Ship;

import java.util.ArrayList;

@Entity (tableName = AppDataBase.FLEET_TABLE)
public class Fleet {
    @PrimaryKey
    private int mLogId;

    private ArrayList<Ship> mFleet;
    private Admiral mAdmiral;
    private String mFleetName;
    public static final int MAX_FLEET_SIZE = 3;
    public Fleet(){
        mFleet = new ArrayList<>();
        mAdmiral = null;
    }

    public Fleet(ArrayList<Ship> ships, Admiral admiral){
        this.mFleet = ships;
        this.mAdmiral = admiral;
    }

    public ArrayList<Ship> getFleet() {
        return mFleet;
    }

    public String getFleetName() {
        return mFleetName;
    }

    public void setFleetName(String fleetName) {
        mFleetName = fleetName;
    }

    public void setFleet(ArrayList<Ship> fleet) {
        this.mFleet = fleet;
    }

    public Admiral getAdmiral() {
        return mAdmiral;
    }

    public void setAdmiral(Admiral admiral) {
        mAdmiral = admiral;
    }

    public void addShipToFleet(Ship newShip){
        if(mFleet.size() < MAX_FLEET_SIZE){
            mFleet.add(newShip);
            System.out.println("Ship added to the fleet!");
            if(this.mAdmiral != null){
                newShip.setAdmiral(mAdmiral);
            }
        }
    }

    @Override
    public String toString() {
        return mFleetName + ": " + mFleet.size() + " ships; "
                + mAdmiral.getAdmiralId() + " commanding";
    }
}
