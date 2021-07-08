package com.appentus.shedistrict.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appentus.shedistrict.R;
import com.appentus.shedistrict.models.DateAndTimeBean;

import java.util.List;

public class SpinnerCustomAdapter extends BaseAdapter {

    List<DateAndTimeBean> result;
    Context context;
    LayoutInflater inflater;
    Boolean value;
    public SpinnerCustomAdapter(@NonNull Context context,List<DateAndTimeBean> result,Boolean value) {
        this.context = context;
        this.result = result;
        this.value = value;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_item, null);
        TextView names = view.findViewById(R.id.title);
        names.setText(result.get(i).getText());

        if(i!=0 && value){
            names.setTextColor(Color.parseColor("#000000"));
        }

        if(value == false){
            names.setTextColor(Color.parseColor("#000000"));
        }

        return view;
    }

}
