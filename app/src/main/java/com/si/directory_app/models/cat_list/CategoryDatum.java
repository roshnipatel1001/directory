
package com.si.directory_app.models.cat_list;

import com.google.gson.annotations.SerializedName;


public class CategoryDatum {

    @SerializedName("cat_image")
    private String mCatImage;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("category")
    private String mCategory;
    @SerializedName("created_by")
    private String mCreatedBy;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("id")
    private String mId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated_by")
    private String mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;

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

    public void setmUserId(String userId) {
        mUserId = userId;
    }

    public String getmUserId() {
        return mUserId;
    }



    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        mCreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }


    @Override
    public String toString() {
        return "CategoryDatum{" +
                "mCatImage='" + mCatImage + '\'' +
                ", mUserId='" + mUserId + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mCreatedBy='" + mCreatedBy + '\'' +
                ", mCreatedOn='" + mCreatedOn + '\'' +
                ", mId='" + mId + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mUpdatedBy='" + mUpdatedBy + '\'' +
                ", mUpdatedOn='" + mUpdatedOn + '\'' +
                '}';
    }
}
