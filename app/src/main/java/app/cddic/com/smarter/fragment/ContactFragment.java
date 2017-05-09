package app.cddic.com.smarter.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.ChatActivity;
import app.cddic.com.smarter.adapter.ContactItemsAdapter;


/**
 * Created by Pantiy on 2017/3/12.
 * Copyright © 2016 All rights Reserved by Pantiy
 */

public class ContactFragment extends BaseFragment {

    private ExpandableListView mContactElv;

    @Override
        protected void initViews() {
        mContactElv = (ExpandableListView) mView.findViewById(R.id.contact_elv);
    }

    @Override
    protected void setupAdapters() {
        ContactItemsAdapter adapter = new ContactItemsAdapter(getActivity());
        mContactElv.setAdapter(adapter);
    }

    @Override
    protected void setupListeners() {
        mContactElv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String contactName = (String) parent.getExpandableListAdapter()
                        .getChild(groupPosition, childPosition);
                Intent intent = ChatActivity.newInstance(getActivity(), contactName);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_contact;
    }
}
