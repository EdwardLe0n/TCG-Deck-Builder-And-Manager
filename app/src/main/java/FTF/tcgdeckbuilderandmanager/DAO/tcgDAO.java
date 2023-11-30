package FTF.tcgdeckbuilderandmanager.DAO;

import androidx.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import FTF.tcgdeckbuilderandmanager.Card;

@Dao
public class tcgDAO {

    @Insert
    void insert(tcgDAO... tcgDAOs);


}
