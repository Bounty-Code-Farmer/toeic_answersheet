package com.example.maxence.toeic_answer_sheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveActivity extends AppCompatActivity {

    public String answers;
    public String checking;
    public boolean checked;
    public String scoreOral;
    public String scoreWritten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        answers = intent.getStringExtra(AnswerActivity.ANSWERS);
        checked = intent.getBooleanExtra(CheckActivity.CHECKED, false);
        checking = intent.getStringExtra(CheckActivity.CHECKING);
        scoreOral = intent.getStringExtra(CheckActivity.SCOREORAL);
        scoreWritten = intent.getStringExtra(CheckActivity.SCOREWRITTEN);

        EditText editText = (EditText) findViewById(R.id.save_fileName);
        editText.requestFocus();

        displayFiles();

    }

    public void displayFiles() {
        TextView files = (TextView) findViewById(R.id.saved_files);
        String names = "";
        for(String s : fileList()){
            names += s.replace("TOEIC_", "").replace(".txt", "") + "\n";
        }
        files.setText(names);
    }

    public void saveClicked(View view){

        String fileName = "Default";

        EditText editText = (EditText) findViewById(R.id.save_fileName);
        String editTextContent = editText.getText().toString();
        if(!editTextContent.equals("") && !editTextContent.contains(".txt"))
            fileName = editText.getText().toString();

        if(checked){
            fileName += "_CHECKED";
        }

        boolean collision = false;
        for(String s : fileList()){
            if(s.equals("TOEIC_" + fileName + ".txt"))
                collision = true;
        }
        if(collision){
            final String finalFileName = fileName;
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("File already exists")
                    .setMessage("Do you really want to replace the existing file?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            save(finalFileName);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else{
            save(fileName);
        }

        displayFiles();
    }

    public void save(String fileName){
        FileOutputStream output = null;
        try {
            output = openFileOutput("TOEIC_" + fileName + ".txt", MODE_PRIVATE);
            output.write(answers.getBytes());
            if(checked){
                output.write(checking.getBytes());
                output.write("true".getBytes());
                output.write(scoreOral.getBytes());
                output.write('|');
                output.write(scoreWritten.getBytes());
            }

            new AlertDialog.Builder(SaveActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Success!")
                    .setMessage("Answer sheet successfully saved")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SaveActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();

            if(output != null)
                output.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
