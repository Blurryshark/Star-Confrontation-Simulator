package com.example.project2.Observer;

import android.content.Context;

public interface Observer {
    public void update(int num);
    public void updateContext(Context context);
}
