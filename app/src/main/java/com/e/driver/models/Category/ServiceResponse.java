
package com.e.driver.models.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse {

    @SerializedName("Status_Type")
    @Expose
    private String statusType;
    @SerializedName("Data")
    @Expose
    private Data data;

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
