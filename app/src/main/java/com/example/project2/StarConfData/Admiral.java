package com.example.project2.StarConfData;

import java.util.Random;

public class Admiral {
    private String mAdmiralId;

    public Admiral(){
        returnRandomAdmiral();
    }
    public Admiral(String admiralId) {
        mAdmiralId = admiralId;
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
}
