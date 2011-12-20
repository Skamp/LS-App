package com.lsn.LoadSensing.element;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class LSImage implements Parcelable {
	
	private Bitmap imageBitmap;
	private String imageName;
	private String imageId;
	private String imageNetwork;
	private String imageSituation;
	
	public LSImage() {

		imageBitmap = null;
		imageName = "";
		imageNetwork = "";
		imageSituation= "";
	}
	
	public LSImage(Parcel in) {

		readFromParcel(in);
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getImageNetwork() {
		return imageNetwork;
	}
	
	public void setImageNetwork(String imageNetwork) {
		this.imageNetwork = imageNetwork;
	}
	
	public String getImageSituation() {
		return imageSituation;
	}
	
	public void setImageSituation(String imageSituation) {
		this.imageSituation = imageSituation;
	}

	public Bitmap getImageBitmap() {
		return imageBitmap;
	}
	
	public Bitmap getImageThumb(int size) {
		return Bitmap.createScaledBitmap(imageBitmap, size, size, false);
	}

	public Bitmap getImageThumb(int sizeX, int sizeY) {
		return Bitmap.createScaledBitmap(imageBitmap, sizeX, sizeY, false);
	}
	
	public void setImageBitmap(Bitmap imageBitmap) {
		this.imageBitmap = imageBitmap;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Override
	public int describeContents() {
		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeParcelable(imageBitmap, flags);
		dest.writeString(imageName);
		dest.writeString(imageId);
		dest.writeString(imageNetwork);
		dest.writeString(imageSituation);
	}
	
	private void readFromParcel(Parcel in) {
			
		imageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
		imageName = in.readString();
		imageId = in.readString();
		imageNetwork = in.readString();
		imageSituation = in.readString();
	}
	
	public static final Parcelable.Creator<LSImage> CREATOR =
	    	new Parcelable.Creator<LSImage>() {
	            public LSImage createFromParcel(Parcel in) {
	                return new LSImage(in);
	            }
	 
	            public LSImage[] newArray(int size) {
	                return new LSImage[size];
	            }
	        };
	
}
