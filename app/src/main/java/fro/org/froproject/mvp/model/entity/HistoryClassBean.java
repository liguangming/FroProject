package fro.org.froproject.mvp.model.entity;

import android.os.Parcel;

import java.io.Serializable;

/**
 * 历史班级内容
 * Created by Administrator on 2017/4/27 0027.
 */

public class HistoryClassBean implements Serializable {

    int id;
    String className;// 班级名称
    String classPic;// 班级图片
    String endDate;//结束时间
    int learnStatus;//: 学习状态 -1 未学习 0 未通过 1已通过
    String startDate;//: 开始时间
    int totalCourseNum;// 总课程数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPic() {
        return classPic;
    }

    public void setClassPic(String classPic) {
        this.classPic = classPic;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getLearnStatus() {
        return learnStatus;
    }

    public void setLearnStatus(int learnStatus) {
        this.learnStatus = learnStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getTotalCourseNum() {
        return totalCourseNum;
    }

    public void setTotalCourseNum(int totalCourseNum) {
        this.totalCourseNum = totalCourseNum;
    }



    public HistoryClassBean() {
    }

    protected HistoryClassBean(Parcel in) {
        this.id = in.readInt();
        this.className = in.readString();
        this.classPic = in.readString();
        this.endDate = in.readString();
        this.learnStatus = in.readInt();
        this.startDate = in.readString();
        this.totalCourseNum = in.readInt();
    }

}
