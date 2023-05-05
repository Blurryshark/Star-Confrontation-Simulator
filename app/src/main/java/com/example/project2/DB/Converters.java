package com.example.project2.DB;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.project2.StarConfData.Fleet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Converters {
    @TypeConverter
    public static List<Integer> stringToList (String value){
        if(value == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String arrayListToString(ArrayList<Integer> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static ArrayList<Integer> stringToArrayList (String value){
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String listToString(List<Integer> list){
        Gson gson = new Gson();
        return gson.toJson(list);
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

    @TypeConverter
    public int integerToInt(Integer num){return num.intValue();}
    @TypeConverter
    public Integer intToInteger(int num){
        return new Integer(num);
    }
}
