package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;
import FTF.tcgdeckbuilderandmanager.DAO.tcgDAO;

public class NormieCardsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "FTF.tcgdeckbuilderandmanager.userIdKey";
    private static final String PREFERENCES_KEY = "FTF.tcgdeckbuilderandmanager.preferencesKey";

    private Button mBackToLand;
    private TextView mCardList;

    private tcgDAO mTCGDao;

    // private user - yippee
    private User mUser;
    private List<Card> mAllCards;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normie_cards);

        getDatabase();

        checkForUser();
        addUserToPreference(mUserId);
        logInUser(mUserId);

        wireUpDisplay();

        // Refreshes the display to show the welcome text!
        refreshDisplay();

    }

    private void refreshDisplay() {

        mAllCards = mTCGDao.getCards();

        if (! mAllCards.isEmpty()) {

            StringBuilder sb = new StringBuilder();

            for (Card c: mAllCards) {
                sb.append(c.toString());
            }
            mCardList.setText(sb.toString());
        }
        else {
            mCardList.setText(R.string.no_cards_message);
        }

    }

    // We're so back
    private void wireUpDisplay() {

        mBackToLand = findViewById(R.id.buttonNormieCardsBack);
        mCardList = findViewById(R.id.normieCardList);

        mBackToLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingActivity.intentFactory((getApplicationContext()), mUserId);
                startActivity(intent);
            }
        });

    }

    private void checkForUser() {

        // do we have a user in the intent?

        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // do we have a user in the preferences

        if(mUserId != -1) {
            return;
        }

        if(mPreferences == null) {
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }

        // do we have any users at all?

        List<User> users = mTCGDao.getAllUsers();

        if(users.size() <= 0) {
            User defaultUser = new User("Og", "12345", true);
            mTCGDao.insert(defaultUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);

    }

    private void logInUser(int userId) {

        mUser = mTCGDao.getUserByUserID(userId);
        invalidateOptionsMenu();

    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {

        if(mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);

    }

    // Gets access to the app database and stores it
    private void getDatabase() {

        mTCGDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tcgDAO();

    }

    public static Intent intentFactory(Context context, int userId) {

        Intent intent = new Intent(context, NormieCardsActivity.class);

        intent.putExtra(USER_ID_KEY, userId);

        return intent;

    }

}