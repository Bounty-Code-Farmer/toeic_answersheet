package com.example.maxence.toeic_answer_sheet;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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

import static android.graphics.Color.*;
import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class CheckActivity extends AppCompatActivity {

    public final static String CHECKING = "com.example.maxence.toeic_answer_sheet.CHECKING";
    public final static String CHECKED = "com.example.maxence.toeic_answer_sheet.CHECKED";
    public final static String SCOREORAL = "com.example.maxence.toeic_answer_sheet.SCOREORAL";
    public final static String SCOREWRITTEN = "com.example.maxence.toeic_answer_sheet.SCOREWRITTEN";
    public String answers;
    public String checking;
    public boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        answers = intent.getStringExtra(AnswerActivity.ANSWERS);

        checked = intent.getBooleanExtra(CheckActivity.CHECKED, false);
        checking = intent.getStringExtra(CheckActivity.CHECKING);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.check_main_layout);
        createAnswerLines(mainLayout);

        if(checked)
            restoreChecking();

        computeScore(findViewById(R.id.check_compute_button));
    }

    public void restoreChecking() {
        RadioGroup checkingGroup;
        RadioGroup answersGroup;
        for(int i = 0; i < 200; i++) {
            checkingGroup = (RadioGroup) findViewById(i*10 + 5);
            answersGroup = (RadioGroup) findViewById(i*10);
            switch (checking.getBytes()[i]){
                case 'N':
                    checkingGroup.check(i*10 + 6);
                    disableCorrection(i*10, answersGroup.getCheckedRadioButtonId());
                    break;
                case 'Y':
                    checkingGroup.check(i*10 + 7);
                    disableCorrection(i*10, answersGroup.getCheckedRadioButtonId());
                    break;
                default:
                    break;
            }
        }
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

        LinearLayout scoreLayout = findViewById(R.id.check_score_layout);
        if(score*5 >= 800){
            // Green
            scoreLayout.setBackgroundColor(Color.parseColor("#a0ff9b"));
        }else{
            // Red
            scoreLayout.setBackgroundColor(Color.parseColor("#ff9b9b"));
        }
    }

    public void enableCorrection(int checkGroupId){
        RadioButton button;
        int radioGroupId = checkGroupId - 5;
        for(int i = radioGroupId + 1; i <= radioGroupId + 4; i++){
            button = (RadioButton) findViewById(i);
            button.setEnabled(true);
        }
    }

    public void disableCorrection(int radioGroupId, int buttonIdToKeepEnable){
        RadioButton button;
        for(int i = radioGroupId + 1; i <= radioGroupId + 4; i++){
            button = (RadioButton) findViewById(i);
            button.setEnabled(false);
        }
        button = (RadioButton) findViewById(buttonIdToKeepEnable);
        if(button != null)
            button.setEnabled(true);
    }

    public void switchToSave(View view){
        computeScore(findViewById(R.id.check_compute_button));
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra(AnswerActivity.ANSWERS, parseAnswers());
        intent.putExtra(CheckActivity.CHECKING, parseChecking());
        intent.putExtra(CheckActivity.CHECKED, true);
        TextView scoreO = (TextView) findViewById(R.id.check_scoreOral);
        TextView scoreW = (TextView) findViewById(R.id.check_scoreWritten);
        intent.putExtra(CheckActivity.SCOREORAL, scoreO.getText());
        intent.putExtra(CheckActivity.SCOREWRITTEN, scoreW.getText());
        startActivity(intent);
    }

    public String parseAnswers(){
        String ans = "";
        for(int i = 0; i < 200; i++){
            int index = i*10;
            RadioGroup temp = (RadioGroup) findViewById(index);
            int checked = temp.getCheckedRadioButtonId();
            if(checked == index + 1) ans+="A";
            else if(checked == index + 2) ans+="B";
            else if(checked == index + 3) ans+="C";
            else if(checked == index + 4) ans+="D";
            else ans+="?";
        }
        return ans;
    }

    public String parseChecking(){
        String checking = "";
        for(int i = 0; i < 200; i++){
            int index = i*10;
            RadioGroup temp = (RadioGroup) findViewById(index + 5);
            int checked = temp.getCheckedRadioButtonId();
            if(checked == index + 6) checking+="N";
            else if(checked == index + 7) checking+="Y";
            else checking+="?";
        }
        return checking;
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

            for(int k = 0; k < 4; k++)
                group.getChildAt(k).setEnabled(false);

            switch (answers.getBytes()[i]){
                case 'A':
                    group.check(i*10 + 1);
                    group.getChildAt(0).setEnabled(true);
                    break;
                case 'B':
                    group.check(i*10 + 2);
                    group.getChildAt(1).setEnabled(true);
                    break;
                case 'C':
                    group.check(i*10 + 3);
                    group.getChildAt(2).setEnabled(true);
                    break;
                case 'D':
                    group.check(i*10 + 4);
                    group.getChildAt(3).setEnabled(true);
                    break;
                default:
                    break;
            }

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    disableCorrection(radioGroup.getId(), id);
                }
            });
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
            checkGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    if(id%10 == 6)
                        enableCorrection(radioGroup.getId());
                    if(id%10 == 7){
                        RadioGroup rg = findViewById(id - id%10);
                        disableCorrection(rg.getId(), rg.getCheckedRadioButtonId());
                    }
                }
            });
            checkGroup.addView(checkYes);

            subLayout.addView(checkGroup);

            mainLayout.addView(subLayout);
        }
    }
}
