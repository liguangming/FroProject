package fro.org.froproject.mvp.model.entity;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class WorkYear {
    boolean select;
    int id=-999;
    String name;

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
