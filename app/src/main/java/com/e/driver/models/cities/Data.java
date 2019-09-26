package com.e.driver.models.cities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("City_List")
@Expose
private List<CityList> cityList = null;

public List<CityList> getCityList() {
return cityList;
}

public void setCityList(List<CityList> cityList) {
this.cityList = cityList;
}

}