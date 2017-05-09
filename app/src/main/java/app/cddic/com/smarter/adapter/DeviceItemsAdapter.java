package app.cddic.com.smarter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.utils.CommonViewHolder;


/**
 * Created by Pantiy on 2017/3/13.
 * Copyright © 2016 All rights Reserved by Pantiy
 */

public class DeviceItemsAdapter extends BaseAdapter {

    private Context mContext;

    public DeviceItemsAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_for_device,viewGroup,false);
        }
        LinearLayout left = CommonViewHolder.get(view, R.id.left_deviceItem);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
