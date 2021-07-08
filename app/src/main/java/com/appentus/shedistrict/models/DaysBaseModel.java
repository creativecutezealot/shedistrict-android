package com.appentus.shedistrict.models;

import java.util.List;

public class DaysBaseModel {

    private String daysType;

    private List<EventBean.AttendingBean>listResult;

    public DaysBaseModel(String daysType, List<EventBean.AttendingBean> listResult) {
        this.daysType = daysType;
        this.listResult = listResult;
    }

    public String getDaysType() {
        return daysType;
    }

    public void setDaysType(String daysType) {
        this.daysType = daysType;
    }

    public List<EventBean.AttendingBean> getListResult() {
        return listResult;
    }

    public void setListResult(List<EventBean.AttendingBean> listResult) {
        this.listResult = listResult;
    }
}
