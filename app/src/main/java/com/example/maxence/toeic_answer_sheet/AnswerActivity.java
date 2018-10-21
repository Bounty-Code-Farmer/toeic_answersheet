package com.example.maxence.toeic_answer_sheet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class AnswerActivity extends AppCompatActivity {

    public final static String ANSWERS = "com.example.maxence.toeic_answer_sheet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.answer_main_layout);
        createAnswerLines(mainLayout);


    }

    public void switchToSave(View view){
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra(ANSWERS, parseAnswers());
        startActivity(intent);
    }

    public void switchToCheck(View view){
        Intent intent = new Intent(this, CheckActivity.class);
        intent.putExtra(ANSWERS, parseAnswers());
        startActivity(intent);
    }

    public String parseAnswers(){
        String answers = "";
        for(int i = 0; i < 200; i++){
            RadioGroup temp = (RadioGroup) findViewById(i*10);
            int index = i*10;
            int checked = temp.getCheckedRadioButtonId();
            if(checked == index + 1) answers+="A";
            else if(checked == index + 2) answers+="B";
            else if(checked == index + 3) answers+="C";
            else if(checked == index + 4) answers+="D";
            else answers+="?";
        }
        return answers;
    }

    public void createAnswerLines(LinearLayout mainLayout){
        for(int i = 0; i < 200; ++i){
            LinearLayout subLayout = new LinearLayout(this);
            subLayout.setOrientation(LinearLayout.HORIZONTAL);
            subLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            if(i == 0){
                TextView oralComp = new TextView(this);
                oralComp.setText("ORAL COMPREHENSION");
                oralComp.setTextSize(COMPLEX_UNIT_DIP, 25);
                oralComp.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                oralComp.setGravity(View.TEXT_ALIGNMENT_CENTER);
                mainLayout.addView(oralComp);
            }
            if(i == 100){
                TextView writterComp = new TextView(this);
                writterComp.setText("WRITTEN COMPREHENSION");
                writterComp.setTextSize(COMPLEX_UNIT_DIP, 25);
                writterComp.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                writterComp.setGravity(View.TEXT_ALIGNMENT_CENTER);
                mainLayout.addView(writterComp);
            }

            TextView questionNum = new TextView(this);
            String text = Integer.toString(i+1);
            if((i+1) / 10 == 0) text += "  ";
            if((i+1) / 100 == 0) text += "  ";
            text += " :";
            questionNum.setText(text);
            questionNum.setTextSize(COMPLEX_UNIT_DIP, 20);
            questionNum.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            RadioGroup group = new RadioGroup(this);
            group.setId(i*10);
            group.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT));
            group.setOrientation(LinearLayout.HORIZONTAL);
            RadioButton radioButtonA = new RadioButton(this);
            radioButtonA.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonA.setText("A");
            radioButtonA.setId(i*10 + 1);
            group.addView(radioButtonA);
            RadioButton radioButtonB = new RadioButton(this);
            radioButtonB.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonB.setText("B");
            radioButtonB.setId(i*10 + 2);
            group.addView(radioButtonB);
            RadioButton radioButtonC = new RadioButton(this);
            radioButtonC.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonC.setText("C");
            radioButtonC.setId(i*10 + 3);
            group.addView(radioButtonC);
            RadioButton radioButtonD = new RadioButton(this);
            radioButtonD.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonD.setText("D");
            radioButtonD.setId(i*10 + 4);
            group.addView(radioButtonD);

            subLayout.addView(questionNum);
            subLayout.addView(group);

            mainLayout.addView(subLayout);
        }
    }
}
