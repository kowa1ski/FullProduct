package com.example.android.fullproduct;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.fullproduct.data.ProductContract;

/**
 * Created by Usuario on 31/07/2017.
 */

/**
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */

public class ProductCursorAdapter extends CursorAdapter {

    /**
     * Construct a new {@Link ProductCursorAdapter}
     * @param context   The context
     * @param c         The cursor from which to get the data.
     */


    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item vew. No data is set(or bound) to the views yet.
     *
     * @param context       app context
     * @param cursor        The cursor from which to get the data. The cursor is already
     *                      moved to the correct position.
     * @param parent        The parent to which the new view is attached to
     * @return              the newly created list item view.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Find individual views that we want to modify in the list item layout
        TextView productNameTextView = (TextView) view.findViewById(R.id.productName);
        TextView quantityOfProductTextView = (TextView) view.findViewById(R.id.quantityOfProducts);

        //Find the colums of products attributes that we´re interested in
        int productNameColumIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int quantityOfProductColumIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(productNameColumIndex);
        String quantityOfProduct = cursor.getString(quantityOfProductColumIndex);

        // If the product is empty string or null, then use some default text
        // that says "Unknown_product", so the TextView isn´t bloank.
        if (TextUtils.isEmpty(quantityOfProduct)){
            quantityOfProduct = "Unknown quantity";
        }

        // Update the TextViews with the attributes for the current product
        productNameTextView.setText(productName);
        quantityOfProductTextView.setText(quantityOfProduct);



    }

}
