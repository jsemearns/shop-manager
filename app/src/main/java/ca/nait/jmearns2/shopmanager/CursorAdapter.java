package ca.nait.jmearns2.shopmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CursorAdapter extends SimpleCursorAdapter
{
    static final String[] columns = {DBManager.C_SHOP_ITEM_NAME, DBManager.C_SHOP_ITEM_PRICE};
    static final int[] ids = {R.id.cursor_name, R.id.cursor_price};

    Context context;

    public CursorAdapter(Context context, Cursor c)
    {
        super(context, R.layout.list_view_row, c, columns, ids);
        this.context = context;
    }

    @Override
    public void bindView(View row, Context context, Cursor cursor)
    {
        super.bindView(row, context, cursor);
    }
}
