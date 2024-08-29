package com.example.firequiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuestionBank implements Parcelable {
    private List<Question> questionList;
    private int currentQuestionIndex = 0;

    public QuestionBank(List<Question> questionList) {
        this.questionList = questionList;
    }

    protected QuestionBank(Parcel in) {
        questionList = in.createTypedArrayList(Question.CREATOR);
        currentQuestionIndex = in.readInt();
    }

    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questionList);
        dest.writeInt(currentQuestionIndex);
    }

    public Question getCurrentQuestion() {
        return questionList.get(currentQuestionIndex);
    }

    public Question getNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= questionList.size()) {
            currentQuestionIndex = 0;
        }
        return getCurrentQuestion();
    }
}

