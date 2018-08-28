package com.evayinfo.xposedtt.bean;

public class CountryInfo {
    private String countryName;
    private String countryIso;
    private String countryPhone;

    public CountryInfo(String countryName, String countryIso, String countryPhone) {
        this.countryName = countryName;
        this.countryIso = countryIso;
        this.countryPhone = countryPhone;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public String getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(String countryPhone) {
        this.countryPhone = countryPhone;
    }
}
