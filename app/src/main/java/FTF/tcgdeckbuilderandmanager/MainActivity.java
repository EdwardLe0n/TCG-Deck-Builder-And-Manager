package FTF.tcgdeckbuilderandmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

// Refer to this series of tutorials!!!!!:
// https://www.youtube.com/watch?v=vxfYa2r3_vs&list=PLMljn2yeXv0GVyClU5sifWev6YUrXfBgU&index=3

public class MainActivity extends AppCompatActivity {

    TextView mMainDisplay;

    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}