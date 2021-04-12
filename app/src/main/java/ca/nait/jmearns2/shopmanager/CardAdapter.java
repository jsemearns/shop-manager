package ca.nait.jmearns2.shopmanager;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private ArrayList<ShopItem> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewPrice;
        ImageView imageViewPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.text_view_name);
            this.textViewPrice = (TextView) itemView.findViewById(R.id.text_view_price);
            this.imageViewPhoto = (ImageView) itemView.findViewById(R.id.image_view_photo);
        }
    }

    public CardAdapter(ArrayList<ShopItem> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewPrice;
        ImageView imageView = holder.imageViewPhoto;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText("$" + dataSet.get(listPosition).getPrice());
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(dataSet.get(listPosition).getImage(), 0, dataSet.get(listPosition).getImage().length));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
