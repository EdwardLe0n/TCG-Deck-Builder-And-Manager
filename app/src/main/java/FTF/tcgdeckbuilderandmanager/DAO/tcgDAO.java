package FTF.tcgdeckbuilderandmanager.DAO;

import androidx.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import FTF.tcgdeckbuilderandmanager.Card;

@Dao
public interface tcgDAO {

    @Insert
    void insert(Card... cards);

    @Update
    void update(Card... cards);

    @Delete
    void delete(Card... card);

    @Query("SELECT * FROM " + AppDatabase.CARD_TABLE)
    List<Card> getCards();

    @Query("SELECT * FROM " + AppDatabase.CARD_TABLE + " WHERE ID = :cardId")
    List<Card> getLogById(int cardId);

}
