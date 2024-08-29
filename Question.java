package com.example.firequiz;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable {
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String question;

    public List<String> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<String> choiceList) {
        this.choiceList = choiceList;
    }

    private List<String> choiceList;
    private int answerIndex;

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public Question(String question, List<String> choiceList, int answerIndex) {
        this.question = question;
        this.choiceList = choiceList;
        this.answerIndex = answerIndex;
    }

    protected Question(Parcel in) {
        question = in.readString();
        choiceList = in.createStringArrayList();
        answerIndex = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(choiceList);
        dest.writeInt(answerIndex);
    }

    // Getters and Setters
}
