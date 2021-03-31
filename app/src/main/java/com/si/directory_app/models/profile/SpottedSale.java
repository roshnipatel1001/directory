
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SpottedSale {

    @SerializedName("end_date")
    private String mEndDate;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("sales")
    private String mSales;
    @SerializedName("saletype")
    private String mSaletype;

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getSales() {
        return mSales;
    }

    public void setSales(String sales) {
        mSales = sales;
    }

    public String getSaletype() {
        return mSaletype;
    }

    public void setSaletype(String saletype) {
        mSaletype = saletype;
    }

}
