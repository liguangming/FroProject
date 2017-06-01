package fro.org.froproject.mvp.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ProvinceData implements Serializable {
    private String name;
    private String type;
    private int id;
    private ArrayList<CityData> children;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CityData> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<CityData> children) {
        this.children = children;
    }
}
