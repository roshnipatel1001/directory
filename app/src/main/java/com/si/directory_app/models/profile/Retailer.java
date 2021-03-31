
package com.si.directory_app.models.profile;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Retailer {

    @SerializedName("access")
    private String mAccess;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private String mId;
    @SerializedName("profile_img")
    private String mProfileImg;
    @SerializedName("rating")
    private String mRating;
    @SerializedName("substatus")
    private Long mSubstatus;

    public String getAccess() {
        return mAccess;
    }

    public void setAccess(String access) {
        mAccess = access;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getProfileImg() {
        return mProfileImg;
    }

    public void setProfileImg(String profileImg) {
        mProfileImg = profileImg;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public Long getSubstatus() {
        return mSubstatus;
    }

    public void setSubstatus(Long substatus) {
        mSubstatus = substatus;
    }

}
