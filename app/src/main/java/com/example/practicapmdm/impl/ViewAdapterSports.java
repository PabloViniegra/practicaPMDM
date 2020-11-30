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
public class ViewAdapterSports extends BaseAdapter {
    private Context mContext;
    public List<Pool> mSports;

    @Builder
    public ViewAdapterSports(Context mContext, List<Pool> mSports) {
        this.mContext = mContext;
        this.mSports = mSports;
    }

    @Override
    public int getCount() {
        return mSports.size();
    }

    @Override
    public Object getItem(int position) {
        return mSports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =layoutInflater.inflate(R.layout.views_of_sports, null);
        }

        TextView textView = convertView.findViewById(R.id.txtSport);
        textView.setText(mSports.get(position).getName());
        return convertView;
    }
}
