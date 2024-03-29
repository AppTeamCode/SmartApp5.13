package app.cddic.com.smarter.fragment;

import android.widget.ListView;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.adapter.DeviceItemsAdapter;


/**
 * Created by Pantiy on 2017/3/12.
 * Copyright © 2016 All rights Reserved by Pantiy
 */

public class DeviceFragment extends BaseFragment {

    private ListView mDeviceItemsListView;

    @Override
    protected void initViews() {
        mDeviceItemsListView = (ListView) mView.findViewById(R.id.deviceItems_listView);
    }

    @Override
    protected void setupAdapters() {
        DeviceItemsAdapter deviceItemsAdapter = new DeviceItemsAdapter(getActivity());
        mDeviceItemsListView.setAdapter(deviceItemsAdapter);
    }

    @Override
    protected void setupListeners() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_device;
    }
}
