package com.lsn.LoadSensing.element;

import android.location.Location;


public class Position
{
	private Double latitude;
	private Double longitude;
	
	public Position() {
		super();
		this.latitude = 0.0;
		this.longitude = 0.0;
	}
	
	public Position(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Position(Location location) {
		super();
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public void setPosition(Location location)
	{
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}
	
	public float milesDistanceTo(Position position)
	{
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(position.latitude - latitude);
		double dLon = Math.toRadians(position.longitude - longitude);
		
		double a = Math.sin(dLat/2)*Math.sin(dLat/2) +
				Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(position.latitude)) *
				Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double dist = earthRadius * c;
		
		return new Float(dist).floatValue();
	}
	
	public float metersDistanceTo(Position position)
	{
		double dist = milesDistanceTo(position);
		int meterConversion = 1609;

		return new Float(dist*meterConversion).floatValue();
	}
}
