package com.example.maxence.toeic_answer_sheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveActivity extends AppCompatActivity {

    public String answers;
    //public boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        answers = intent.getStringExtra(AnswerActivity.ANSWERS);
        //checked = intent.getBooleanExtra(CheckActivity.???)

        displayFiles();

    }

    public void displayFiles() {
        TextView files = (TextView) findViewById(R.id.saved_files);
        String names = "";
        for(String s : fileList()){
            names += s + "\n";
        }
        files.setText(names);
    }

    public void save(View view){
        FileOutputStream output = null;
        String fileName = "Default";

        EditText editText = (EditText) findViewById(R.id.save_fileName);
        String editTextContent = editText.getText().toString();
        if(!editTextContent.equals("") && !editTextContent.contains("txt"))
            fileName = editText.getText().toString();

        try {
            output = openFileOutput("TOEIC_" + fileName + ".txt", MODE_PRIVATE);
            output.write(answers.getBytes());
            if(output != null)
                output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayFiles();
    }

}
