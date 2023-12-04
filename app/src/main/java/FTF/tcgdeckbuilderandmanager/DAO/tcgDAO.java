package FTF.tcgdeckbuilderandmanager.DAO;

import androidx.room.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import FTF.tcgdeckbuilderandmanager.Card;
import FTF.tcgdeckbuilderandmanager.User;

@Dao
public interface tcgDAO {

    @Insert
    void insert(Card... cards);

    @Update
    void update(Card... cards);

    @Delete
    void delete(Card... card);

    @Query("SELECT * FROM " + AppDatabase.CARD_TABLE + " ORDER BY `Set Number`")
    List<Card> getCards();

    @Query("SELECT * FROM " + AppDatabase.CARD_TABLE + " WHERE ID = :cardId")
    List<Card> getCardById(int cardId);

    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User...users);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE ID = :id")
    User getUserByUserID(int id);

}
