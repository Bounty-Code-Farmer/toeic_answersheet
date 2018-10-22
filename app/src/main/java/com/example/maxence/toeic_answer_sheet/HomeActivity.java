package com.example.maxence.toeic_answer_sheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        Button button = (Button) findViewById(R.id.newsheet);
        button.setEnabled(true);
        super.onResume();
    }

    public void switchToAnswer(View view) {
        Intent intent = new Intent(this, AnswerActivity.class);
        Button button = (Button) findViewById(R.id.newsheet);
        button.setEnabled(false);
        startActivity(intent);
    }

    public void switchToLoad(View view) {
        Intent intent = new Intent(this, LoadActivity.class);
        startActivity(intent);
    }

    public void switchToDelete(View view) {
        Intent intent = new Intent(this, DeleteActivity.class);
        startActivity(intent);
    }
}
