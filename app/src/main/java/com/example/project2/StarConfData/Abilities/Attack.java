package com.example.project2.StarConfData.Abilities;

import com.example.project2.StarConfData.Ships.Ship;

/* Liam Cristescu
 * 3.5.2023
 * I decided to have a little bit of fun with this assignment, all of the implementation is the same, except now the motif
 * is Star Trek ships. The only this that has changed is that how each child of Ships.Ship (was Monster) how has two hp fields:
 * shields, and hull (because every starship needs some shielding). I realize i turned in the first phase of this assignemnt
 * as per the original instructions, I have gone back and retroactively changed this files, and can resubmit them upon
 * request.
 * This is the Abilities.Attack interface. it extends Abilities.Ability and only has one method.*/
public interface Attack extends Ability {
    public Integer attack(Ship target);

}