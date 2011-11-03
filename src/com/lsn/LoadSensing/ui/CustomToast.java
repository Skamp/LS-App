package com.lsn.LoadSensing.ui;

import com.lsn.LoadSensing.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
//public class CustomToast extends Toast {

//	//Length Definitions
//	public static final int LENGTH_SHORT = 0;
//	public static final int LENGTH_LONG  = 1;
//
//	//Image Resources
//	public static final int IMG_AWARE       = R.drawable.ic_toast_aware;
//	public static final int IMG_CORRECT     = R.drawable.ic_toast_correct;
//	public static final int IMG_ERROR       = R.drawable.ic_toast_error;
//	public static final int IMG_EXCLAMATION = R.drawable.ic_toast_exclamation;
//	public static final int IMG_FAVORITE    = R.drawable.ic_toast_favorite;
//	public static final int IMG_HELP        = R.drawable.ic_toast_help;
//	public static final int IMG_INFORMATION = R.drawable.ic_toast_information;
//	public static final int IMG_QUESTION    = R.drawable.ic_toast_question;
//
//	//Custom Toast elements
//	private Context context;
//	
//	
//	//private static TextView  txtToast;
//	//private static ImageView imgToast;
//	//private static Toast     curToast;
//	//private static View      layout;
//	
//	
//	
//	public CustomToast(Context context) {
//		super(context);
//	}
//	
//	@Override
//	public void setDuration(int duration) {
//		
//		super.setDuration(duration);
//	}
//	
//	@Override
//	public void setGravity(int gravity, int xOffset, int yOffset) {
//		
//		super.setGravity(gravity, xOffset, yOffset);
//	}
//	
//	@Override
//	public void setText(CharSequence s) {
//		
//		super.setText(s);
//	}
//	
//	@Override
//	public void setText(int resId) {
//		
//		super.setText(resId);
//	}
//	
//	@Override
//	public void setView(View view) {
//		
//		super.setView(view);
//	}
//	
//	@Override
//	public void show() {
//		super.show();
//	}



	//Length Definitions
	public static final int LENGTH_SHORT = 0;
	public static final int LENGTH_LONG  = 1;

	//Image Resources
	public static final int IMG_AWARE       = R.drawable.ic_toast_aware;
	public static final int IMG_CORRECT     = R.drawable.ic_toast_correct;
	public static final int IMG_ERROR       = R.drawable.ic_toast_error;
	public static final int IMG_EXCLAMATION = R.drawable.ic_toast_exclamation;
	public static final int IMG_FAVORITE    = R.drawable.ic_toast_favorite;
	public static final int IMG_HELP        = R.drawable.ic_toast_help;
	public static final int IMG_INFORMATION = R.drawable.ic_toast_information;
	public static final int IMG_QUESTION    = R.drawable.ic_toast_question;

	//Custom Toast elements
	private static TextView  txtToast;
	private static ImageView imgToast;
	private static Toast     curToast;
	private static View      layout;
	
	/**
	 * Inflate Custom Toast Layout
	 * @param context: Activity context where the Custom Toast will be shown
	 */
	private static void inflateToastLayout(Activity context) {
		
		LayoutInflater inflater = context.getLayoutInflater();
		layout = inflater.inflate(R.layout.customtoast, null);
	}
	
	/**
	 * Generate and show Custom Toast
	 * @param context: Activity context where the Custom Toast will be shown
	 * @param duration: Show the view or text notification for a long period of time. This time could be user-definable.
	 */
	private static void generateToast(Activity context, int duration) {
		
		curToast = new Toast(context);
		curToast.setDuration(duration);
		curToast.setView(layout);
		curToast.show();
	}
	
	/**
	 * Customizes Toast information (String message) and shows Custom Toast
	 * @param context: Activity context where the Custom Toast will be shown
	 * @param strMessage: String message to be shown in Custom Toast
	 * @param duration: Show the view or text notification for a long period of time. This time could be user-definable.
	 */
	public static void showCustomToast(Activity context,String strMessage,int duration) {
		
		// Inflate Custom Toast layout
		inflateToastLayout(context);
		
		// Set Text of Custom Toast
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(strMessage);
		
		// Generate Custom Toast
		generateToast(context, duration);
	}
	
	/**
	 * Customizes Toast information (String resource) and shows Custom Toast 
	 * @param context: Activity context where the Custom Toast will be shown
	 * @param intMessage: String resource to be shown in Custom Toast
	 * @param duration: Show the view or text notification for a long period of time. This time could be user-definable.
	 */
	public static void showCustomToast(Activity context,int intMessage,int duration) {
		
		// Inflate Custom Toast layout
		inflateToastLayout(context);
		
		// Set Text of Custom Toast
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(context.getResources().getText(intMessage));
			
		// Generate Custom Toast
		generateToast(context, duration);
	}
	
	/**
	 * Customizes Toast information (String message and Drawable resource) and show Custom Toast
	 * @param context: Activity context where the Custom Toast will be shown
	 * @param strMessage: String message to be shown in Custom Toast
	 * @param intDrawable: Drawable resource to be shown in Custom Toast
	 * @param duration: Show the view or text notification for a long period of time. This time could be user-definable.
	 */
	public static void showCustomToast(Activity context,String strMessage,int intDrawable,int duration) {
		
		// Inflate Custom Toast layout
		inflateToastLayout(context);
		
		// Set Text of Custom Toast
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(strMessage);

		// Set Image of Custom Toast and make it visible
		imgToast = (ImageView)layout.findViewById(R.id.imgToast);
		imgToast.setImageDrawable(context.getResources().getDrawable(intDrawable));
		imgToast.setVisibility(View.VISIBLE);

		// Generate Custom Toast
		generateToast(context, duration);
	}
	
	/**
	 * Customizes Toast information (String resource and Drawable resource) and show Custom Toast
	 * @param context: Activity context where the Custom Toast will be shown
	 * @param intMessage: String resource to be shown in Custom Toast
	 * @param intDrawable: Drawable resource to be shown in Custom Toast
	 * @param duration: Show the view or text notification for a long period of time. This time could be user-definable.
	 */
	public static void showCustomToast(Activity context,int intMessage,int intDrawable,int duration) {
		
		// Inflate Custom Toast layout
		inflateToastLayout(context);

		// Set Text of Custom Toast
		txtToast = (TextView)layout.findViewById(R.id.txtToast);
		txtToast.setText(context.getResources().getText(intMessage));

		// Set Image of Custom Toast and make it visible		
		imgToast = (ImageView)layout.findViewById(R.id.imgToast);
		imgToast.setImageDrawable(context.getResources().getDrawable(intDrawable));
		imgToast.setVisibility(View.VISIBLE);
		
		// Generate Custom Toast
		generateToast(context, duration);
	}
}
