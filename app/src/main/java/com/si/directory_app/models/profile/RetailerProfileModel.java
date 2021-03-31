
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class RetailerProfileModel {

    @SerializedName("data")
    private ProfileData mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public ProfileData getData() {
        return mData;
    }

    public void setData(ProfileData data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
