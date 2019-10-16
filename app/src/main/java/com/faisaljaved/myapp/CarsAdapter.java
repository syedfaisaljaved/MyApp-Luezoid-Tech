package com.faisaljaved.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class CarsAdapter extends ArrayAdapter<Cars> {

    public CarsAdapter(Context context, List<Cars> cars){
        super(context,0,cars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

            Cars currentCar = getItem(position);

            TextView title = (TextView) listItemView.findViewById(R.id.text1);
            TextView subtitle = (TextView) listItemView.findViewById(R.id.text2);
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_item);

            title.setText(currentCar.getmTitle());
            subtitle.setText(currentCar.getmSubtitle());
            Picasso.get().load(currentCar.getmImageId()).into(imageView);

        }

        return listItemView;
    }
}
