package app.cddic.com.smarter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import app.cddic.com.smarter.R;

/**
 * Created by VileWind on 2017/5/5 0005.
 */

public class PluginManageItemsAdapter extends BaseAdapter {
    private Context mContext;

    public PluginManageItemsAdapter(Context context) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_for_plugin,viewGroup,false);
        }
        return view;
    }
}
