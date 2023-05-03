package com.example.project2.StarConfData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;

import java.util.Random;

@Entity(tableName = AppDataBase.ADMIRAL_TABLE)

public class Admiral {
    @PrimaryKey(autoGenerate = true)
    private int logId;

    private String mAdmiralId;
    private int mAdmiralImage;

    public Admiral(){
        returnRandomAdmiral();
    }
    public Admiral(String admiralId, int admiralImage) {
        mAdmiralId = admiralId;
        mAdmiralImage = admiralImage;
    }

    private void returnRandomAdmiral(){
        Random rand = new Random();
        int name = rand.nextInt(5);
        switch (name){
            case 0:
                setAdmiralId("Sisko");
            case 1:
                setAdmiralId("Dukat");
            case 2:
                setAdmiralId("Picard");
            case 3:
                setAdmiralId("Kirk");
            case 4:
                setAdmiralId("Chang");
        }
    }

    public String getAdmiralId() {
        return mAdmiralId;
    }

    public void setAdmiralId(String admiralId) {
        mAdmiralId = admiralId;
    }

    public int getAdmiralImage() {
        return mAdmiralImage;
    }

    public void setAdmiralImage(int admiralImage) {
        mAdmiralImage = admiralImage;
    }

}
