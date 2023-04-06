package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    EditText nameResultText;
    EditText groupResultText;

    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        nameResultText = findViewById(R.id.name_result_text);
        groupResultText = findViewById(R.id.group_result_text);
        cancelButton = findViewById(R.id.cancel_button);
        okButton = findViewById(R.id.ok_button);

        okButton.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        cancelButton.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String name = extras.getString(Constants.NAME_TEXT);
                String group = extras.getString(Constants.GROUP_TEXT);
                if (!name.isEmpty()) {
                    nameResultText.setText(name);
                }
                if (!group.isEmpty()) {
                    groupResultText.setText(group);
                }
            }
        } else {
            nameResultText.setText(savedInstanceState.getString(Constants.NAME_TEXT));
            groupResultText.setText(savedInstanceState.getString(Constants.GROUP_TEXT));
        }
    }
}