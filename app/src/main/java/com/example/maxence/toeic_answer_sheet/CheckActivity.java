package com.example.maxence.toeic_answer_sheet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class CheckActivity extends AppCompatActivity {

    public String answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        answers = intent.getStringExtra(AnswerActivity.ANSWERS);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.check_main_layout);
        createAnswerLines(mainLayout);
    }

    public void computeScore(View view){
        int scoreOral = 0;
        int scoreWritten = 0;
        RadioGroup group;
        for(int i = 0; i < 200; i++){
            group = (RadioGroup) findViewById(i*10 + 5);
            // YES is checked
            if(group.getCheckedRadioButtonId() == i*10 + 7){
                if(i < 100)
                    scoreOral++;
                else
                    scoreWritten++;
            }

        }

        int score = scoreOral + scoreWritten;
        TextView scoreO = (TextView) findViewById(R.id.check_scoreOral);
        scoreO.setText("" + scoreOral);
        TextView scoreW = (TextView) findViewById(R.id.check_scoreWritten);
        scoreW.setText("" + scoreWritten);
        TextView score200 = (TextView) findViewById(R.id.check_score200);
        score200.setText("" + score + "/200");
        TextView score1000 = (TextView) findViewById(R.id.check_score1000);
        score1000.setText("" + score * 5 + "/1000");
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

            subLayout.addView(questionNum);

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

            switch (answers.getBytes()[i]){
                case 'A':
                    group.check(i*10 + 1);
                    break;
                case 'B':
                    group.check(i*10 + 2);
                    break;
                case 'C':
                    group.check(i*10 + 3);
                    break;
                case 'D':
                    group.check(i*10 + 4);
                    break;
                default:
                    break;
            }

            subLayout.addView(group);

            Space space = new Space(this);
            float dip = 20f;
            Resources r = getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            space.setLayoutParams(new ViewGroup.LayoutParams(
                    (int) px,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            subLayout.addView(space);

            RadioGroup checkGroup = new RadioGroup(this);
            checkGroup.setId(i*10 + 5);
            checkGroup.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT));
            checkGroup.setOrientation(LinearLayout.HORIZONTAL);
            RadioButton checkNo = new RadioButton(this);
            checkNo.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            checkNo.setText("N");
            checkNo.setId(i*10 + 6);
            checkGroup.addView(checkNo);
            RadioButton checkYes = new RadioButton(this);
            checkYes.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            checkYes.setText("Y");
            checkYes.setId(i*10 + 7);
            checkGroup.addView(checkYes);

            subLayout.addView(checkGroup);

            mainLayout.addView(subLayout);
        }
    }
}
