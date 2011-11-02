package com.lsn.LoadSensing.ui;

import com.lsn.LoadSensing.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast  {

	
	public static final int LENGTH_SHORT=0;
	public static final int LENGTH_LONG=1;
	
	public static final int IMG_AWARE       = R.drawable.ic_toast_aware;
	public static final int IMG_CORRECT     = R.drawable.ic_toast_correct;
	public static final int IMG_ERROR       = R.drawable.ic_toast_error;
	public static final int IMG_EXCLAMATION = R.drawable.ic_toast_exclamation;
	public static final int IMG_FAVORITE    = R.drawable.ic_toast_favorite;
	public static final int IMG_HELP        = R.drawable.ic_toast_help;
	public static final int IMG_INFORMATION = R.drawable.ic_toast_information;
	public static final int IMG_QUESTION    = R.drawable.ic_toast_question;
	
	private static TextView txtToast;
	private static ImageView imgToast;
	
	public static void showCustomToast(Activity context,String strMessage,int duration) {
		
		
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.customtoast, null);
		//ViewGroup layToast = (ViewGroup)layout.findViewById(R.id.layoutToast);
		
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(strMessage);
		
		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
		//layToast.bringToFront();
	}
	
	
	public static void showCustomToast(Activity context,int intMessage,int duration) {
		
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.customtoast, null);
		//ViewGroup layToast = (ViewGroup)layout.findViewById(R.id.layoutToast);
		
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(context.getResources().getText(intMessage));
			
		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
		//layToast.bringToFront();
	}
	
	
	public static void showCustomToast(Activity context,String strMessage,int intDrawable,int duration) {
		
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.customtoast, null);
		//ViewGroup layToast = (ViewGroup)layout.findViewById(R.id.layoutToast);
		
		imgToast = (ImageView)layout.findViewById(R.id.imgToast);
		imgToast.setImageDrawable(context.getResources().getDrawable(intDrawable));
		imgToast.setVisibility(View.VISIBLE);
		
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(strMessage);
		
		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
		//layToast.bringToFront();
	}
	
	public static void showCustomToast(Activity context,int intMessage,int intDrawable,int duration) {
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.customtoast, null);
		//ViewGroup layToast = (ViewGroup)layout.findViewById(R.id.layoutToast);
		
		imgToast = (ImageView)layout.findViewById(R.id.imgToast);
		imgToast.setImageDrawable(context.getResources().getDrawable(intDrawable));
		imgToast.setVisibility(View.VISIBLE);
		
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(context.getResources().getText(intMessage));
		
		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setView(layout);
		toast.show();
		//layToast.bringToFront();
	
	}
	
	
	
}
