package com.lsn.LoadSensing.func;

import java.util.List;

import com.lsn.LoadSensing.R;
import com.lsn.LoadSensing.ui.CustomToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;

public class LSFunctions {

	public static boolean isIntentAvailable(Context context, String action)
	{
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 
				                                    PackageManager.MATCH_DEFAULT_ONLY);
		
		return (list.size() > 0);
	}
	
	
	public static boolean checkSDCard(Activity context)
	{
		String auxSDCardStatus = Environment.getExternalStorageState();
		Integer strCustomMessage=0;
		Integer imgCustomImage=0;
		
		if      (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED))
		{
			return true;
		}
		else if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
		{
			imgCustomImage = CustomToast.IMG_EXCLAMATION;
			strCustomMessage = R.string.msgSDReadOnly;
		}
		else if (auxSDCardStatus.equals(Environment.MEDIA_NOFS))
		{
			imgCustomImage = CustomToast.IMG_ERROR;
			strCustomMessage = R.string.msgSDBadFormat;
		}
		else if (auxSDCardStatus.equals(Environment.MEDIA_REMOVED))
		{
			imgCustomImage = CustomToast.IMG_ERROR;
			strCustomMessage = R.string.msgSDNotFound;
		}		
		else if (auxSDCardStatus.equals(Environment.MEDIA_SHARED))
		{
			imgCustomImage = CustomToast.IMG_ERROR;
			strCustomMessage = R.string.msgSDShared;
		}		
		else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTABLE))
		{
			imgCustomImage = CustomToast.IMG_ERROR;
			strCustomMessage = R.string.msgSDUnmountable;

		}		
		else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTED))
		{
			imgCustomImage = CustomToast.IMG_ERROR;
			strCustomMessage = R.string.msgSDUnmounted;
		}		
		
		CustomToast.showCustomToast(context,
				strCustomMessage,
                imgCustomImage,
                CustomToast.LENGTH_LONG);
		
		return false;
	}
	
}
