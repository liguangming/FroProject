package fro.org.froproject.mvp.model.entity;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class ExerciseResponseBean implements Serializable{
    boolean passed;//是否通过

    int passedExerciseNum;//通过题数

    int totalExerciseNum;//总题数

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getPassedExerciseNum() {
        return passedExerciseNum;
    }

    public void setPassedExerciseNum(int passedExerciseNum) {
        this.passedExerciseNum = passedExerciseNum;
    }

    public int getTotalExerciseNum() {
        return totalExerciseNum;
    }

    public void setTotalExerciseNum(int totalExerciseNum) {
        this.totalExerciseNum = totalExerciseNum;
    }


    public ExerciseResponseBean() {
    }

    protected ExerciseResponseBean(Parcel in) {
        this.passed = in.readByte() != 0;
        this.passedExerciseNum = in.readInt();
        this.totalExerciseNum = in.readInt();
    }

}
