package ca.nait.jmearns2.shopmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ManageShop extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{
    ArrayList<ShopItem> shopItems;
    static SQLiteDatabase database;
    static DBManager manager;
    static Cursor cursor;

    ListView listView;
    EditText etName;
    EditText etPrice;
    ImageView imPreview;

    static int currentItemIndex = -1;
    private static final int SELECT_PHOTO = 100;
    static byte[] image;

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        cursor.requery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shop);

        manager = new DBManager(this);

        Button create = (Button)findViewById(R.id.button_save_list_item);
        Button update = (Button)findViewById(R.id.button_update_item);
        Button delete = (Button)findViewById(R.id.button_delete_item);
        Button upload = (Button)findViewById(R.id.button_upload_photo);

        create.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        upload.setOnClickListener(this);

        image = new byte[]{};

        etName = findViewById(R.id.edit_text_item_name);
        etPrice = findViewById(R.id.edit_text_item_price);
        imPreview = (ImageView)findViewById(R.id.image_view_preview);
        listView = (ListView)findViewById(R.id.list_view_items);
        listView.setOnItemClickListener(this);
        refreshListView();

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ManageShop.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        currentItemIndex = position;
        ShopItem item = shopItems.get(position);
        etName.setText(item.getName());
        etPrice.setText(item.getPrice() + "");
        image = item.getImage();
        imPreview.setImageBitmap(BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length));
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()) {
            case R.id.button_save_list_item: {
                if (image.length > 0 && etName.getText().toString().length() > 0 && etPrice.getText().toString().length() > 0) {
                    String name = etName.getText().toString();
                    float price = Float.parseFloat(etPrice.getText().toString());

                    etName.setText("");
                    etPrice.setText("");

                    ContentValues values = new ContentValues();
                    values.put(DBManager.C_SHOP_ITEM_NAME, name);
                    values.put(DBManager.C_SHOP_ITEM_PRICE, price);
                    values.put(DBManager.C_SHOP_ITEM_IMAGE, image);

                    try {
                        database = manager.getWritableDatabase();
                        database.insertOrThrow(DBManager.TABLE_SHOP_ITEM, null, values);
                        Toast.makeText(this, "Item is saved", Toast.LENGTH_SHORT).show();
                        database.close();
                        refreshListView();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                    }

                    image = new byte[]{};
                    imPreview.setImageBitmap(null);
                } else {
                    Toast.makeText(this, "Error: Must have complete details", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_upload_photo: {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
            }
            case R.id.button_update_item: {
                if (currentItemIndex >= 0) {
                    String name = etName.getText().toString();
                    float price = Float.parseFloat(etPrice.getText().toString());

                    etName.setText("");
                    etPrice.setText("");

                    ContentValues values = new ContentValues();
                    values.put(DBManager.C_SHOP_ITEM_NAME, name);
                    values.put(DBManager.C_SHOP_ITEM_PRICE, price);
                    values.put(DBManager.C_SHOP_ITEM_IMAGE, image);

                    ShopItem item = shopItems.get(currentItemIndex);

                    try {
                        database = manager.getWritableDatabase();
                        database.update(DBManager.TABLE_SHOP_ITEM, values, DBManager.C_ID + "=" + item.getShopItemId(), null);
                        database.close();
                        refreshListView();
                        Toast.makeText(this, "Updated: " + name, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                    }

                    currentItemIndex = -1;
                    image = new byte[]{};
                    imPreview.setImageBitmap(null);
                } else {
                    Toast.makeText(this, "Select an item to update first", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.button_delete_item: {
                if (currentItemIndex >= 0) {
                    ShopItem item = shopItems.get(currentItemIndex);
                    etPrice.setText("");
                    etName.setText("");

                    try {
                        database = manager.getWritableDatabase();
                        database.delete(DBManager.TABLE_SHOP_ITEM, DBManager.C_ID + "=" + item.getShopItemId(), null);
                        database.close();
                        Toast.makeText(this, "Deleted: " + item.getName(), Toast.LENGTH_LONG).show();
                        refreshListView();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
                    }

                    image = new byte[]{};
                    imPreview.setImageBitmap(null);
                    currentItemIndex = -1;
                } else {
                    Toast.makeText(this, "Select an item to delete first", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (imageReturnedIntent != null) {
            Uri selectedImage = imageReturnedIntent.getData();
            switch(requestCode) {
                case SELECT_PHOTO:
                {
                    if (resultCode == RESULT_OK)
                    {
                        try {
                            Bitmap sImage = decodeUri(selectedImage);
                            // set image to preview
                            imPreview.setImageBitmap(sImage);

                            // convert bitmap to byte array to store in database
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            sImage.compress(Bitmap.CompressFormat.PNG, 0, stream);
                            image = stream.toByteArray();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        } else {
            Toast.makeText(getBaseContext(), "data null", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException
    {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }

    private void refreshListView() {
        shopItems = new ArrayList<ShopItem>();
        database = manager.getReadableDatabase();

        cursor = database.query(DBManager.TABLE_SHOP_ITEM, null, null, null, null, null, null);
        startManagingCursor(cursor);

        int id;
        String name;
        float price;
        byte[] image;

        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.C_ID));
            name = cursor.getString(cursor.getColumnIndex(DBManager.C_SHOP_ITEM_NAME));
            price = cursor.getFloat(cursor.getColumnIndex(DBManager.C_SHOP_ITEM_PRICE));
            image = cursor.getBlob(cursor.getColumnIndex(DBManager.C_SHOP_ITEM_IMAGE));

            ShopItem item = new ShopItem(id, name, price, image);
            shopItems.add(item);
        }

        CursorAdapter adapter = new CursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        stopManagingCursor(cursor);
    }
}