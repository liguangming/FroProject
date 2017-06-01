package fro.org.froproject.mvp.model.entity;


import java.io.Serializable;


/**
 * 课程
 * Created by Administrator on 2017/4/26 0026.
 */

public class CourseBean implements Serializable {
    int order;// 顺序

    String classPic; //  课程所在班级图片

    String courseName;//  课程名称

    int id;//
    /**
     * 学习状态 -1 未学习 0 未通过 1已通过
     */
    int learnStatus;//

    String passedDate;// 课程通过时间
    String text;// html内容
    int classId;
    String className;
    int totalCourseNum;

    public int getTotalCourseNum() {
        return totalCourseNum;
    }

    public void setTotalCourseNum(int totalCourseNum) {
        this.totalCourseNum = totalCourseNum;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getClassPic() {
        return classPic;
    }

    public void setClassPic(String classPic) {
        this.classPic = classPic;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLearnStatus() {
        return learnStatus;
    }

    public void setLearnStatus(int learnStatus) {
        this.learnStatus = learnStatus;
    }

    public String getPassedDate() {
        return passedDate;
    }

    public void setPassedDate(String passedDate) {
        this.passedDate = passedDate;
    }


    public CourseBean() {
    }

}
