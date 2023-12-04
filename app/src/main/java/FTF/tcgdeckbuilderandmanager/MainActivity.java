package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;
import FTF.tcgdeckbuilderandmanager.DAO.tcgDAO;
import FTF.tcgdeckbuilderandmanager.databinding.ActivityMainBinding;

// Refer to this series of tutorials!!!!!:
// https://www.youtube.com/watch?v=vxfYa2r3_vs&list=PLMljn2yeXv0GVyClU5sifWev6YUrXfBgU&index=3

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "FTF.tcgdeckbuilderandmanager.userIdKey";
    private static final String PREFERENCES_KEY = "FTF.tcgdeckbuilderandmanager.preferencesKey";

    ActivityMainBinding binding;

    TextView mMainDisplay;

    // List of edit text variables, switches, and the button for a use to add a card
    EditText mCardName;
    EditText mCardRPSType;
    EditText mCardType;
    EditText mCardSubType;
    EditText mCardPower;
    EditText mCardSlots;
    SwitchCompat mDupesAllowed;
    EditText mSetNum;
    EditText mSetMonNum;
    SwitchCompat mIsLegal;
    EditText mCardEffect;
    EditText mCardDescription;
    Button mCreateCard;

    tcgDAO mTCGDao;

    List<Card> mTCGDaoList;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getDatabase();
        
        checkForUser();
        addUserToPreference(mUserId);
        logInUser(mUserId);

        // Converts the xml file (activity main) into a useable object
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Sets all calls to the objects relative to the binding
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Create a Card");


        // Connects the variables form the xml to these class variables
        mMainDisplay = binding.cardInfoShpeal;
        mCardName = binding.cardNameEditText;
        mCardRPSType = binding.cardRPSEditText;
        mCardType = binding.cardTypeEditText;
        mCardSubType = binding.cardSubTypeEditText;
        mCardPower = binding.cardPowerEditText;
        mCardSlots = binding.cardSlotEditText;
        mDupesAllowed = binding.dupesAllowedSwitch;
        mSetNum = binding.setNumEditText;
        mSetMonNum = binding.setMonNumEditText;
        mIsLegal = binding.isLegalSwitch;
        mCardEffect = binding.cardEffectEditText;
        mCardDescription = binding.cardDescriptionEditText;
        mCreateCard = binding.newCardButton;

        mMainDisplay.setMovementMethod((new ScrollingMovementMethod()));

        mCreateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCard();
            }
        });

    }

    private void logInUser(int userId) {

        mUser = mTCGDao.getUserByUserID(userId);
        invalidateOptionsMenu();

    }

    private void addUserToPreference(int userId) {

        if(mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);

    }

    private void getDatabase() {

        mTCGDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tcgDAO();

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

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    // TODO: FIX LOGIC. IT SHOULD NOT BE THIS STRAIGHT FORWARD
    // i.e. do some checks to see if the new card is even valid
    private void createCard(){

        String name = mCardName.getText().toString();
        int cardRPSType = Integer.parseInt(mCardRPSType.getText().toString());
        int cardType = Integer.parseInt(mCardType.getText().toString());
        int cardSubType = Integer.parseInt(mCardSubType.getText().toString());
        int cardPower = Integer.parseInt(mCardPower.getText().toString());
        int cardSlots = Integer.parseInt(mCardSlots.getText().toString());
        boolean cardDupesAllowed = mDupesAllowed.isChecked();
        int setNum = Integer.parseInt(mSetNum.getText().toString());
        int setMonNum = Integer.parseInt(mSetMonNum.getText().toString());
        boolean isLegal = mIsLegal.isChecked();
        String effect = mCardEffect.getText().toString();
        String description = mCardDescription.getText().toString();

        Card c = new Card(name, cardRPSType, cardType, cardSubType, cardPower, cardSlots,
                          cardDupesAllowed, setNum, setMonNum, isLegal, effect, description);

        mTCGDao.insert(c);

    }

    // TODO: IMPLEMENT
    // Built the method by following one of the profs tutorials
    private void logoutUser () {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    clearUserFromIntent();
                    clearUserFromPref();
                    mUserId = -1;
                }
            });

        alertBuilder.setNegativeButton(getString(R.string.no),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
        });

    }

    private void clearUserFromIntent() {
        addUserToPreference(-1);
    }

    private void clearUserFromPref() {

        if(mPreferences == null) {
            getPrefs();
        }

    }

    public static Intent intentFactory(Context context, int userId) {

        Intent intent = new Intent(context, MainActivity.class);
        
        intent.putExtra(USER_ID_KEY, userId);

        return intent;

    }


}