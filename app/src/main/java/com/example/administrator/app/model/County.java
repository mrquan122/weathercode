package com.example.administrator.app.model;

/**
 * Created by Administrator on 2016/3/4.
 */
public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String provinceName) {
        this.countyName = provinceName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String provinceCode) {
        this.countyCode = provinceCode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

}
