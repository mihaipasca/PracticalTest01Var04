package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    Button navigateButton;
    Button displayButton;
    CheckBox nameCheckBox;
    CheckBox groupCheckBox;
    EditText nameText;
    EditText groupText;
    TextView displayText;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);
        navigateButton = findViewById(R.id.navigate_button);
        displayButton = findViewById(R.id.display_button);
        nameCheckBox = findViewById(R.id.name_checkbox);
        groupCheckBox = findViewById(R.id.group_checkbox);
        nameText = findViewById(R.id.name_text);
        groupText = findViewById(R.id.group_text);
        displayText = findViewById(R.id.display_text);

        navigateButton.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
            intent.putExtra(Constants.NAME_TEXT, nameText.getText().toString());
            intent.putExtra(Constants.GROUP_TEXT, groupText.getText().toString());
            startActivityForResult(intent, Constants.REQUEST_CODE);
        });

        displayButton.setOnClickListener(it -> {
            String displayContent = "";
            boolean flag = true;
            if (nameCheckBox.isChecked()) {
                if (nameText.getText().toString().isEmpty()) {
                    flag = false;
                    Toast.makeText(this, "Name is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    displayContent += nameText.getText().toString();
                }
            }
            if (groupCheckBox.isChecked()) {
                if (groupText.getText().toString().isEmpty()) {
                    flag = false;
                    Toast.makeText(this, "Group is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!displayContent.isEmpty()) {
                        displayContent += " ";
                    }
                    displayContent += groupText.getText().toString();
                }
            }
            if (flag) {
                displayText.setText(displayContent);
                startTestService();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.NAME_TEXT, nameText.getText().toString());
        outState.putString(Constants.GROUP_TEXT, groupText.getText().toString());
        outState.putString(Constants.DISPLAY_TEXT, displayText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        nameText.setText(savedInstanceState.getString(Constants.NAME_TEXT));
        groupText.setText(savedInstanceState.getString(Constants.GROUP_TEXT));
        displayText.setText(savedInstanceState.getString(Constants.DISPLAY_TEXT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Rezultat ok!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Rezultat not ok!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void startTestService() {
        if (!nameText.getText().toString().isEmpty() && !groupText.getText().toString().isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
            intent.putExtra(Constants.NAME_TEXT, nameText.getText().toString());
            intent.putExtra(Constants.GROUP_TEXT, groupText.getText().toString());
            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
        getApplicationContext().stopService(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        intentFilter.addAction(Constants.actionTypes[0]);
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }
}