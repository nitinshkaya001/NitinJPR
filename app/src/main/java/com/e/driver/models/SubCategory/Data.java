
package com.e.driver.models.SubCategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Service_List")
    @Expose
    private List<ServiceList> serviceList = null;

    public List<ServiceList> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceList> serviceList) {
        this.serviceList = serviceList;
    }

}
