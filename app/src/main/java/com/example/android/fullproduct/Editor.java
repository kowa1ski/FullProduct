package com.example.android.fullproduct;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.fullproduct.data.ProductContract;
import com.example.android.fullproduct.data.ProductProvider;

public class Editor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    // Identifier for the product data loader
    private static final int EXISTING_PRODUCT_LOADER = 0;

    /**
     * Content URI for the existing product (null if it´s a new pet)
     */
    private Uri mCurrentProductUri;

    /**
     * EditText field to enter the name of product
     */
    private EditText mNameProduct;

    /**
     * EditText field to enter the quantity
     */
    private EditText mQuantityProduct;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameProduct = (EditText) findViewById(R.id.productName);
        mQuantityProduct = (EditText) findViewById(R.id.quantityOfProducts);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, ir the user tries to leave the editor without saving.
        // TODO están en la línea 158 del pets












        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save pet to database
                //TODO (hacer el método e indicarlo justo aquí debajo) Ya está. Voy a probarlo.
                saveProduct();
                // Exit activity
                // Return from this Activity to CatalogActivity
                finish();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void saveProduct() {
        String nameProductString = mNameProduct.getText().toString().trim();
        String quantityProductString = mQuantityProduct.getText().toString().trim();

        // Create a ContentValues objetc where column names are the keys,
        //and product attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME, nameProductString);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantityProductString);

        // Insert a new product into the provider, returning the content URI for the new product.
        Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion
        // was successful.
        if (newUri == null){
            // If the new content is null, then there was an error with insetion.
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
        }else {
            // Otherwise, the insertion was sucessful and we can display a toast
            Toast.makeText(this, "product saved", Toast.LENGTH_SHORT).show();
        }




    }


    /**
     *
     */







    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the product table.
        String[] projection = {
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_IMAGE
        };

        // This loader will execute the ContentProvider´s query method on a background thread
        return new CursorLoader(this,       // Parent activity context
                mCurrentProductUri,         // Query the content URI for the current product
                projection,                 // Columns to include in the resulting Cursor
                null,                       // No selection clause
                null,                       // No selection arguments
                null                        // Default sort order

        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Bail early if the cursor is null or there is less than 1 row in the cursor.
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of product attributes that we are interested in.
            int nameColumIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
            int quantityColumIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);
            int priceColumIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_IMAGE);

            // Extract the value from the Cursor for the given column index
            String name = cursor.getString(nameColumIndex);
            int quantity = cursor.getInt(quantityColumIndex);
            int price = cursor.getInt(priceColumIndex);
            int image = cursor.getInt(imageColumnIndex);

            // Update the views on the screen with the values from the database.
            mNameProduct.setText(name);
            mQuantityProduct.setText(Integer.toString(quantity));






        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        // If the loader is invalidated, clear out all the data from the input fields.
        mNameProduct.setText("");
        mQuantityProduct.setText("");



    }









}
