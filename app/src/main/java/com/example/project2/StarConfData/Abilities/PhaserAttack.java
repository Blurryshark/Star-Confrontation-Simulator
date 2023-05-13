package com.example.project2.StarConfData.Abilities;

import com.example.project2.StarConfData.Ship;

public class PhaserAttack implements Attack{

    Ship attacker;

    public PhaserAttack(Ship attacker){
        this.attacker = attacker;
    }

    @Override
    public Integer attack(Ship target, StringBuilder output){

        output.append(attacker.getShipType() + " uses a phaser attack on " + target.getShipType() + "\n");
        Integer result = attacker.getStr() - attacker.getDef();
        /*The damage returned by this attack function is determined by the difference in the attackers strength and the
         * defenders... defense. go figure.*/
        if(result < 0){
            return 0;
        }
        return 0;
    }
}
