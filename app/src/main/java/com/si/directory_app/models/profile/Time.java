
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class Time {

    @SerializedName("closing_time")
    private String mClosingTime;
    @SerializedName("day")
    private String mDay;
    @SerializedName("id")
    private String mId;
    @SerializedName("opening_time")
    private String mOpeningTime;
    @SerializedName("status")
    private String mStatus;

    public String getClosingTime() {
        return mClosingTime;
    }

    public void setClosingTime(String closingTime) {
        mClosingTime = closingTime;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOpeningTime() {
        return mOpeningTime;
    }

    public void setOpeningTime(String openingTime) {
        mOpeningTime = openingTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
