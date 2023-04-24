package com.example.project2.DB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2.User;

@Database(entities = {User.class}, version = 1)
@TypeConverters({DataTypeConverter.class})
public abstract class AppDataBase extends RoomDatabase {
    public static final String USER_DATABASE_NAME = "UserDAO.db";
    public static final String USER_TABLE = "User_table";
    public static final String SHIP_TABLE = "Ship_table";
    public static final String SHIP_DATABASE_NAME = "ShipDAO.db";

    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();

    public abstract UserDAO UserDAO();

    public static AppDataBase getUserDbInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            USER_DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
    public static AppDataBase getShipDbInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            SHIP_DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
