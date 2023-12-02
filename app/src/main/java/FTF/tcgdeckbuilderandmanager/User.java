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
    private String name;

    // Each user has their password!
    @NonNull
    @ColumnInfo(name = "Password")
    private String password;

    // Boolean Variable that is used to show a user is an administrator
    @NonNull
    @ColumnInfo(name = "Administrator?")
    private boolean isAdmin;

    public User(@NonNull String name, @NonNull String password, @NonNull boolean isAdmin) {

        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;

    }

    // Standard getters that get used throughout the code

    public String getUserName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    // Quick check to see whether a user has inputted the correct password
    public boolean compPass(String temp) {
        return temp.equals(this.password);
    }



}
