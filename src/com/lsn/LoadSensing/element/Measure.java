package com.lsn.LoadSensing.element;

import android.os.Parcel;
import android.os.Parcelable;

public class Measure implements Parcelable {

	private Double measure;
	private String measureUnit;
	private Double maxLoad;
	private String maxLoadUnit;
	private Double sensitivity;
	private String sensitivityUnit;
	private Double offset;
	private String offsetUnit;
	private Double alarmAt;
	private String alarmAtUnit;
	private String lastTare;
	
	public Measure() {
		measure = 0.0;
		measureUnit = "";
		maxLoad = 0.0;
		maxLoadUnit  = "";
		sensitivity = 0.0;
		sensitivityUnit  = "";
		offset = 0.0;
		offsetUnit  = "";
		alarmAt = 0.0;
		alarmAtUnit  = "";
		lastTare  = "";
	}
	
	public Measure(Parcel in) {

		readFromParcel(in);
	}
	
	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Double getMaxLoad() {
		return maxLoad;
	}

	public void setMaxLoad(Double maxLoad) {
		this.maxLoad = maxLoad;
	}

	public String getMaxLoadUnit() {
		return maxLoadUnit;
	}

	public void setMaxLoadUnit(String maxLoadUnit) {
		this.maxLoadUnit = maxLoadUnit;
	}

	public Double getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(Double sensitivity) {
		this.sensitivity = sensitivity;
	}

	public String getSensitivityUnit() {
		return sensitivityUnit;
	}

	public void setSensitivityUnit(String sensitivityUnit) {
		this.sensitivityUnit = sensitivityUnit;
	}

	public Double getOffset() {
		return offset;
	}

	public void setOffset(Double offset) {
		this.offset = offset;
	}

	public String getOffsetUnit() {
		return offsetUnit;
	}

	public void setOffsetUnit(String offsetUnit) {
		this.offsetUnit = offsetUnit;
	}

	public Double getAlarmAt() {
		return alarmAt;
	}

	public void setAlarmAt(Double alarmAt) {
		this.alarmAt = alarmAt;
	}

	public String getAlarmAtUnit() {
		return alarmAtUnit;
	}

	public void setAlarmAtUnit(String alarmAtUnit) {
		this.alarmAtUnit = alarmAtUnit;
	}

	public String getLastTare() {
		return lastTare;
	}

	public void setLastTare(String lastTare) {
		this.lastTare = lastTare;
	}

	@Override
	public int describeContents() {
	
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeDouble(measure);
		dest.writeString(measureUnit);
		dest.writeDouble(maxLoad);
		dest.writeString(maxLoadUnit);
		dest.writeDouble(sensitivity);
		dest.writeString(sensitivityUnit);
		dest.writeDouble(offset);
		dest.writeString(offsetUnit);
		dest.writeDouble(alarmAt);
		dest.writeString(alarmAtUnit);
		dest.writeString(lastTare);
	}

	private void readFromParcel(Parcel in) {
		
		measure = in.readDouble();
		measureUnit = in.readString();
		maxLoad = in.readDouble();
		maxLoadUnit = in.readString();
		sensitivity = in.readDouble();
		sensitivityUnit = in.readString();
		offset =  in.readDouble();
		offsetUnit = in.readString();
		alarmAt = in.readDouble();
		alarmAtUnit = in.readString();
		lastTare = in.readString();
	}
	
	

	public static final Parcelable.Creator<Measure> CREATOR =
	    	new Parcelable.Creator<Measure>() {
	            public Measure createFromParcel(Parcel in) {
	                return new Measure(in);
	            }
	 
	            public Measure[] newArray(int size) {
	                return new Measure[size];
	            }
	        };
}
