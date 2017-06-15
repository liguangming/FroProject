package fro.org.froproject.mvp.model.entity;


/**
 * 班级简单介绍
 * Created by Administrator on 2017/4/27 0027.
 */

public class ClassInfoBean<T> {
    PagedResult<T> pagedResult;

    String className;// 班级名称

    String classPic;// 班级图片

    int id;//

    int passedCourseNum;//已通过课程数

    int process;// 进度
    String sampleDescription;// 班级介绍

    int totalCourseNum;// 总课程数
    long startDate ;//起始时间
    long endDate;//结束时间

    public PagedResult<T> getPagedResult() {
        return pagedResult;
    }

    public void setPagedResult(PagedResult<T> pagedResult) {
        this.pagedResult = pagedResult;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public int getTotalCourseNum() {
        return totalCourseNum;
    }

    public void setTotalCourseNum(int totalCourseNum) {
        this.totalCourseNum = totalCourseNum;
    }
}
