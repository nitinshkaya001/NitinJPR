
package com.e.driver.models.LoginEmail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("User_Name")
    @Expose
    private String userName;
    @SerializedName("Role_ID")
    @Expose
    private String roleID;
    @SerializedName("Login_ID")
    @Expose
    private String loginID;
    @SerializedName("Customer_Type ID")
    @Expose
    private String customerTypeID;
    @SerializedName("Prime_End_Date")
    @Expose
    private String primeEndDate;
    @SerializedName("Total_Count")
    @Expose
    private String totalCount;
    @SerializedName("Mobile_No")
    @Expose
    private String mobileNo;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Landmark")
    @Expose
    private String landmark;
    @SerializedName("Pincode")
    @Expose
    private String pincode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getCustomerTypeID() {
        return customerTypeID;
    }

    public void setCustomerTypeID(String customerTypeID) {
        this.customerTypeID = customerTypeID;
    }

    public String getPrimeEndDate() {
        return primeEndDate;
    }

    public void setPrimeEndDate(String primeEndDate) {
        this.primeEndDate = primeEndDate;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}
