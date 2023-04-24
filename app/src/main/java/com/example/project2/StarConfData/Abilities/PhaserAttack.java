package com.example.project2.StarConfData.Abilities;

import com.example.project2.StarConfData.Ships.Ship;

public class PhaserAttack implements Attack{

    Ship attacker;

    public PhaserAttack(Ship attacker){
        this.attacker = attacker;
    }

    @Override
    public Integer attack(Ship target){

        String message = attacker + " uses a phaser attack on " + target;
        System.out.println(message);
        Integer output = attacker.getStr() - target.getAgi();
        if( output < 0 ){
            return 0;
        }
        /*The damage returned by this attack function is determined by the difference in the attackers strength and the
         * defenders... defense. go figure.*/
        return attacker.getStr() - target.getDef();
    }
}
