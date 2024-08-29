package com.example.firequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firequiz.R;
import com.example.firequiz.Question;
import com.example.firequiz.QuestionBank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mGreetingTextView;
    private Button mPlayButton1;
    private Button mPlayButton2;
    private Button mPlayButton3;
    private Button mPlayButton4;
    private int mRemainingQuestionCount;
    private Question mCurrentQuestion;
    private int mScore;
    private boolean mEnableTouchEvents;
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";
    public static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    private QuestionBank mQuestionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGreetingTextView = findViewById(R.id.game_activity_textview_question);
        mPlayButton1 = findViewById(R.id.game_activity_button_1);
        mPlayButton2 = findViewById(R.id.game_activity_button_2);
        mPlayButton3 = findViewById(R.id.game_activity_button_3);
        mPlayButton4 = findViewById(R.id.game_activity_button_4);

        mPlayButton1.setOnClickListener(this);
        mPlayButton2.setOnClickListener(this);
        mPlayButton3.setOnClickListener(this);
        mPlayButton4.setOnClickListener(this);

        if (savedInstanceState == null) {
            mScore = 0;
            mRemainingQuestionCount = 3;
            mQuestionBank = generateQuestionBank();
            mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        } else {
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTION_BANK);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION_COUNT);
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mCurrentQuestion = savedInstanceState.getParcelable("currentQuestion");
        }

        displayQuestion(mCurrentQuestion);
        mEnableTouchEvents = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int index;

        if (v == mPlayButton1) {
            index = 0;
        } else if (v == mPlayButton2) {
            index = 1;
        } else if (v == mPlayButton3) {
            index = 2;
        } else if (v == mPlayButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Vue cliquée inconnue : " + v);
        }

        if (index == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Faux !", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemainingQuestionCount--;

                if (mRemainingQuestionCount > 0) {
                    mCurrentQuestion = mQuestionBank.getNextQuestion();
                    displayQuestion(mCurrentQuestion);
                } else {
                    endGame();
                }

                mEnableTouchEvents = true;
            }
        }, 2000);
    }

    private void displayQuestion(final Question question) {
        mGreetingTextView.setText(question.getQuestion());
        mPlayButton1.setText(question.getChoiceList().get(0));
        mPlayButton2.setText(question.getChoiceList().get(1));
        mPlayButton3.setText(question.getChoiceList().get(2));
        mPlayButton4.setText(question.getChoiceList().get(3));
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bravo !")
                .setMessage("Votre score est " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_STATE_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_STATE_QUESTION_BANK, mQuestionBank);
        outState.putInt(BUNDLE_STATE_QUESTION_COUNT, mRemainingQuestionCount);
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putParcelable("currentQuestion", mCurrentQuestion);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mQuestionBank = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTION_BANK);
        mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION_COUNT);
        mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
        mCurrentQuestion = savedInstanceState.getParcelable("currentQuestion");
        displayQuestion(mCurrentQuestion);
    }
    private QuestionBank generateQuestionBank() {
        List<Question> questionList = new ArrayList<>();

        questionList.add(new Question(
                "Quand le premier homme a-t-il marché sur la lune ?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3
        ));

        questionList.add(new Question(
                "Quel est le numéro de maison des Simpson ?",
                Arrays.asList("42", "101", "666", "742"),
                3
        ));

        questionList.add(new Question(
                "Qui a peint la Joconde ?",
                Arrays.asList("Michel-Ange", "Léonard de Vinci", "Raphaël", "Caravage"),
                1
        ));

        return new QuestionBank(questionList);
    }
}
