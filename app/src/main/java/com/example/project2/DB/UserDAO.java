package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.StarConfData.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getUser();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserLogId=:logId")
    List<User> getUserById(int logId);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUsername=:username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserLogId=:userLogId")
    User getUserByLogId(int userLogId);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mPassword = :password")
    List<User> getUserByPassword(String password);
}

