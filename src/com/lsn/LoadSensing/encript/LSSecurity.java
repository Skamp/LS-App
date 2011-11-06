package com.lsn.LoadSensing.encript;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.Editable;
import android.text.SpannableStringBuilder;


public class LSSecurity {

	public static final String Code_MD5 = "MD5";
	public static final String Code_SHA_256 = "SHA-256";
	public static final String Code_SHA_384 = "SHA-384";
	public static final String Code_SHA_512 = "SHA-512";
	
	public static String rot13Encode(String strInput)
	{
		StringBuilder strOutput = new StringBuilder();
		for (int i=0;i<strInput.length();i++)
		{
			char c = strInput.charAt(i);
			if ((c<32)||(c>126))
			{
				strOutput.append(c);
			}
			else
			{
				c+=13;
				if (c>126)
				{
					c-=((126-32)+1);
				}
				strOutput.append(c);
			}
		}
		return strOutput.toString();
	}
	
	public static String rot13Decode(String strInput)
	{
		StringBuilder strOutput = new StringBuilder();
		for (int i=0;i<strInput.length();i++)
		{
			char c = strInput.charAt(i);
			if ((c<32)||(c>126))
			{
				strOutput.append(c);
			}
			else
			{
				c-=13;
				if (c<32)
				{
					c+=((126-32)+1);
				}
				strOutput.append(c);
			}
		}
		
		return strOutput.toString();
	}
	
	public static String encrypt(String strInput,String code)
	{
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(code);
			md.reset();
			md.update(strInput.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte byteData[] = md.digest();
		
		//Convert the byte to hex format
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<byteData.length;i++) {
			String hex=Integer.toHexString(0xff & byteData[i]);
			if (hex.length()==1) hexString.append('0');
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
}
