package com.example.project2.DB;

import androidx.room.TypeConverter;

public class DataTypeConverter {

    @TypeConverter
    public boolean convertIntToBoolean(int in){
        return in == 1;
    }
    @TypeConverter
    public int convertBooleanToInt(Boolean bool){
        if (bool == true){
            return 1;
        } else {return 0;}
    }
}