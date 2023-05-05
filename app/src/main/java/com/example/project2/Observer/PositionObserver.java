package com.example.project2.Observer;

public class PositionObserver implements Observer{
    private int position;

    public PositionObserver() {
        super();
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
