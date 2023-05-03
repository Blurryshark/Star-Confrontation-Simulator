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
    private int mLogId;

    private String mUsername;
    private String mPassword;
    private boolean mAdminStatus;
    @Ignore
    private ArrayList<Fleet> mFleets;

    public User(String username, String password, boolean adminStatus) {
        mUsername = username;
        mPassword = password;
        mAdminStatus = adminStatus;
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int logId) {
        mLogId = logId;
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
}
