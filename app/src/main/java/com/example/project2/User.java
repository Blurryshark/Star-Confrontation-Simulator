package com.example.project2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private String mUsername;

    private String mPassword;
    private boolean mAdminStatus;

    public User(String username, String password, boolean adminStatus) {
        mUsername = username;
        mPassword = password;
        mAdminStatus = adminStatus;
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
