package ro.pub.cs.systems.eim.practicaltest01var07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var07SecondaryActivity extends AppCompatActivity {

    private EditText nameEditText = null, groupEditText = null;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(Activity.RESULT_OK);
                    break;
                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameEditText = (EditText) findViewById(R.id.name_second_edit_text);
        groupEditText = (EditText) findViewById(R.id.group_second_edit_text);

        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);


        Intent intent = getIntent();
        if (intent != null) {
            String nameText = intent.getStringExtra("nameEditText");
            String groupText = intent.getStringExtra("groupEditText");

            nameEditText.setText(nameText);
            groupEditText.setText(groupText);
        }
    }

}
