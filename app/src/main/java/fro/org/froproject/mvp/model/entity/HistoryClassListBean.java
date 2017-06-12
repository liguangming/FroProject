package fro.org.froproject.mvp.model.entity;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class HistoryClassListBean<T> {

    PagedResult<T> pagedResult;

    String historyClassNum;

    public String getHistoryClassNum() {
        return historyClassNum;
    }

    public void setHistoryClassNum(String historyClassNum) {
        this.historyClassNum = historyClassNum;
    }

    public PagedResult<T> getPagedResult() {
        return pagedResult;
    }

    public void setPagedResult(PagedResult<T> pagedResult) {
        this.pagedResult = pagedResult;
    }
}
