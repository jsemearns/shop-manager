package ca.nait.jmearns2.shopmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button manageShop, viewShop;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageShop = (Button)findViewById(R.id.button_manage_shop);
        manageShop.setOnClickListener(this);
        viewShop = (Button)findViewById(R.id.button_view_shop);
        viewShop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId()) {
            case R.id.button_view_shop: {
                Intent intent = new Intent(this, ViewShopActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_manage_shop: {
                Intent intent = new Intent(this, ManageShop.class);
                startActivity(intent);
                break;
            }
        }
    }
}