
package com.e.driver.models.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceList {

    @SerializedName("service_list_id")
    @Expose
    private String serviceListId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getServiceListId() {
        return serviceListId;
    }

    public void setServiceListId(String serviceListId) {
        this.serviceListId = serviceListId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
