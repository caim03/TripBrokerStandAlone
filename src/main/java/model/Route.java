package model;

import model.entityDB.ViaggioEntity;

import java.util.ArrayList;
import java.util.List;

public class Route extends ArrayList<ViaggioEntity> {

    private String value;

    public Route(List<ViaggioEntity> entities, String value) {
        super(entities);
        setValue(value);
    }

    public void setValue(String value) { this.value = value; }
    public String getValue() { return this.value; }
}
