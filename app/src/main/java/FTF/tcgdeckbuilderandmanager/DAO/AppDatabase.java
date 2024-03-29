package FTF.tcgdeckbuilderandmanager.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import FTF.tcgdeckbuilderandmanager.Card;
import FTF.tcgdeckbuilderandmanager.User;

@Database(entities = {Card.class, User.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "TCGDatabase.db";
    public static final String CARD_TABLE = "card_table";
    public static final String USER_TABLE = "user_table";

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract tcgDAO tcgDAO();

    public static AppDatabase getInstance(Context context) {

        if(instance == null) {
            synchronized (LOCK){
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }

        return instance;

    }



}
