package cn.chenhaonee.hostelWorld.domain;

/**
 * Created by nichenhao on 2017/3/22.
 */
public class BinaryDataDouble {
    private String name;
    private double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BinaryDataDouble(String name, double value) {

        this.name = name;
        this.value = value;
    }

    public BinaryDataDouble() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
