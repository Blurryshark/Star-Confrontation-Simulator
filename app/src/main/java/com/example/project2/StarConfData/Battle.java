package com.example.project2.StarConfData;

import android.content.Context;

import androidx.room.Room;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.UserDAO;

import java.util.List;
import java.util.Random;

public class Battle {

    Fleet mBattleFleetOne;
    Fleet mBattleFleetTwo;
    User mLoggedUser;
    List<Ship> mShipListOne;
    List<Ship>mShipListTwo;

    Context mContext;

    FleetsTableDAO mFleetsTableDAO;

    public Battle (User user, Fleet fleetOne, Fleet fleetTwo, Context context){
        mLoggedUser = user;
        mBattleFleetOne = fleetOne;
        mBattleFleetTwo = fleetTwo;
        mContext = context;

        mFleetsTableDAO = Room.databaseBuilder(mContext, AppDataBase.class, AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetsTableDAO();

        mShipListOne = mFleetsTableDAO.getShipsByFleetId(mBattleFleetOne.getFleetId());
        mShipListTwo = mFleetsTableDAO.getShipsByFleetId(mBattleFleetTwo.getFleetId());

    }

    public Battle () {
        Battle star_confrontation = new Battle (null, null, null, null);
    }

    public void Fight(){
        while (continueFight()){
            Random rand = new Random();
            mShipListOne.get(rand.nextInt(3)).attackTarget(mShipListTwo.get(rand.nextInt(3)));
        }
    }

    public boolean continueFight(){
        if (!isDestroyed(mShipListOne)) {
            return false;
        } else if (!isDestroyed(mShipListTwo)){
            return false;
        }
        return true;
    }
    /*This method is going to check and see if all members of a given fleet have been destroyed*/
    public boolean isDestroyed (List<Ship> shipList) {
        for (Ship ship : shipList) {
            if(ship.getHull() <= 0){
                return true;
            }
        }
        return false;
    }
}
