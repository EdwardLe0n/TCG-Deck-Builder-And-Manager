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

    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mPasswordAgainField;

    private Button mCreateAccount;

    private Button mGoBack;

    private tcgDAO mTCGDao;

    private String mUsername;
    private String mPassword;
    private String mPasswordAgain;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireupDisplay();

        getDatabase();

    }

    private void wireupDisplay() {

        mUsernameField = findViewById(R.id.editTextNewAccountUserName);
        mPasswordField = findViewById(R.id.editTextNewAccountPassword);
        mPasswordAgainField = findViewById(R.id.editTextNewAccountPasswordAgain);

        mCreateAccount = findViewById(R.id.buttonNewAccountCreation);
        mGoBack = findViewById(R.id.buttonBackToLogIn);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValuesFromDisplay();

                if (!checkForUserInDatabase()) {

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

        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);

            }
        });

    }

    private void getValuesFromDisplay(){

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mPasswordAgain = mPasswordAgainField.getText().toString();

    }

    private boolean checkForUserInDatabase() {
        mUser = mTCGDao.getUserByUsername(mUsername);

        if (mUser == null) {
            return false;
        }
        else {
            return true;
        }

    }

    private boolean validatePassword() {
        return mPassword.equals(mPasswordAgain);
    }

    private void getDatabase() {

        mTCGDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tcgDAO();

    }

    public static Intent intentFactory(Context context) {

        Intent intent = new Intent(context, CreateAccountActivity.class);

        return intent;

    }

}