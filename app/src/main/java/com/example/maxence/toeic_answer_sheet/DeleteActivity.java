package com.example.maxence.toeic_answer_sheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        displayFiles();
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.delete_main_layout);
        Button deleteButton = new Button(this);
        deleteButton.setText("DELETE");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelectedFiles();
            }
        });
        deleteButton.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        main_layout.addView(deleteButton);

    }

    public void deleteSelectedFiles(){
        for(int i = 0; i < fileList().length; i++){
            CheckBox checkBox = (CheckBox) findViewById(15000 + i);
            if(checkBox.isChecked()){
                deleteFile("TOEIC_" + checkBox.getText().toString() + ".txt");
            }
        }
        displayFiles();
    }

    public void displayFiles() {
        LinearLayout files_layout = (LinearLayout) findViewById(R.id.delete_files_layout);
        files_layout.removeAllViews();

        int i = 0;
        if(fileList().length == 0){
            TextView textView = new TextView(this);
            textView.setText("There is no answer sheet saved on this phone");
            textView.setTextSize(COMPLEX_UNIT_DIP,15);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            files_layout.addView(textView);
        }
        for(String s : fileList()){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(s.replace("TOEIC_", "").replace(".txt", ""));
            checkBox.setId(15000 + i);
            files_layout.addView(checkBox);
            i++;
        }
    }
}
