package com.lsn.LoadSensing.func;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class LSFunctions {

	public static boolean isIntentAvailable(Context context, String action)
	{
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 
				                                    PackageManager.MATCH_DEFAULT_ONLY);
		
		return (list.size() > 0);
	}
	
	//@SuppressWarnings("rawtypes")
	public static JSONObject urlRequest(String url, Map<?,?> params)
	{
		HttpClient client = new DefaultHttpClient();
		//Set timeout connection
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpPost post = null;
		HttpResponse response = null;
		HttpEntity entity;
		
		try
		{
			post = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
			Iterator<?> it = params.entrySet().iterator();
			while (it.hasNext())
			{
				
				Map.Entry<?,?> element = (Map.Entry<?,?>)it.next();
				nameValuePairs.add(new BasicNameValuePair(element.getKey().toString(),element.getValue().toString()));
			}
			
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		entity = response.getEntity();
		
		JSONObject retJSON = null;
		try {
			retJSON = new JSONObject(EntityUtils.toString(entity));
			//retJSON = new JSONObject(EntityUtils.toString(entity,HTTP.UTF_8));
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return retJSON;
		
	}
	
	
	public static JSONArray urlRequestArray(String url, Map<?,?> params)
	{
		HttpClient client = new DefaultHttpClient();
		//Set timeout connection
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		
		HttpPost post = null;
		HttpResponse response = null;
		HttpEntity entity;
		
		
		try
		{
			post = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
			Iterator<?> it = params.entrySet().iterator();
			while (it.hasNext())
			{
				
				Map.Entry<?,?> element = (Map.Entry<?,?>)it.next();
				nameValuePairs.add(new BasicNameValuePair(element.getKey().toString(),element.getValue().toString()));
			}
			
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		entity = response.getEntity();
		
		JSONArray retJSONArray = null;
		try {
			//retJSONArray = new JSONArray(EntityUtils.toString(entity));
			retJSONArray = new JSONArray(EntityUtils.toString(entity,HTTP.UTF_8));
			
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return retJSONArray;
		
	}
	
	
	
	
}
