
package com.si.directory_app.models.login;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("access")
    private String mAccess;
    @SerializedName("activation_code")
    private Object mActivationCode;
    @SerializedName("activation_selector")
    private Object mActivationSelector;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("contact_number")
    private Object mContactNumber;
    @SerializedName("created_by")
    private String mCreatedBy;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("forgotten_password_code")
    private Object mForgottenPasswordCode;
    @SerializedName("forgotten_password_selector")
    private Object mForgottenPasswordSelector;
    @SerializedName("forgotten_password_time")
    private Object mForgottenPasswordTime;
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private String mId;
    @SerializedName("last_access")
    private String mLastAccess;
    @SerializedName("last_login")
    private String mLastLogin;
    @SerializedName("otp")
    private String mOtp;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("profile_img")
    private String mProfileImg;
    @SerializedName("remember_code")
    private Object mRememberCode;
    @SerializedName("remember_selector")
    private Object mRememberSelector;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("updated_by")
    private String mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;
    @SerializedName("username")
    private String mUsername;

    public String getAccess() {
        return mAccess;
    }

    public void setAccess(String access) {
        mAccess = access;
    }

    public Object getActivationCode() {
        return mActivationCode;
    }

    public void setActivationCode(Object activationCode) {
        mActivationCode = activationCode;
    }

    public Object getActivationSelector() {
        return mActivationSelector;
    }

    public void setActivationSelector(Object activationSelector) {
        mActivationSelector = activationSelector;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Object getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(Object contactNumber) {
        mContactNumber = contactNumber;
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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Object getForgottenPasswordCode() {
        return mForgottenPasswordCode;
    }

    public void setForgottenPasswordCode(Object forgottenPasswordCode) {
        mForgottenPasswordCode = forgottenPasswordCode;
    }

    public Object getForgottenPasswordSelector() {
        return mForgottenPasswordSelector;
    }

    public void setForgottenPasswordSelector(Object forgottenPasswordSelector) {
        mForgottenPasswordSelector = forgottenPasswordSelector;
    }

    public Object getForgottenPasswordTime() {
        return mForgottenPasswordTime;
    }

    public void setForgottenPasswordTime(Object forgottenPasswordTime) {
        mForgottenPasswordTime = forgottenPasswordTime;
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

    public String getLastAccess() {
        return mLastAccess;
    }

    public void setLastAccess(String lastAccess) {
        mLastAccess = lastAccess;
    }

    public String getLastLogin() {
        return mLastLogin;
    }

    public void setLastLogin(String lastLogin) {
        mLastLogin = lastLogin;
    }

    public String getOtp() {
        return mOtp;
    }

    public void setOtp(String otp) {
        mOtp = otp;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getProfileImg() {
        return mProfileImg;
    }

    public void setProfileImg(String profileImg) {
        mProfileImg = profileImg;
    }

    public Object getRememberCode() {
        return mRememberCode;
    }

    public void setRememberCode(Object rememberCode) {
        mRememberCode = rememberCode;
    }

    public Object getRememberSelector() {
        return mRememberSelector;
    }

    public void setRememberSelector(Object rememberSelector) {
        mRememberSelector = rememberSelector;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
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

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    @Override
    public String toString() {
        return "ProfileData{" +
                "mAccess='" + mAccess + '\'' +
                ", mActivationCode=" + mActivationCode +
                ", mActivationSelector=" + mActivationSelector +
                ", mAddress='" + mAddress + '\'' +
                ", mContactNumber=" + mContactNumber +
                ", mCreatedBy='" + mCreatedBy + '\'' +
                ", mCreatedOn='" + mCreatedOn + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mForgottenPasswordCode=" + mForgottenPasswordCode +
                ", mForgottenPasswordSelector=" + mForgottenPasswordSelector +
                ", mForgottenPasswordTime=" + mForgottenPasswordTime +
                ", mFullName='" + mFullName + '\'' +
                ", mId='" + mId + '\'' +
                ", mLastAccess='" + mLastAccess + '\'' +
                ", mLastLogin='" + mLastLogin + '\'' +
                ", mOtp='" + mOtp + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mProfileImg='" + mProfileImg + '\'' +
                ", mRememberCode=" + mRememberCode +
                ", mRememberSelector=" + mRememberSelector +
                ", mStatus='" + mStatus + '\'' +
                ", mUpdatedBy='" + mUpdatedBy + '\'' +
                ", mUpdatedOn='" + mUpdatedOn + '\'' +
                ", mUsername='" + mUsername + '\'' +
                '}';
    }
}
