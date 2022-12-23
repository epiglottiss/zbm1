package pack;

import com.google.gson.annotations.SerializedName;

public class WiFi {
	//(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year,
	// in_out_door, wifi_environment, lat, lnt, work_date)
	
	@SerializedName("X_SWIFI_MGR_NO")
	String id;
	
	@SerializedName("X_SWIFI_WRDOFC")
	String goo="";
	
	@SerializedName("X_SWIFI_MAIN_NM")
	String name="";
	
	@SerializedName("X_SWIFI_ADRES1")
	String dorojuso="";
	
	@SerializedName("X_SWIFI_ADRES2")
	String detailjuso="";
	
	@SerializedName("X_SWIFI_INSTL_FLOOR")
	String floor="";
	
	@SerializedName("X_SWIFI_INSTL_TY")
	String install_type="";
	
	@SerializedName("X_SWIFI_INSTL_MBY")
	String gigwan="";
	
	@SerializedName("X_SWIFI_SVC_SE")
	String service_guboon="";
	
	@SerializedName("X_SWIFI_CMCWR")
	String mang_type="";
	
	@SerializedName("X_SWIFI_CNSTC_YEAR")
	String install_year;
	
	@SerializedName("X_SWIFI_INOUT_DOOR")
	String in_out_door="";
	
	@SerializedName("X_SWIFI_REMARS3")
	String wifi_environment="";
	
	@SerializedName("LAT")
	double lat;
	
	@SerializedName("LNT")
	double lnt;
	
	@SerializedName("WORK_DTTM")
	String work_date="";
	
	double distance;
	
	
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoo() {
		return goo;
	}
	public void setGoo(String goo) {
		this.goo = goo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDorojuso() {
		return dorojuso;
	}
	public void setDorojuso(String dorojuso) {
		this.dorojuso = dorojuso;
	}
	public String getDetailjuso() {
		return detailjuso;
	}
	public void setDetailjuso(String detailjuso) {
		this.detailjuso = detailjuso;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getInstall_type() {
		return install_type;
	}
	public void setInstall_type(String install_type) {
		this.install_type = install_type;
	}
	public String getGigwan() {
		return gigwan;
	}
	public void setGigwan(String gigwan) {
		this.gigwan = gigwan;
	}
	public String getService_guboon() {
		return service_guboon;
	}
	public void setService_guboon(String service_guboon) {
		this.service_guboon = service_guboon;
	}
	public String getMang_type() {
		return mang_type;
	}
	public void setMang_type(String mang_type) {
		this.mang_type = mang_type;
	}
	public String getInstall_year() {
		return install_year;
	}
	public void setInstall_year(String install_year) {
		this.install_year = install_year;
	}
	public String getIn_out_door() {
		return in_out_door;
	}
	public void setIn_out_door(String in_out_door) {
		this.in_out_door = in_out_door;
	}
	public String getWifi_environment() {
		return wifi_environment;
	}
	public void setWifi_environment(String wifi_environment) {
		this.wifi_environment = wifi_environment;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLnt() {
		return lnt;
	}
	public void setLnt(double lnt) {
		this.lnt = lnt;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	
	
	
	
	
}
