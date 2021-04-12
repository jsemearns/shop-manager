package ca.nait.jmearns2.shopmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBManager extends SQLiteOpenHelper
{
    static final String DB_NAME = "ManageShop";
    static final int DB_VERSION = 1;
    static final String TABLE_SHOP_ITEM = "ShopItem";

    static final String C_ID = BaseColumns._ID;

    static final String C_SHOP_ITEM_NAME = "name";
    static final String C_SHOP_ITEM_PRICE = "price";
    static final String C_SHOP_ITEM_IMAGE = "image";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_SHOP_ITEM + " (" + C_ID + " integer primary key autoincrement, "
                + C_SHOP_ITEM_NAME + " text, "
                + C_SHOP_ITEM_PRICE + " decimal, "
                + C_SHOP_ITEM_IMAGE + " blob)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_SHOP_ITEM);
        onCreate(db);
    }
}
