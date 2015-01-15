package com.bhavdip.coupondunia.model;

import java.util.List;

public class DataModel {

	String SubFranchiseID;
	String OutletID;
	String OutletName;
	String BrandID;
	String Address;
	String NeighbourhoodID;

	String CityID;
	String Email;
	String Timings;
	String CityRank;
	String Latitude;
	String Longitude;

	String Pincode;
	String Landmark;
	String Streetname;
	String BrandName;
	String OutletURL;

	String NeighbourhoodName;
	String PhoneNumber;
	String CityName;
	String LogoURL;
	String CoverURL;

	int NumCoupons;
	float Distance;

	List<CategoryModel> category;

	public DataModel() {
	}

	public String getSubFranchiseID() {
		return SubFranchiseID;
	}

	public void setSubFranchiseID(String subFranchiseID) {
		SubFranchiseID = subFranchiseID;
	}

	public String getOutletID() {
		return OutletID;
	}

	public void setOutletID(String outletID) {
		OutletID = outletID;
	}

	public String getOutletName() {
		return OutletName;
	}

	public void setOutletName(String outletName) {
		OutletName = outletName;
	}

	public String getBrandID() {
		return BrandID;
	}

	public void setBrandID(String brandID) {
		BrandID = brandID;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getNeighbourhoodID() {
		return NeighbourhoodID;
	}

	public void setNeighbourhoodID(String neighbourhoodID) {
		NeighbourhoodID = neighbourhoodID;
	}

	public String getCityID() {
		return CityID;
	}

	public void setCityID(String cityID) {
		CityID = cityID;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getTimings() {
		return Timings;
	}

	public void setTimings(String timings) {
		Timings = timings;
	}

	public String getCityRank() {
		return CityRank;
	}

	public void setCityRank(String cityRank) {
		CityRank = cityRank;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPincode() {
		return Pincode;
	}

	public void setPincode(String pincode) {
		Pincode = pincode;
	}

	public String getLandmark() {
		return Landmark;
	}

	public void setLandmark(String landmark) {
		Landmark = landmark;
	}

	public String getStreetname() {
		return Streetname;
	}

	public void setStreetname(String streetname) {
		Streetname = streetname;
	}

	public String getBrandName() {
		return BrandName;
	}

	public void setBrandName(String brandName) {
		BrandName = brandName;
	}

	public String getOutletURL() {
		return OutletURL;
	}

	public void setOutletURL(String outletURL) {
		OutletURL = outletURL;
	}

	public int getNumCoupons() {
		return NumCoupons;
	}

	public void setNumCoupons(int numCoupons) {
		NumCoupons = numCoupons;
	}

	public String getNeighbourhoodName() {
		return NeighbourhoodName;
	}

	public void setNeighbourhoodName(String neighbourhoodName) {
		NeighbourhoodName = neighbourhoodName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getLogoURL() {
		return LogoURL;
	}

	public void setLogoURL(String logoURL) {
		LogoURL = logoURL;
	}

	public String getCoverURL() {
		return CoverURL;
	}

	public void setCoverURL(String coverURL) {
		CoverURL = coverURL;
	}

	public float getDistance() {
		return Distance;
	}

	public void setDistance(float distance) {
		Distance = distance;
	}

	public List<CategoryModel> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryModel> category) {
		this.category = category;
	}

}
