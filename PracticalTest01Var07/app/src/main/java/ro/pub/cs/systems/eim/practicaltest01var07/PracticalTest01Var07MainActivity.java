package ro.pub.cs.systems.eim.practicaltest01var07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var07MainActivity extends AppCompatActivity {

    CheckBox nameCheckBox = null, groupCheckBox = null;
    EditText nameEditText = null, groupEditText = null;

    private CheckBoxListener checkBoxListener = new CheckBoxListener();
    private NavigateClickListener navigateClickListener = new NavigateClickListener();

    private boolean serviceStatus;
    private IntentFilter startedServiceIntentFilter;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.name_checkbox:
                    if (isChecked) {
                        nameEditText.setEnabled(true);
                    }
                    else if (!isChecked) {
                        nameEditText.setEnabled(false);
                    }
                    break;
                case R.id.group_checkbox:
                    if (isChecked) {
                        groupEditText.setEnabled(true);
                    }
                    else if (!isChecked) {
                        groupEditText.setEnabled(false);
                    }
                    break;
            }

            boolean firstChecked = nameCheckBox.isChecked();
            boolean secondChecked = groupCheckBox.isChecked();
            String nameText = nameEditText.getText().toString();
            String groupText = groupEditText.getText().toString();

            if (firstChecked == false && secondChecked == false && !nameText.equals("") && !groupEditText.equals("")) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var07Service.class);
                intent.putExtra("nameEditText", nameText);
                intent.putExtra("groupEditText", groupText);
                startService(intent);
                serviceStatus = true;
            }
            else {
                if (serviceStatus == true) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var07Service.class);
                    stopService(intent);
                    serviceStatus = false;
                }
            }
        }
    }

    private class NavigateClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var07SecondaryActivity.class);
            intent.putExtra("nameEditText", nameEditText.getText().toString());
            intent.putExtra("groupEditText", groupEditText.getText().toString());

            startActivityForResult(intent, 2016);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameCheckBox = (CheckBox) findViewById(R.id.name_checkbox);
        nameCheckBox.setOnCheckedChangeListener(checkBoxListener);

        groupCheckBox = (CheckBox) findViewById(R.id.group_checkbox);
        groupCheckBox.setOnCheckedChangeListener(checkBoxListener);

        nameEditText = (EditText) findViewById(R.id.name_edit_text);

        groupEditText = (EditText) findViewById(R.id.group_edit_text);

        Button secondActivityButton = (Button) findViewById(R.id.navigate_second_activity_button);
        secondActivityButton.setOnClickListener(navigateClickListener);

        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction("action1");
        startedServiceIntentFilter.addAction("action2");
        serviceStatus = false;

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("nameCheckBox")) {
                nameCheckBox.setChecked(savedInstanceState.getBoolean("nameCheckBox"));
            }
            else {
                nameCheckBox.setChecked(false);
            }
            if (savedInstanceState.containsKey("groupCheckBox")) {
                groupCheckBox.setChecked(savedInstanceState.getBoolean("groupCheckBox"));
            }
            else {
                groupCheckBox.setChecked(false);
            }
            if (savedInstanceState.containsKey("nameEditText")) {
                nameEditText.setText(savedInstanceState.getString("nameEditText"));
            }
            else {
                nameEditText.setText("");
            }
            if (savedInstanceState.containsKey("groupEditText")) {
                groupEditText.setText(savedInstanceState.getString("groupEditText"));
            }
            else {
                groupEditText.setText("");
            }
        }
        else {
            nameCheckBox.setChecked(false);
            groupCheckBox.setChecked(false);
            nameEditText.setText("");
            groupEditText.setText("");
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 2016:
                Toast.makeText(getApplication(), "Secondary Activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var07Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("nameCheckBox", nameCheckBox.isChecked());
        outState.putBoolean("groupCheckBox", groupCheckBox.isChecked());

        if (!nameEditText.getText().toString().equals("")) {
            outState.putString("nameEditText", nameEditText.getText().toString());
        }

        if (!groupEditText.getText().toString().equals("")) {
            outState.putString("groupEditText", groupEditText.getText().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("nameCheckBox")) {
            nameCheckBox.setChecked(savedInstanceState.getBoolean("nameCheckBox"));
        }
        else {
            nameCheckBox.setChecked(false);
        }

        if (savedInstanceState.containsKey("groupCheckBox")) {
            groupCheckBox.setChecked(savedInstanceState.getBoolean("groupCheckBox"));
        }
        else {
            groupCheckBox.setChecked(false);
        }

        if (savedInstanceState.containsKey("nameEditText")) {
            nameEditText.setText(savedInstanceState.getString("nameEditText"));
        }
//        else {
//            nameEditText.setText("");
//        }

        if (savedInstanceState.containsKey("groupEditText")) {
            groupEditText.setText(savedInstanceState.getString("groupEditText"));
        }
//        else {
//            groupEditText.setText("");
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_var07_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
