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
            int rand1 = rand.nextInt(3);
            int rand2 = rand.nextInt(3);
            /*This while loop SHOULD keep generating random numbers until we land on a ship that is
            * still alive */
            while(mShipListTwo.get(rand2).getHull() <= 0){
                rand2 = rand.nextInt(3);
            }
            mShipListOne.get(rand1).attackTarget(mShipListTwo.get(rand2));

            rand1 = rand.nextInt(3);
            rand2 = rand.nextInt(3);
            while (mShipListOne.get(rand1).getHull() <= 0){
                rand1 = rand.nextInt(3);
            }
            mShipListTwo.get(rand2).attackTarget(mShipListOne.get(rand1));
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
