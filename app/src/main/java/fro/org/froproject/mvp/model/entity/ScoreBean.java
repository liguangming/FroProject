package fro.org.froproject.mvp.model.entity;


/**
 * 进度与成绩
 * Created by Administrator on 2017/5/3 0003.
 */

public class ScoreBean<T> {
    PagedResult<T> pagedResult;

    public PagedResult<T> getPagedResult() {
        return pagedResult;
    }

    public void setPagedResult(PagedResult<T> pagedResult) {
        this.pagedResult = pagedResult;
    }
}
