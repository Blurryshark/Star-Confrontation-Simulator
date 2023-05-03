package com.example.project2.DB;

import androidx.room.TypeConverter;

import com.example.project2.StarConfData.Fleet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class Converters {
    @TypeConverter
    public static ArrayList<Fleet> stringToList (String value){
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String listToString(ArrayList<String> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
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
