package com.example.android.fullproduct.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 29/06/2017.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    //** Name of the database file */
    private static final String DATABASE_NAME = "FullProducts.db";

    /*
    Database version. If you change the database schema, you must
    increment the database version
     */
    private static final int DATABASE_VERSION = 1;


    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statements to create
        // the products table.
        // TODO create here string with campos de la tabla

        // Execute the SQL statement
        // TODO quitar esta línea y habilitar la siguiente
        // db.execSQL(SQL_CREATE_PRODUCTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
