package fro.org.froproject.mvp.model.entity;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class ClassListBean<T>{
    String joinClassNumber;

    PagedResult<T> pagedResult;


    public PagedResult<T> getPagedResult() {
        return pagedResult;
    }

    public void setPagedResult(PagedResult<T> pagedResult) {
        this.pagedResult = pagedResult;
    }

    public String getJoinClassNumber() {
        return joinClassNumber;
    }

    public void setJoinClassNumber(String joinClassNumber) {
        this.joinClassNumber = joinClassNumber;
    }

}
