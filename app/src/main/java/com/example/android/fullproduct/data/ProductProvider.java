package com.example.android.fullproduct.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Usuario on 28/07/2017.
 */

public class ProductProvider extends ContentProvider {

    /** URI matcher code for the content URI for the products table */
    private static final int PRODUCTS = 100;

    /** URI matcher code for the content URI for a single product in the table */
    private static final int PRODUCTS_ID = 101;

    /**
     * Urimatcher object to match a content URI to a corresponding code.
     * The input passed into the contructor represents the code to return for the root URI.
     * It´s common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        sUrimatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT, PRODUCTS);
        sUrimatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT + "/#", PRODUCTS_ID);
    }

    /* Database helper object */
    private ProductDbHelper mDbHelper;


    /**
     * Initialize the provider and the database helper object
     * @return
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new ProductDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given Uri. Use the given projection, selection,
     * selection arguments, and sort order.
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUrimatcher.match(uri);
        switch (match){
            case PRODUCTS:
                cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCTS_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        // Set notification URI on the Cursor.
        // So we kow what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);


        return cursor;
    }

    /**
     * Returns the MIME type of data for the content URI.
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {

        final int match = sUrimatcher.match(uri);
        switch (match){
            case PRODUCTS:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCTS_ID:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match" + match);

        }


    }

    /**
     * Insert a product into the database with the given content values. Return the new content URI
     * for that specific row in the database.

     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUrimatcher.match(uri);
        switch (match){
            case PRODUCTS:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    /**
     * Insert a product into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     * @param uri
     * @param values
     * @return
     */

    private Uri insertProduct(Uri uri, ContentValues values) {

        // TODO chek the values is not null etc joer

        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new product with the given values
        long id = database.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e("LogTag", "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the product content URI
        // uri: content://com.example.android.products/products or something
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
