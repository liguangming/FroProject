package fro.org.froproject.mvp.model.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class ExerciseCommitBean {
    int classId;
    int courseId;
    ArrayList exercises;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public ArrayList getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList exercises) {
        this.exercises = exercises;
    }
}
