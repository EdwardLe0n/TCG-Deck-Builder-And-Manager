package FTF.tcgdeckbuilderandmanager;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;

@Entity(tableName = AppDatabase.CARD_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)



}
