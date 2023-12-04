package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;
import FTF.tcgdeckbuilderandmanager.DAO.tcgDAO;

public class LandingActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "FTF.tcgdeckbuilderandmanager.userIdKey";
    private static final String PREFERENCES_KEY = "FTF.tcgdeckbuilderandmanager.preferencesKey";

    private TextView mLandingText;
    private Button mCardsButton;
    private Button mDecksButton;
    private Button mAccountButton;
    private Button mBackButton;

    private tcgDAO mTCGDao;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getDatabase();

        checkForUser();
        addUserToPreference(mUserId);
        logInUser(mUserId);

        wireUpDisplay();

        // Refreshes the display to show the welcome text!
        refreshDisplay();

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
            User defaultUser = new User("Og", "12345", false);
            mTCGDao.insert(defaultUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);

    }

    private void logInUser(int userId) {

        mUser = mTCGDao.getUserByUserID(userId);
        invalidateOptionsMenu();

    }

    private void wireUpDisplay() {

        getSupportActionBar().setTitle("Welcome to the landing page!");

        mLandingText = findViewById(R.id.textViewLandingPage);
        mCardsButton = findViewById(R.id.viewCardsButton);
        mDecksButton = findViewById(R.id.viewDecksButton);
        mAccountButton = findViewById(R.id.viewAccountsButton);
        mBackButton = findViewById(R.id.landingBackButton);

    }

    // Todo: FIX
    private void refreshDisplay() {

        String temp = "ruh roh";

        if (mUser.getIsAdmin()) {
            temp = "Welcome admin " + mUser.getUsername() + " ! \nI hope you're having a good day" +
                    "\nWhat would you like to do today";
        }
        else {

        }

        mLandingText.setText(temp);

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

        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra(USER_ID_KEY, userId);

        return intent;

    }

}