package app.cddic.com.smarter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.ChatActivity;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by asus on 2017/3/28.
 */

public class ContactDetailFragment extends BaseFragment {

    private static final String KEY_CONTACT_NAME = "contactName";

    private TopView mTopView;
    private TextView mContactNameTv;
    private Button mSendMessageBtn;

    public static ContactDetailFragment newInstance(String contactName) {
        ContactDetailFragment contactDetailFragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_CONTACT_NAME, contactName);
        contactDetailFragment.setArguments(args);
        return contactDetailFragment;
    }

    protected void initViews() {

        mTopView = (TopView)mView.findViewById(R.id.accout_detail);
        mTopView.setText("联系人","联系人详情",null);
        mContactNameTv = findView(R.id.contactName_tv);
        mContactNameTv.setText(getArguments().getString(KEY_CONTACT_NAME));
        mSendMessageBtn = findView(R.id.send_message_btn);
    }
    @Override
    protected void setupAdapters() {
        mSendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ChatActivity.newInstance(getActivity(),
                        getArguments().getString(KEY_CONTACT_NAME));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setupListeners() {
        mTopView.setupListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        },null);

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_contact_detail;
    }
}
