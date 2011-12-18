package com.lsn.LoadSensing.element;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class LSSensor implements Parcelable {
	
	private String sensorSituation;
	private String sensorName;
	private String sensorId;
	private String sensorNetwork;
	private Position sensorPosition;
	private String sensorChannel;
	private String sensorType;
	private Bitmap sensorImage;
	private String sensorDesc;

	public LSSensor() {

		sensorSituation = "";
		sensorName = "";
		sensorId = "";
		sensorNetwork = "";
		sensorPosition = new Position();
		sensorChannel = "";
		sensorType = "";
		sensorImage = null;
		sensorDesc = "";
	}
	
	public LSSensor(Parcel in) {

		sensorPosition = new Position();
		readFromParcel(in);
	}
	
	public String getSensorName() {
		return sensorName;
	}
	
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorSituation() {
		return sensorSituation;
	}
	
	public void setSensorSituation(String sensorSituation) {
		this.sensorSituation = sensorSituation;
	}
	
	public String getSensorId() {
		return sensorId;
	}
	
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorNetwork() {
		return sensorNetwork;
	}

	public void setSensorNetwork(String sensorNetwork) {
		this.sensorNetwork = sensorNetwork;
	}
	
	public Position getSensorPosition() {
		return sensorPosition;
	}

	public void setSensorPosition(Position sensorPosition) {
		this.sensorPosition = sensorPosition;
	}

	public String getSensorChannel() {
		return sensorChannel;
	}

	public void setSensorChannel(String sensorChannel) {
		this.sensorChannel = sensorChannel;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public Bitmap getSensorImage() {
		return sensorImage;
	}

	public void setSensorImage(Bitmap sensorImage) {
		this.sensorImage = sensorImage;
	}

	public String getSensorDesc() {
		return sensorDesc;
	}

	public void setSensorDesc(String sensorDesc) {
		this.sensorDesc = sensorDesc;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(sensorSituation);
		dest.writeString(sensorName);
		dest.writeString(sensorId);
		dest.writeString(sensorNetwork);
		dest.writeParcelable(sensorPosition, flags);
		dest.writeString(sensorChannel);
		dest.writeString(sensorType);
		dest.writeParcelable(sensorImage,flags);
		dest.writeString(sensorDesc);
	}

	private void readFromParcel(Parcel in) {
		
		sensorSituation = in.readString();
		sensorName = in.readString();
		sensorId = in.readString();
		sensorNetwork = in.readString();
		sensorPosition = in.readParcelable(Position.class.getClassLoader());
		sensorChannel = in.readString();
		sensorType = in.readString();
		sensorImage = in.readParcelable(Bitmap.class.getClassLoader());
		sensorDesc = in.readString();
	}
	
	public static final Parcelable.Creator<LSSensor> CREATOR =
	    	new Parcelable.Creator<LSSensor>() {
	            public LSSensor createFromParcel(Parcel in) {
	                return new LSSensor(in);
	            }
	 
	            public LSSensor[] newArray(int size) {
	                return new LSSensor[size];
	            }
	        };
}
