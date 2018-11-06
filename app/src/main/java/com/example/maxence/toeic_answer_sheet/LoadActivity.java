package com.example.maxence.toeic_answer_sheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    @Override
    protected void onResume() {
        Button button = (Button) findViewById(R.id.load_load_button);
        button.setEnabled(true);
        super.onResume();
    }

    public void loadSelectedFile(View view) {
        FileInputStream input;
        StringBuffer data = new StringBuffer("");
        RadioGroup saves = (RadioGroup) findViewById(R.id.load_files);
        RadioButton selected = findViewById(saves.getCheckedRadioButtonId());
        if(selected != null){
            try {
                input = openFileInput("TOEIC_" + selected.getText().toString() + ".txt");
                byte[] buffer = new byte[1024];
                data = new StringBuffer("");

                int n;
                while ((n = input.read(buffer)) != -1)
                {
                    data.append(new String(buffer, 0, n));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String dataString = data.toString();

            Intent intent;
            if(dataString.contains("true")){ // Sheet already checked
                intent = new Intent(this, CheckActivity.class);
                intent.putExtra(CheckActivity.CHECKED, true);
                intent.putExtra(CheckActivity.CHECKING, dataString.substring(200,400));
            }else {
                intent = new Intent(this, AnswerActivity.class);
            }

            intent.putExtra(AnswerActivity.ANSWERS, dataString.substring(0,200));

            Button button = (Button) findViewById(R.id.load_load_button);
            button.setEnabled(false);
            startActivity(intent);
        }else{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("You have to select a saved sheet first")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            
                        }
                    })
                    .show();
        }

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
