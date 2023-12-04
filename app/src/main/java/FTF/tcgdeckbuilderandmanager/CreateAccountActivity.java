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

public class CreateAccountActivity extends AppCompatActivity {

    // Edit text fields that are be used to as references to objects that hold
    // user inputs
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mPasswordAgainField;

    // Buttons that each have on click listeners that bring a user to separate activities
    private Button mCreateAccount;
    private Button mGoBack;

    // private variable that will hold the dao for the app
    private tcgDAO mTCGDao;

    // private strings that'll each hold user inputs
    private String mUsername;
    private String mPassword;
    private String mPasswordAgain;

    // private user - yippee
    private User mUser;


    // When this activity is created, the activity will set this as the current state
    // In addition, buttons and edit texts will be wired up, as well as a call to the database
    // to get up to date information
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireupDisplay();

        getDatabase();

    }

    private void wireupDisplay() {

        // Edit text linking
        mUsernameField = findViewById(R.id.editTextNewAccountUserName);
        mPasswordField = findViewById(R.id.editTextNewAccountPassword);
        mPasswordAgainField = findViewById(R.id.editTextNewAccountPasswordAgain);

        // Button linking
        mCreateAccount = findViewById(R.id.buttonNewAccountCreation);
        mGoBack = findViewById(R.id.buttonBackToLogIn);

        // This code is linked to the button and will run whenever it is pressed
        // When the button is pressed, the code does a couple of things
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // First, it gathers the inputs that the users have inputted
                getValuesFromDisplay();

                // Then it checks if the username is not already in the database
                if (!checkForUserInDatabase()) {

                    // Finally, it checks if the password is valid!

                    if (!validatePassword()) {
                        Toast.makeText(CreateAccountActivity.this, "Make sure password match", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        mUser = new User(mUsername, mPassword, false);
                        mTCGDao.insert(mUser);

                        Intent intent = LoginActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }

                }
                else {
                    Toast.makeText(CreateAccountActivity.this, "A user with this username already exists! Choose a different username!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Alternatively, if the user would like, they can head back to the login page
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);

            }
        });

    }

    // Quick method that gets the values off the display and stores them
    private void getValuesFromDisplay(){

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mPasswordAgain = mPasswordAgainField.getText().toString();

    }

    // Checks if an account given a username even exists
    private boolean checkForUserInDatabase() {
        mUser = mTCGDao.getUserByUsername(mUsername);

        if (mUser == null) {
            return false;
        }
        else {
            return true;
        }

    }

    // Method checks if the password matches the other inputted password
    private boolean validatePassword() {
        return mPassword.equals(mPasswordAgain);
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

        Intent intent = new Intent(context, CreateAccountActivity.class);

        return intent;

    }

}