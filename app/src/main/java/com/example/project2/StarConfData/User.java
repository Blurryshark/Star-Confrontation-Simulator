package com.example.project2.StarConfData;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;
import com.example.project2.StarConfData.Fleet;

import java.util.ArrayList;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    public int mUserLogId;

    private String mUsername;
    private String mPassword;
    private boolean mAdminStatus;
    private int mFleetIdArray;

    public User(String username, String password, boolean adminStatus) {
        mUsername = username;
        mPassword = password;
        mAdminStatus = adminStatus;
    }

    public int getLogId() {
        return mUserLogId;
    }

    public void setLogId(int logId) {
        mUserLogId = logId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdminStatus() {
        return mAdminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        mAdminStatus = adminStatus;
    }

    public int getUserLogId() {
        return mUserLogId;
    }

    public void setUserLogId(int userLogId) {
        mUserLogId = userLogId;
    }

    public int getFleetIdArray() {
        return mFleetIdArray;
    }

    public void setFleetIdArray(int fleetIdArray) {
        mFleetIdArray = fleetIdArray;
    }
}
