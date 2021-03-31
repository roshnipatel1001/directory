
package com.si.directory_app.models.registration;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegistrationModel {

    @SerializedName("data")
    private Data mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
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

    @Override
    public String toString() {
        return "RegistrationModel{" +
                "mData=" + mData +
                ", mMessage='" + mMessage + '\'' +
                ", mStatus=" + mStatus +
                '}';
    }
}
