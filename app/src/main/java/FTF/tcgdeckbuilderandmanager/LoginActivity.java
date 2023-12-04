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

    private EditText mUsernameField;
    private EditText mPasswordField;

    private Button mLoginToAccount;
    private Button mNewAccount;

    private tcgDAO mTCGDao;

    private String mUsername;
    private String mPassword;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();

        getDatabase();

    }

    private void wireupDisplay() {

        mUsernameField = findViewById(R.id.editTextUserName);
        mPasswordField = findViewById(R.id.editTextPassword);

        mLoginToAccount = findViewById(R.id.buttonLogin);
        mNewAccount = findViewById(R.id.buttonNewAccount);

        mLoginToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValuesFromDisplay();

                if (checkForUserInDatabase()) {

                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getId());
                        startActivity(intent);
                    }

                }

            }
        });

        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(intent);

            }
        });

    }

    private boolean validatePassword() {
        return mUser.getPassword().equals(mPassword);
    }

    private void getValuesFromDisplay(){

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();

    }

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

    private void getDatabase() {

        mTCGDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tcgDAO();

    }

    public static Intent intentFactory(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        return intent;

    }


}