
package com.si.directory_app.models.search;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SearchingModel {

    @SerializedName("data")
    private List<SearchModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public List<SearchModel> getData() {
        return mData;
    }

    public void setData(List<SearchModel> data) {
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
