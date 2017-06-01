package fro.org.froproject.mvp.model.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class CourseResponseBean {
    List<CourseBean> dataList = new ArrayList<>();
    int pageNo;// (integer, optional),
    int pageSize;//(integer, optional),
    int pages;//(integer, optional),
    int total;//(integer, optional)

    public List<CourseBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<CourseBean> dataList) {
        this.dataList = dataList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
