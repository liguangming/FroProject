package fro.org.froproject.mvp.model.entity;

/**
 * 组织机构
 * Created by Administrator on 2017/5/22 0022.
 */

public class OrgBean {
    boolean select;
    int id=-999;//(integer, optional),
    String name;// (string, optional): 机构名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

}
