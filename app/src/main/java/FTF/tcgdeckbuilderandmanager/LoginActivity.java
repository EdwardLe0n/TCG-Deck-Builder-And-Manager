package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;
import FTF.tcgdeckbuilderandmanager.DAO.tcgDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;

    private Button mCreateAccount;

    private tcgDAO mTCGDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();

        getDatabase();

    }

    private void wireupDisplay() {

        mUsername = findViewById(R.id.editTextUserName);
        mPassword = findViewById(R.id.editTextPassword);

        mCreateAccount = findViewById(R.id.buttonLogin);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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