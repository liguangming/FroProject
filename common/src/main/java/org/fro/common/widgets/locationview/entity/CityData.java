package org.fro.common.widgets.locationview.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class CityData implements Serializable {
    private String type;
    private String name;
    private int id;
    private ArrayList<CountryData> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CountryData> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<CountryData> children) {
        this.children = children;
    }
}
