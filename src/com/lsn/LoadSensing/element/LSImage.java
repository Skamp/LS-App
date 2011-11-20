package com.lsn.LoadSensing.element;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class LSImage {
	
	private Drawable imageDrawable;
	private Bitmap imageBitmap;
	private String imageName;
	private String imageNetwork;
	private String imageSituation;
	
	public Drawable getImageDrawable() {
		return imageDrawable;
	}

	public void setImageDrawable(Drawable imageDrawable) {
		this.imageDrawable = imageDrawable;
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

	public void setImageBitmap(Bitmap imageBitmap) {
		this.imageBitmap = imageBitmap;
	}
	

	
}
