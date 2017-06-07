package fro.org.froproject.mvp.model.entity;

/**
 * 班级
 * Created by Administrator on 2017/4/25 0025.
 */

public class ClassBean {
    int totalCourseNum;
    int process;
    int passedCourseNum;
    int id;
    String className;
    String classPic;

    public int getTotalCourseNum() {
        return totalCourseNum;
    }

    public void setTotalCourseNum(int totalCourseNum) {
        this.totalCourseNum = totalCourseNum;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getPassedCourseNum() {
        return passedCourseNum;
    }

    public void setPassedCourseNum(int passedCourseNum) {
        this.passedCourseNum = passedCourseNum;
    }

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
}
