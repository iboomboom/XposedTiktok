package com.evayinfo.xposedtt.bean;

public class CountryInfo {
    private String areaName;
    private String areaIso;
    private String areaPhone;

    public CountryInfo(String areaName, String areaIso, String areaPhone) {
        this.areaName = areaName;
        this.areaIso = areaIso;
        this.areaPhone = areaPhone;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaIso() {
        return areaIso;
    }

    public void setAreaIso(String areaIso) {
        this.areaIso = areaIso;
    }

    public String getAreaPhone() {
        return areaPhone;
    }

    public void setAreaPhone(String areaPhone) {
        this.areaPhone = areaPhone;
    }
}
