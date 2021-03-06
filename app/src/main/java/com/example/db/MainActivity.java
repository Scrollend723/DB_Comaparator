package com.example.db;

import android.arch.persistence.room.RoomDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.db.DBManagers.ObjectBoxManager;
import com.example.db.DBManagers.RealmManager;
import com.example.db.DBManagers.RoomManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button objectBoxButton;
    private Button roomButton;
    private Button realmButton;
    private TextView realmTextView;
    private TextView roomTextView;
    private TextView objectBoxTextView;
    private RoomManager roomManager;
    private ObjectBoxManager objectBoxManager;
    private Button amountButton;
    private EditText amountEditText;
    private RealmManager realmManager;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDbManagers();
        initViewFields();
        setOnClickListeners();
        initSpinner();
    }

    private void initDbManagers() {
        roomManager = new RoomManager(this);
        objectBoxManager = new ObjectBoxManager(this);
        realmManager = new RealmManager(this);
    }

    private void initViewFields() {
        objectBoxButton = findViewById(R.id.objectbox_button);
        objectBoxTextView = findViewById(R.id.objectbox_text_view);

        roomButton = findViewById(R.id.room_button);
        roomTextView = findViewById(R.id.room_text_view);

        realmButton = findViewById(R.id.realm_button);
        realmTextView = findViewById(R.id.realm_text_view);

        amountButton = findViewById(R.id.amount_button);
        amountEditText = findViewById(R.id.amount_edit_text);
    }

    private void setOnClickListeners() {
        roomButton.setOnClickListener(this);
        objectBoxButton.setOnClickListener(this);
        amountButton.setOnClickListener(this);
        realmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.room_button:
                roomManager.start(amount);
                setRoomTextView("working");
                break;
            case R.id.objectbox_button:
                objectBoxManager.start(amount);
                setObjectBoxTextView("working");
                break;
            case R.id.realm_button:
                realmManager.start(amount);
                setRealmTextView("working");
                break;
            case R.id.amount_button:
                amount = Integer.parseInt(amountEditText.getText().toString());
                setAllTextViews("0");
                break;
        }
    }

    private void setAllTextViews(String text) {
        setRoomTextView(text);
        setObjectBoxTextView(text);
        setRealmTextView(text);
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.room_db_mode_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switchRoomMode(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void switchRoomMode(int position) {
        switch (position) {
            case 0:
                roomManager.switchJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING);
                break;
            case 1:
                roomManager.switchJournalMode(RoomDatabase.JournalMode.TRUNCATE);
                break;
            case 2:
                roomManager.switchJournalMode(RoomDatabase.JournalMode.AUTOMATIC);
                break;
        }
    }

    public void setRoomTextView(String time) {
        roomTextView.setText(time);
    }

    public void setObjectBoxTextView(String time) {
        objectBoxTextView.setText(time);
    }

    public void setRealmTextView(String text) {
        realmTextView.setText(text);
    }
}
