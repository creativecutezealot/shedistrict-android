package com.appentus.shedistrict.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.appentus.shedistrict.R;
import com.appentus.shedistrict.models.IntroBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class IntroPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<IntroBean> list;
    TextView dis;
    ImageView ivImage;
    public IntroPagerAdapter(Context mContext,List<IntroBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_home, collection, false);
        dis = layout.findViewById(R.id.tvDis);
        ivImage = layout.findViewById(R.id.ivImages);
        dis.setText(list.get(position).getDis());

        Glide.with(mContext).load(list.get(position).getImage())
                .placeholder(R.drawable.ic_logo).into(ivImage);


        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
