package com.example.maxence.toeic_answer_sheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        String answers = intent.getStringExtra(AnswerActivity.ANSWERS);
        TextView data = (TextView) findViewById(R.id.save_data);
        data.setText(answers);
    }
}
