package com.example.project2.StarConfData.Ships;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.StarConfData.Abilities.Attack;
import com.example.project2.StarConfData.Abilities.PhaserAttack;
import com.example.project2.StarConfData.Abilities.TorpedoAttack;
import com.example.project2.StarConfData.Admiral;
//import com.example.project2.StarConfData.Abilities.TorpedoAttack;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

@Entity (tableName = AppDataBase.SHIP_TABLE)
public class Ship{

    @PrimaryKey(autoGenerate = true)
    private int logId;

    private String mShipType;
    private Integer shields;
    private Integer hull;
    private Admiral admiral;

/*these are attributes possessed by all ships. Upon creation of a ship, a ship type will be provided
* and will be used to reference a Room DB for the values for the given ship type */
    private Integer agi;
    private Integer def;
    private Integer str;
    private Integer maxShields;
    private Integer maxHull;

    Attack phaserAttack = new PhaserAttack(this);
    Attack torpAttack = new TorpedoAttack(this);
    HashMap<String, Integer> stats = new HashMap<>();

    public Ship (Context context){
        /*makes a random ship object, for the decision averse User*/
        this(makeRandomShip(), context);
    }
    public Ship(String shipType, Context context){
        this.mShipType = shipType;
        /*Instantiating a database so data can pulled to create ship objects*/
        StarShipDAO mStarShipDAO = Room.databaseBuilder(context, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .StarShipDAO();

        /*Pulling values for the required fields to make every ship unique from eachother*/
        this.setAgi(mStarShipDAO.getShipByShipType(shipType).getAgi());
        this.setStr(mStarShipDAO.getShipByShipType(shipType).getStr());
        this.setDef(mStarShipDAO.getShipByShipType(shipType).getDef());
        this.setMaxHull(mStarShipDAO.getShipByShipType(shipType).getMaxHull());
        this.setMaxShields(mStarShipDAO.getShipByShipType(shipType).getMaxShields());
    }

    private static String makeRandomShip(){
        Random rand = new Random();
        int key = rand.nextInt(2);

        switch (key){
            case 0:
                return "Galaxy";
            case 1:
                return "BirdOfPrey";
        }
        return null;
    }

    public Integer attackTarget(Ship target){
        /*
         * Because both elements of this program are ships, I want them both to have access to the torpedo and phaser attack.
         * As such, this bit randomly selects an attack for them to use by generating a random number and checking if it is even
         * or odd, so that at any given time either ship may fire its
         * phasers or its torpedoes
         *
         * As well, I have implemented get attribute here so that agility and strength are more variable. More detail on my thought
         * process is provided at Ship.getAttribute();
         *
         * This implementation was mainly designed for the original implementation of this project, very little
         * should need to change. I think the only things I will need to change are in regards to how the Admirals will
         * affect ship behavior
         */
        Random rand = new Random();
        int atk = rand.nextInt();
        Integer tempStr = this.str;         // Temporary variables so that str and agi can be returned to their default values
        Integer tempAgi = target.agi;       // after every attack. This is necessary to keep the pool of random values consistent
        this.str = getAttribute(getStr(), stats.get("str"));
        target.agi = getAttribute(target.getAgi(), stats.get("agi"));
        Integer dmg;
        if ((atk % 2) != 0){
            dmg = this.phaserAttack.attack(target);
            target.takeDamage(dmg, 1);
        } else {
            dmg = this.torpAttack.attack(target);
            target.takeDamage(dmg, 2);
        }
        this.str = tempStr;                 // returning the stats to their default values
        target.agi = tempAgi;               //
        return dmg;
    }

    boolean takeDamage (Integer damage, int atkType){
        /*
         * I am actually quite proud of this. so the first thing this does is determine whether the damage from the attack
         * being passed in was made by phasers or by torpedoes. This is simply done by the second parameter being passed in.
         * Everybody knows that phasers do extra damage to shields and the torpedoes do extra damage to a ship's hull, so IF
         * the ship still has its shields up, and it is hit by an attack of type: 1 (Phasers), then the incoming damage will \
         * be doubled. If the ship's shields are down, and it is hit by a torpedo, then the incoming damage will be doubled.
         * otherwise, all values are passed normally.
         *
         * This implementation was mainly designed for the original implementation of this project, very little
         * should need to change. I think the only things I will need to change are in regards to how the Admirals will
         * affect ship behavior
         */

        /*This is really just a test case for the implementation of Admirals into the program. Chang evidently has an affinity
        * for Torpedoes /shrug */
        if (this.getAdmiral().getAdmiralId().equals("Chang")){
            atkType = 2;
        }
        if(getShields() >0 && atkType ==1){ //atk 1 means phasers
            damage *= 2;
        } else if (getShields() ==0 && atkType ==2){ // atk 2 means torpedoes
            damage *= 2;
        }
        System.out.println("The ship was hit for " + damage + "!");
        /*
         * The shields act as a layer of protection for the ship's hull, so if the shields are up, all incoming damage will
         * be dealt to the shields FIRST. the shields cannot fall below 0, as this is physcially impossible, so, any incoming
         * that is not blocked by the shields will be dealt directly to the hull, meaning it is possible for the shields to
         * block SOME of an attack, but not all of it (See Star Trek VI: The Undiscovered Country for reference)
         */
        if (getShields() == 0){
            Integer newHull = getHull() - damage;
            setHull(newHull);
        } else {
            /*when the damage is greater than the shields, the remainder hits the hull*/
            if(damage > getShields()){
                Integer newHull = getHull() - (damage - getShields());
                setHull(newHull);
                setShields(0);
            }else {
                Integer newShields = getShields() - damage;
                setShields(newShields);
            }
        }
        //This if statement triggers if the ship is destroyed.
        if (getHull() <= 0){
            System.out.println("Matter/Anti-Matter Containment breach! The ship was destroyed!");
        }

        System.out.println(this.toString());
        return hull > 0;
    }

    /*
     * I have created setters for the str, agi, and def attributes. This is  so that the attributes can be
     * determined every time an attack is launched. the thinking behind this is that every starship rolls off of the assembly
     * line with the same statistics, but in the heat of battle there are momentary fluctuations in power and miscalculations
     * made by the ship's helmsman, so the str and agi of the ship will change from attack to attack. This has the added
     * (in my eyes) benefit of meaning some attacks will hit harder than others.
     */
    Integer getAttribute(Integer min, Integer max){
        Random rand = new Random();
        if(min > max){
            Integer temp = min;
            min = max;
            max = temp;
        }
        return rand.nextInt(max-min) + min;
    }

    /*getters and setters for all required fields*/
    public Integer getShields() {
        return shields;
    }

    public Integer getHull() {
        return hull;
    }

    public Integer getAgi() {
        return agi;
    }

    public Integer getDef() {
        return def;
    }

    public Integer getStr() {
        return str;
    }

    public void setAgi(Integer agi) {
        this.agi = agi;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public void setStr(Integer str) {
        this.str = str;
    }

    public void setMaxShields(Integer maxShields) {
        this.maxShields = maxShields;
    }

    public void setMaxHull(Integer maxHull) {
        this.maxHull = maxHull;
    }

    public Integer getMaxShields() {
        return maxShields;
    }

    public Integer getMaxHull() {
        return maxHull;
    }

    public void setShields(Integer shields) {
        this.shields = shields;
    }

    public void setHull(Integer hull) {
        this.hull = hull;
    }

    public Admiral getAdmiral() {
        return admiral;
    }

    public void setAdmiral(Admiral admiral) {
        this.admiral = admiral;
    }

    /*These methods are overrided from their respective classes. equals() and hashCode() are just the standard implementations
     * provided by the IDE, toString() is custom, just displays the shield value and the hull integrity. I had to shorten it a
     * bunch because it was little too verbose, especially after being printed 50 times in the command line*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return Objects.equals(shields, ship.shields) && Objects.equals(hull, ship.hull) && Objects.equals(maxShields, ship.maxShields) && Objects.equals(maxHull, ship.maxHull);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shields, hull, maxShields, maxHull);
    }

    @Override
    public String toString(){
        return  shields + "/" + maxShields + " shield integrity; " + hull + "/" + maxHull + " hull integrity";
    }
}
