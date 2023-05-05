package com.example.project2.StarConfData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;

import java.util.Random;

@Entity(tableName = AppDataBase.ADMIRAL_TABLE)

public class Admiral {
    @PrimaryKey(autoGenerate = true)
    private int mAdmiralId;

    private String mAdmiralName;
    private int mAdmiralImage;

    public Admiral() {
        returnRandomAdmiral();
    }

    public Admiral(String admiralId, int admiralImage) {
        mAdmiralName = admiralId;
        mAdmiralImage = admiralImage;
    }

    private void returnRandomAdmiral() {
        Random rand = new Random();
        int name = rand.nextInt(5);
        switch (name) {
            case 0:
                setAdmiralName("Sisko");
            case 1:
                setAdmiralName("Dukat");
            case 2:
                setAdmiralName("Picard");
            case 3:
                setAdmiralName("Kirk");
            case 4:
                setAdmiralName("Chang");
        }
    }


    public int getAdmiralImage() {
        return mAdmiralImage;
    }

    public void setAdmiralImage(int admiralImage) {
        mAdmiralImage = admiralImage;
    }

    public int getAdmiralId() {
        return mAdmiralId;
    }

    public void setAdmiralId(int admiralId) {
        mAdmiralId = admiralId;
    }

    public String getAdmiralName() {
        return mAdmiralName;
    }

    public void setAdmiralName(String admiralName) {
        mAdmiralName = admiralName;
    }
}


