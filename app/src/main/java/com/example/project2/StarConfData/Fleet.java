package com.example.project2.StarConfData;

import com.example.project2.StarConfData.Ships.Ship;

import java.util.ArrayList;

public class Fleet {
    private ArrayList<Ship> fleet;
    private Admiral mAdmiral;
    public static final int MAX_FLEET_SIZE = 3;

    public Fleet(){
        fleet = new ArrayList<>();
        mAdmiral = null;
    }

    public Fleet(ArrayList<Ship> ships, Admiral admiral){
        this.fleet = ships;
        this.mAdmiral = admiral;
    }

    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }

    public Admiral getAdmiral() {
        return mAdmiral;
    }

    public void setAdmiral(Admiral admiral) {
        mAdmiral = admiral;
    }

    public void addShipToFleet(Ship newShip){
        if(fleet.size() < MAX_FLEET_SIZE){
            fleet.add(newShip);
            System.out.println("Ship added to the fleet!");
            if(this.mAdmiral != null){
                newShip.setAdmiral(mAdmiral);
            }
        }
    }
}
