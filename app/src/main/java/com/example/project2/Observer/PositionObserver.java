package com.example.project2.Observer;

import android.content.Context;

public class PositionObserver implements Observer{
    private int position;

    public PositionObserver() {
        super();
    }

    public void updateContext(Context context) {

    }

    @Override
    public void update(int num) {

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
