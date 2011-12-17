package com.lsn.LoadSensing.element;

public class LSNetwork {
	
	private String networkSituation;
	private String networkName;
	private String networkId;
	private Position networkPosition = new Position();
	private Integer networkNumSensors;
	
	public String getNetworkName() {
		return networkName;
	}
	
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkSituation() {
		return networkSituation;
	}
	
	public void setNetworkSituation(String networkSituation) {
		this.networkSituation = networkSituation;
	}

	public Integer getNetworkNumSensors() {
		return networkNumSensors;
	}

	public void setNetworkNumSensors(Integer networkNumber) {
		this.networkNumSensors = networkNumber;
	}
	
	public void setNetworkNumSensors(String networkNumber) {
		this.networkNumSensors = Integer.parseInt(networkNumber);
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Position getNetworkPosition() {
		return networkPosition;
	}

	public void setNetworkPosition(Position networkPosition) {
		this.networkPosition = networkPosition;
	}
	
	public void setNetworkPosition(String lat, String lon) {
		
		lat=lat.replace(',', '.');
		lon=lon.replace(',', '.');
		this.networkPosition.setLatitude(Double.parseDouble(lat));
		
		
		this.networkPosition.setLongitude(Double.parseDouble(lon));
	}
	
	public void setNetworkPosition(Double lat, Double lon) {
		this.networkPosition.setLatitude(lat);
		this.networkPosition.setLongitude(lon);
	}
}
