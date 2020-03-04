package com.ldlywt.androidadvance.bean;

/**
 * <pre>
 *     author : wutao
 *     time   : 2018/11/15
 *     desc   :
 * </pre>
 */
public class PieData {

    private String name;
    private float value;
    private float percentage;

    private int color = 0;
    private float angle = 0;

    public PieData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
