package com.flyco.tablayout;

import android.text.TextUtils;

/**
 * author : wutao
 * e-mail : wutao@himango.cn
 * time   : 2019/06/17
 * desc   :
 * version: 1.0
 */
public class TabItemBean {

    private String titleColor = "#d9000000";
    private String titleSelectedColor = "#66000000";
    private String title = "";
    private String indicatorColor = "#FF4FCAA4";

    public TabItemBean() {
    }

    public TabItemBean(String titleColor, String titleSelectedColor, String title, String indicatorColor) {
        this.titleColor = TextUtils.isEmpty(indicatorColor) ? "#d9000000" : titleColor;
        this.titleSelectedColor = TextUtils.isEmpty(indicatorColor) ? "#66000000" : titleSelectedColor;
        this.title = title;
        this.indicatorColor = TextUtils.isEmpty(indicatorColor) ? "#FF4FCAA4" : indicatorColor;
    }


    public String getTitleColor() {
        return titleColor == null ? "" : titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getTitleSelectedColor() {
        return titleSelectedColor == null ? "" : titleSelectedColor;
    }

    public void setTitleSelectedColor(String titleSelectedColor) {
        this.titleSelectedColor = titleSelectedColor;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndicatorColor() {
        return indicatorColor == null ? "" : indicatorColor;
    }

    public void setIndicatorColor(String indicatorColor) {
        this.indicatorColor = indicatorColor;
    }
}
