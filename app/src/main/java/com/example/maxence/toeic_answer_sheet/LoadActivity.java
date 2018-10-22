package com.example.maxence.toeic_answer_sheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        displayFiles();
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.load_main_layout);

    }

    public void loadSelectedFile(View view) {
        FileInputStream input;
        StringBuffer answers = new StringBuffer("");
        RadioGroup saves = (RadioGroup) findViewById(R.id.load_files);
        RadioButton selected = findViewById(saves.getCheckedRadioButtonId());
        try {
            input = openFileInput("TOEIC_" + selected.getText().toString() + ".txt");
            byte[] buffer = new byte[1024];
            answers = new StringBuffer("");

            int n;
            while ((n = input.read(buffer)) != -1)
            {
                answers.append(new String(buffer, 0, n));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, AnswerActivity.class);
        intent.putExtra(AnswerActivity.ANSWERS, answers.toString());
        startActivity(intent);
    }

    public void displayFiles() {
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.load_main_layout);

        if(fileList().length == 0){
            TextView textView = new TextView(this);
            textView.setText("There is no answer sheet saved on this phone");
            textView.setTextSize(COMPLEX_UNIT_DIP,15);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            main_layout.addView(textView);
        }

        RadioGroup saves = (RadioGroup) findViewById(R.id.load_files);
        for(String s : fileList()){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(s.replace("TOEIC_", "").replace(".txt", ""));
            saves.addView(radioButton);
        }
    }
}
