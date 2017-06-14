package fro.org.froproject.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/4/28 0028.
 */

public class ExerciseAnswerBean implements Comparable<ExerciseAnswerBean> {
    int order;// 选项顺序

    String content;//选项内容

    int id;
    boolean selected;//是否选中

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int get_order() {
        return order;
    }

    public void setorder(int _order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull ExerciseAnswerBean o) {
        return this.get_order() - o.get_order();
    }
}
