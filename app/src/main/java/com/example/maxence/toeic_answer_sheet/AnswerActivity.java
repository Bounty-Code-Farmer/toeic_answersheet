package com.example.maxence.toeic_answer_sheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        createAnswerLines();
    }

    public void createAnswerLines(){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.answer_main_layout);
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
            group.setId(1000 + i);
            group.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT));
            group.setOrientation(LinearLayout.HORIZONTAL);
            RadioButton radioButtonA = new RadioButton(this);
            radioButtonA.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonA.setText("A");
            group.addView(radioButtonA);
            RadioButton radioButtonB = new RadioButton(this);
            radioButtonB.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonB.setText("B");
            group.addView(radioButtonB);
            RadioButton radioButtonC = new RadioButton(this);
            radioButtonC.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonC.setText("C");
            group.addView(radioButtonC);
            RadioButton radioButtonD = new RadioButton(this);
            radioButtonD.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButtonD.setText("D");
            group.addView(radioButtonD);

            subLayout.addView(questionNum);
            subLayout.addView(group);

            mainLayout.addView(subLayout);
        }
    }
}
