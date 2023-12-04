package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NormieCardsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "FTF.tcgdeckbuilderandmanager.userIdKey";
    private static final String PREFERENCES_KEY = "FTF.tcgdeckbuilderandmanager.preferencesKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normie_cards);
    }

    public static Intent intentFactory(Context context, int userId) {

        Intent intent = new Intent(context, NormieCardsActivity.class);

        intent.putExtra(USER_ID_KEY, userId);

        return intent;

    }

}