package fro.org.froproject.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class CountryData implements Serializable {
    String name;
    String type;
    String children[];
    int id=-999;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
