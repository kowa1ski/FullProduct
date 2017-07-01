package com.example.android.fullproduct.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Usuario on 01/07/2017.
 */

public class ProductContract {

    // To prevent someone accidentaly instantiating the contract class
    // give it an empty constructor.

    private ProductContract () {}

    /**
     * The content Authority is the name for the entire content provider
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.fullproduct";

    /**
     * We create the base for uri for content provider
     */
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Posible path (append to base content URI for possible URI's)
     */
    public final static String PATH_PRODUCT = "fullproduct";

    /**
     * Inner class that defines constant values for the products dastabase table.
     * Each entry in the table represents a single product.
     */
    public static final class ProductEntry implements BaseColumns {

        /**
         * The content URI to access the product data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCT);

        /** Name database */
        public final static String TABLE_NAME = "products";

        /**
         * Unique ID number for the product (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the text
         *
         * Type TEXT
         */
        public final static String COLUMN_PRODUCT_NAME = "name";

        /**
         * Quantity
         *
         * Type INTEGER
         */
        public final static String COLUMN_QUANTITY = "quantity";

        /**
         * Price
         *
         * Type INTEGER
         */
        public final static String COLUMN_PRICE = "price";

        /**
         * Image
         *
         * Type Blob
         */
        public final static String COLUMN_IMAGE = "image";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT ;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT ;


    }

















}
