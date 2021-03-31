
package com.si.directory_app.models.cat_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class CategoryModel {

    @SerializedName("data")
    private List<CategoryDatum> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public List<CategoryDatum> getData() {
        return mData;
    }

    public void setData(List<CategoryDatum> data) {
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
