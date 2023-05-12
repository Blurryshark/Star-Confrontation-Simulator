package com.example.project2.Observer;

import android.content.Context;

public class ContextObserver implements Observer{
    private Context mContext;

    public ContextObserver (){
        super();
    }


    @Override
    public void update(int num) {

    }

    @Override
    public void updateContext(Context context) {

    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
