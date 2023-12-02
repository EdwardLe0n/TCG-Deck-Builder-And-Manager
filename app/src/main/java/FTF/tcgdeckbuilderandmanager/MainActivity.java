package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Converts the xml file (activity main) into a useable object
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Sets all calls to the objects relative to the binding
        setContentView(binding.getRoot());


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

        mTCGDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tcgDAO();

        refreshDisplay();

        mCreateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCard();
                refreshDisplay();
            }
        });

    }

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

    private void refreshDisplay() {

        mTCGDaoList = mTCGDao.getCards();
        if (! mTCGDaoList.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            for (Card c: mTCGDaoList) {
                sb.append(c.toString());
            }
            mMainDisplay.setText(sb.toString());

        }
        else {
            mMainDisplay.setText(R.string.no_cards_message);
        }

    }


}