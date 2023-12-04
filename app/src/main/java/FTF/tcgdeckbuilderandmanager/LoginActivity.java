package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;
import FTF.tcgdeckbuilderandmanager.DAO.tcgDAO;

public class LoginActivity extends AppCompatActivity {

    // Edit text fields that are be used to as references to objects that hold
    // user inputs (username/password)
    private EditText mUsernameField;
    private EditText mPasswordField;

    // Buttons that each have on click listeners that bring a user to separate activities
    private Button mLoginToAccount;
    private Button mNewAccount;

    // private variable that will hold the dao for the app
    private tcgDAO mTCGDao;

    // private strings that'll each hold user inputs
    private String mUsername;
    private String mPassword;

    // private user - yippee
    private User mUser;

    // When this activity is created, the activity will set this as the current state
    // In addition, buttons and edit texts will be wired up, as well as a call to the database
    // to get up to date information

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();

        getDatabase();

    }

    // Class that 'wires up the display' by linking variables to those in the xml layout
    private void wireupDisplay() {

        // Edit text linking
        mUsernameField = findViewById(R.id.editTextUserName);
        mPasswordField = findViewById(R.id.editTextPassword);

        // Button linking
        mLoginToAccount = findViewById(R.id.buttonLogin);
        mNewAccount = findViewById(R.id.buttonNewAccount);

        // This code is linked to the button and will run whenever it is pressed
        // When the button is pressed, the code does a couple of things
        mLoginToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // First, it gathers the inputs that the users have inputted
                getValuesFromDisplay();

                // Then it checks if the username is from and actual user in the database
                if (checkForUserInDatabase()) {

                    // Finally, it checks if the password is correct!
                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = LandingActivity.intentFactory(getApplicationContext(), mUser.getId());
                        startActivity(intent);
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "The username is either incorrect, " +
                            "or the account doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // If there are any invalid inputs, the code will let the user know!


        // This code is linked to the button and will run whenever it is pressed
        // Just a quick method that brings the user to a method where they can make an account
        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(intent);

            }
        });
    }

    // Quick method that gets the values off the display and stores them
    private void getValuesFromDisplay(){

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();

    }

    // Checks if an account given a username even exists
    private boolean checkForUserInDatabase() {
        mUser = mTCGDao.getUserByUsername(mUsername);

        if (mUser == null) {
            Toast.makeText(this, "No user with the username " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }

    }

    // Method checks if the password in the database matches the inputted password
    private boolean validatePassword() {
        return mUser.getPassword().equals(mPassword);
    }

    // Gets access to the app database and stores it
    private void getDatabase() {

        mTCGDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tcgDAO();

    }

    // Intent factory!! Only requires context and class bc no info about users is needed yet
    public static Intent intentFactory(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        return intent;

    }


}