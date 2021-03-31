
package com.si.directory_app.models.liked_retailer;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class LikeRetailModel {

    @SerializedName("data")
    private List<LikedRetailerModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public List<LikedRetailerModel> getData() {
        return mData;
    }

    public void setData(List<LikedRetailerModel> data) {
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
