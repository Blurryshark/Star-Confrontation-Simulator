package com.example.project2.StarConfData;

import android.content.Context;

import androidx.room.Room;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {

    Fleet mBattleFleetOne;
    Fleet mBattleFleetTwo;
    User mLoggedUser;
    List<Ship> mShipListOne;
    List<Ship>mShipListTwo;

    Context mContext;

    StarShipDAO mStarShipDAO;

    public Battle (User user, Fleet fleetOne, Fleet fleetTwo, Context context){
        mLoggedUser = user;
        mBattleFleetOne = fleetOne;
        mBattleFleetTwo = fleetTwo;
        mContext = context;

        mStarShipDAO = Room.databaseBuilder(context, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .allowMainThreadQueries()
                .build().StarShipDAO();

        mShipListOne = initList(mBattleFleetOne);
        mShipListTwo = initList(mBattleFleetTwo);
    }

    public Battle () {
        Battle star_confrontation = new Battle (null, null, null, null);
    }

    public static StringBuilder Fight(Battle battle, Fleet fleetOne, Fleet fleetTwo){
        StringBuilder output = new StringBuilder();
        while (continueFight(battle, fleetOne, fleetTwo, output)){
            Random rand = new Random();
            int rand1 = rand.nextInt(3);
            int rand2 = rand.nextInt(3);
            /*This while loop SHOULD keep generating random numbers until we land on a ship that is
            * still alive */
            while(battle.mShipListTwo.get(rand2).getHull() <= 0){
                rand2 = rand.nextInt(3);
            }
            battle.mShipListOne.get(rand1).attackTarget(battle.mShipListTwo.get(rand2), output);

            rand1 = rand.nextInt(3);
            rand2 = rand.nextInt(3);
            while (battle.mShipListOne.get(rand1).getHull() <= 0){
                rand1 = rand.nextInt(3);
            }
            battle.mShipListTwo.get(rand2).attackTarget(battle.mShipListOne.get(rand1), output);
        }
        return output;
    }

    public static boolean continueFight(Battle battle, Fleet fleetOne, Fleet fleetTwo, StringBuilder output){
        if (isDestroyed(battle.mShipListOne)) {
            output.append("\n" + fleetOne.toString() + " is destroyed in the heat of interstellar battle!\n");
            return false;
        } else if (isDestroyed(battle.mShipListTwo)){
            output.append("\n" + fleetTwo.toString() + " is destroyed in the heat of interstellar battle!\n");
            return false;
        }
        return true;
    }
    /*This method is going to check and see if all members of a given fleet have been destroyed*/
    public static boolean isDestroyed (List<Ship> shipList) {
        int wreckCount = 0;
        for (Ship ship : shipList) {
            if(ship.getHull() <= 0){
                wreckCount++;
            }
        }
        return wreckCount == 3;
    }

    public List<Ship> initList (Fleet fleet){
        List<Ship> output = new ArrayList<>();
        for (int i = 0; i < fleet.getFleet().size(); i++){
            output.add(mStarShipDAO.getShipByLogId(fleet.getFleet().get(i)));
            output.get(i).setHull(output.get(i).getMaxHull());
            output.get(i).setShields(output.get(i).getMaxShields());
        }
        return output;
    }

    public Fleet getBattleFleetOne() {
        return mBattleFleetOne;
    }

    public void setBattleFleetOne(Fleet battleFleetOne) {
        mBattleFleetOne = battleFleetOne;
    }

    public Fleet getBattleFleetTwo() {
        return mBattleFleetTwo;
    }

    public void setBattleFleetTwo(Fleet battleFleetTwo) {
        mBattleFleetTwo = battleFleetTwo;
    }
}
