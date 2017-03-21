package cn.chenhaonee.hostelWorld.domain;

/**
 * Created by nichenhao on 2017/3/20.
 */
public class TTO {
    private String name;
    private int value;

    public TTO(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TTO() {
    }
}
