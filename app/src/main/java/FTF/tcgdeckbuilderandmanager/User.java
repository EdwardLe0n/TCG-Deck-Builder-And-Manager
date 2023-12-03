package FTF.tcgdeckbuilderandmanager;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    // Primary key is used to help look through the list of users
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    // Each user has a username
    @NonNull
    @ColumnInfo(name = "Username")
    private String username;

    // Each user has their password!
    @NonNull
    @ColumnInfo(name = "Password")
    private String password;

    // Boolean Variable that is used to show a user is an administrator
    @NonNull
    @ColumnInfo(name = "Administrator?")
    private boolean isAdmin;

    public User(@NonNull String username, @NonNull String password, boolean isAdmin) {

        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;

    }


    // Standard getters that get used throughout the code

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Quick check to see whether a user has inputted the correct password
    public boolean compPass(String temp) {
        return temp.equals(this.password);
    }



}
