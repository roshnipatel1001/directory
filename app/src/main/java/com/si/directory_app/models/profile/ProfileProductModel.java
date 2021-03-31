
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ProfileProductModel {

    @SerializedName("cat_image")
    private String mCatImage;
    @SerializedName("category")
    private String mCategory;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;

    public String getCatImage() {
        return mCatImage;
    }

    public void setCatImage(String catImage) {
        mCatImage = catImage;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
