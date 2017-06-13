package fro.org.froproject.mvp.model.entity;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class ScoreClassBean {
    int id;
    String className;// 班级名称

    String classPic;// 班级图片

    int failedCourseNum;// 未通过课程数量


    int notLearnNum;// 未学习数量

    int passedCourseNum;// 已通过课程数

    int process;// 进度

    int totalCourseNum;// 总课程数

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

    public int getFailedCourseNum() {
        return failedCourseNum;
    }

    public void setFailedCourseNum(int failedCourseNum) {
        this.failedCourseNum = failedCourseNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotLearnNum() {
        return notLearnNum;
    }

    public void setNotLearnNum(int notLearnNum) {
        this.notLearnNum = notLearnNum;
    }

    public int getPassedCourseNum() {
        return passedCourseNum;
    }

    public void setPassedCourseNum(int passedCourseNum) {
        this.passedCourseNum = passedCourseNum;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getTotalCourseNum() {
        return totalCourseNum;
    }

    public void setTotalCourseNum(int totalCourseNum) {
        this.totalCourseNum = totalCourseNum;
    }
}
