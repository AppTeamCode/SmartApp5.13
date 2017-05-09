package app.cddic.com.smarter.fragment.manage;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.adapter.PluginManageItemsAdapter;
import app.cddic.com.smarter.widget.TopView;

/**
 * SmartSecurity-Manager
 * app.edu.cdu.com.smartsecurity_manager.fragment.manage
 * Created by Pantiy on 2017/3/30.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public class PluginManageFragment extends ManageFragment {

    private ListView mPluginManageListView;

    @Override
    protected void setFragmentName() {
 mFragmentName="插件管理";
    }

    @Override
    protected void initViews() {

       mPluginManageListView=(ListView)mView.findViewById(R.id.plugin_manage_listView);
    }

    @Override
    protected void setupAdapters() {
        PluginManageItemsAdapter pluginManageItemsAdapter = new PluginManageItemsAdapter(getActivity());
       mPluginManageListView.setAdapter(pluginManageItemsAdapter);
    }

    @Override
    protected void setupListeners() {
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_plugin_manage;
    }
}
