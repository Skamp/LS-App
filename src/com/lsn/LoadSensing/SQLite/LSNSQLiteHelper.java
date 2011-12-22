package com.lsn.LoadSensing.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LSNSQLiteHelper extends SQLiteOpenHelper {

	// Sentencia SQL para crear la tabla de Usuarios
	String sqlCreateNetwork = "CREATE TABLE Network (user TEXT, poblacio TEXT, name TEXT, "
			+ "idNetwork TEXT, sensors INTEGER, lat TEXT, lon TEXT)";

	String sqlCreateSensor = "CREATE TABLE Sensor (user TEXT, poblacio TEXT, name TEXT, " +
		     "serial, idSensor TEXT, idNetwork TEXT, type TEXT, channel TEXT, " +
		     "description TEXT, lat TEXT, lon TEXT, image TEXT);";

	String sqlCreateImage = "CREATE TABLE Image (user TEXT, poblacio TEXT, name TEXT, "
			+ "idImage TEXT, idNetwork TEXT, imageFile TEXT)";

	public LSNSQLiteHelper(Context contexto, String nombre,
			CursorFactory factory, int version) {
		super(contexto, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Se ejecuta la sentencia SQL de creación de la tabla
		db.execSQL(sqlCreateNetwork);
		db.execSQL(sqlCreateSensor);
		db.execSQL(sqlCreateImage);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
