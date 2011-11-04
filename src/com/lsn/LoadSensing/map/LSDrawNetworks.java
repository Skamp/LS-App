package com.lsn.LoadSensing.map;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class LSDrawNetworks extends Overlay {

	private Double latitud = 41.37*1E6;
	private Double longitud = 2.17*1E6;
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	
		Projection projection = mapView.getProjection();
		GeoPoint geoPoint = new GeoPoint(latitud.intValue(),longitud.intValue());
		
		if (shadow == false)
		{
			Point centro = new Point();
			projection.toPixels(geoPoint, centro);
			
			//Definición del pincel de dibujo
			Paint p = new Paint();
			p.setColor(Color.RED);
			
			//Marca Ejemplo 1: Círculo y Texto
			canvas.drawCircle(centro.x,centro.y,5,p);
			canvas.drawText("Barcelona", centro.x+10,centro.y+5,p);
			
			//Marca Ejemplo 2: Bitmap
			Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),R.drawable.ic_dialog_map);
			canvas.drawBitmap(bm, centro.x - bm.getWidth(), centro.y - bm.getHeight(), p);
			
		}
	}
	
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) {
		
		Context contexto = mapView.getContext();
		String msg = "Lat: " + point.getLatitudeE6()/1E6 + " - " + "Lon: " + point.getLongitudeE6()/1E6;
		
		Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_SHORT);
		toast.show();
		
		return true;
	}
}
