package ca.nait.jmearns2.shopmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class ViewShopActivity extends AppCompatActivity
{
    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<ShopItem> items;

    static SQLiteDatabase database;
    static DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop);
        manager = new DBManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<ShopItem>();
        database = manager.getReadableDatabase();
        Cursor cursor = database.query(DBManager.TABLE_SHOP_ITEM, null, null, null, null, null, null);
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
            items.add(item);
        }

        adapter = new CardAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}