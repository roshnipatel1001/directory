
package com.si.directory_app.models.liked_cat;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class LikedCatModel {

    @SerializedName("data")
    private List<LikedCategoryModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public List<LikedCategoryModel> getData() {
        return mData;
    }

    public void setData(List<LikedCategoryModel> data) {
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
