
package com.si.directory_app.models.sub_list;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class SubCategoryModel {

    @SerializedName("cat_image")
    private String mCatImage;
    @SerializedName("category")
    private String mCategory;
    @SerializedName("created_by")
    private String mCreatedBy;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("id")
    private String mId;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated_by")
    private String mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("substatus")
    private Long msubstatus;

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

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
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

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }


    public Long getSubstatus() {
        return msubstatus;
    }

    public void setSubstatus(Long substatus) {
        msubstatus = substatus;
    }

    @Override
    public String toString() {
        return "SubCategoryModel{" +
                "mCatImage='" + mCatImage + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mCreatedBy='" + mCreatedBy + '\'' +
                ", mCreatedOn='" + mCreatedOn + '\'' +
                ", mId='" + mId + '\'' +
                ", mSlug='" + mSlug + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mUpdatedBy='" + mUpdatedBy + '\'' +
                ", mUpdatedOn='" + mUpdatedOn + '\'' +
                ", mUserId='" + mUserId + '\'' +
                ", msubstatus='" + msubstatus + '\'' +
                '}';
    }
}
