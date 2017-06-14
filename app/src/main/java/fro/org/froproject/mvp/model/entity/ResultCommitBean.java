package fro.org.froproject.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 题 包含ID和答案列表
 * Created by Administrator on 2017/4/28 0028.
 */

public class ResultCommitBean {
    int id;

    List<ResultAnswer> courseExerCiseAnsweres = new ArrayList();//题


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ResultAnswer> getCourseExerCiseAnsweres() {
        return courseExerCiseAnsweres;
    }

    public void setCourseExerCiseAnsweres(List<ResultAnswer> courseExerCiseAnsweres) {
        this.courseExerCiseAnsweres = courseExerCiseAnsweres;
    }
}
