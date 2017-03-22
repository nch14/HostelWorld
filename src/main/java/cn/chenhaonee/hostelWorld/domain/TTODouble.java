package cn.chenhaonee.hostelWorld.domain;

/**
 * Created by nichenhao on 2017/3/22.
 */
public class TTODouble {
    private String innId;
    private String nameToShow;
    private double value;

    public String getInnId() {
        return innId;
    }

    public void setInnId(String innId) {
        this.innId = innId;
    }

    public TTODouble(String innId, String nameToShow, double value) {

        this.innId = innId;
        this.nameToShow = nameToShow;
        this.value = value;
    }

    public TTODouble() {

    }

    public String getNameToShow() {
        return nameToShow;
    }

    public void setNameToShow(String nameToShow) {
        this.nameToShow = nameToShow;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
