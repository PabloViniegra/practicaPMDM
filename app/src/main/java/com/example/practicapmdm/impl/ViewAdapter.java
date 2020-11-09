package com.example.practicapmdm.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practicapmdm.R;
import com.example.practicapmdm.models.Pool;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ViewAdapter extends BaseAdapter {
    private Context mcontext;
    private int layout;
    private List<Pool> mPools;

    @Builder
    public ViewAdapter(Context mcontext, int layout, List<Pool> mPools) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.mPools = mPools;
    }

    @Override
    public int getCount() {
        return mPools.size();
    }

    @Override
    public Object getItem(int position) {
        return mPools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =layoutInflater.inflate(R.layout.mylist, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imgViewList);
        TextView textView = convertView.findViewById(R.id.txtViewList);
        imageView.setImageResource(R.drawable.moreinfo_arrow_pressed);
        textView.setText(mPools.get(position).getName());
        return null;
    }
}
