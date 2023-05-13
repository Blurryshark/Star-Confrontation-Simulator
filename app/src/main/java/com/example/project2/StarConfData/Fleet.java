package com.example.project2.StarConfData;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.TypeConverters;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.Converters;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.R;

import java.util.ArrayList;
import java.util.List;

@Entity (tableName = AppDataBase.FLEET_TABLE)
public class Fleet {
    @PrimaryKey(autoGenerate = true)
    private int mFleetId;

    private List<Integer> mFleetIdList;
    private int mAdmiralId;
    private String mFleetName;
    private int mOwnerId;
    private int fleetImage;
    public static final int MAX_FLEET_SIZE = 3;
    public Fleet(){
        mFleetIdList = new ArrayList<>();
        mAdmiralId = 0;
    }

    public Fleet(List<Integer> ships, int admiral, String fleetName, int ownerId, Context context){
        this.mFleetIdList = ships;
        this.mAdmiralId = admiral;
        this.mFleetName = fleetName;
        this.fleetImage = R.drawable.starfleetbadge;
        this.mOwnerId = ownerId;

        FleetDAO mFleetDAO = Room.databaseBuilder(context, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetDAO();
        mFleetDAO.insert(this);
    }

    public int getFleetId() {
        return mFleetId;
    }

    public List<Integer> getFleetIdList() {
        return mFleetIdList;
    }

    public void setFleetIdList(List<Integer> fleetIdList) {
        mFleetIdList = fleetIdList;
    }

    public int getAdmiralId() {
        return mAdmiralId;
    }

    public void setAdmiralId(int admiralId) {
        mAdmiralId = admiralId;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(int ownerId) {
        mOwnerId = ownerId;
    }

    public void setFleetId(int fleetId) {
        mFleetId = fleetId;
    }

    public List<Integer> getFleet() {
        return mFleetIdList;
    }

    public String getFleetName() {
        return mFleetName;
    }

    public void setFleetName(String fleetName) {
        mFleetName = fleetName;
    }

    public void setFleet(List<Integer> fleet) {
        this.mFleetIdList = fleet;
    }

    public int getAdmiral() {
        return mAdmiralId;
    }

    public void setAdmiral(Admiral admiral) {
        mAdmiralId = admiral.getAdmiralId();
    }

    public int getLogId() {
        return mFleetId;
    }

    public void setLogId(int logId) {
        mFleetId = logId;
    }

    public int getFleetImage() {
        return fleetImage;
    }

    public void setFleetImage(int fleetImage) {
        this.fleetImage = fleetImage;
    }

    public void addShipToFleet(Ship newShip){
        if(mFleetIdList.size() < MAX_FLEET_SIZE){
            mFleetIdList.add(newShip.getShipLogId());
            System.out.println("Ship added to the fleet!");
            if(this.mAdmiralId != 0){
                newShip.setAdmiralId(mAdmiralId);
            }
        }
    }


    @Override
    public String toString() {
        return mFleetName + ": " + mFleetIdList.size() + " ships; "
                + mAdmiralId + " commanding";
    }
}
