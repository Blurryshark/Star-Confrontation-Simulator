package com.example.project2.StarConfData.Abilities;

import com.example.project2.StarConfData.Ships.Ship;

public class TorpedoAttack implements Attack{
    Ship attacker;

    public TorpedoAttack(Ship attacker){
        this.attacker = attacker;
    }

    @Override
    public Integer attack(Ship target){
        String message = attacker + " uses a torpedo attack on " + target;
        System.out.println(message);
        Integer output = attacker.getStr() - target.getAgi();
        if( output < 0 ){
            return 0;
        }
        /*The damage returned by this attack function is determined by the difference in the attackers strength and the
         * defender's agility. This is because torpedoes are slow moving and can potentially be evaded. in a more complete version
         * of this program I would write a function for evasion to reduce the damage caused by the torpedo, and for the
         * remaining damage to be reduced by the defense, but for now this is how it is.*/
        return attacker.getStr() - target.getAgi();
    }
}

