package fro.org.froproject.mvp.model.entity;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 题
 * <p>
 * Created by Administrator on 2017/4/28 0028.
 */

public class ExerciseBean implements Comparable<ExerciseBean> {
    int order;
    int id;
    String text;
    List<ExerciseAnswerBean> courseExerCises;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ExerciseAnswerBean> getCourseExerCises() {
        return courseExerCises;
    }

    public void setCourseExerCises(List<ExerciseAnswerBean> courseExerCises) {
        this.courseExerCises = courseExerCises;
    }

    @Override
    public int compareTo(@NonNull ExerciseBean o) {
        return this.getOrder() - o.getOrder();
    }
}
