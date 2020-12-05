package com.example.practicapmdm.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicapmdm.R;
import com.example.practicapmdm.models.Pool;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavouritesAdapter extends BaseAdapter {
    private Context mContext;
    private List<Pool> favouritesObjects;

    @Builder
    public FavouritesAdapter(Context mContext, List<Pool> favouritesObjects) {
        this.mContext = mContext;
        this.favouritesObjects = favouritesObjects;
    }

    @Override
    public int getCount() {
        return favouritesObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return favouritesObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.view_favourites, null);
        }

        TextView textView = convertView.findViewById(R.id.txtFavourites);
        textView.setText(favouritesObjects.get(position).getName());
        return convertView;
    }
}
