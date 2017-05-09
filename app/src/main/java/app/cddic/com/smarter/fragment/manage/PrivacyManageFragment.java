package app.cddic.com.smarter.fragment.manage;

import android.content.Intent;
import android.view.View;
import android.widget.Switch;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;

/**
 * SmartSecurity-Manager
 * app.edu.cdu.com.smartsecurity_manager.fragment.manage
 * Created by Pantiy on 2017/3/30.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public class PrivacyManageFragment extends ManageFragment {

    private Switch mSavePasswordsth;
    private Switch mAddFriendsth;
    private Switch mAssociateDevicesth;
    private Switch mAutoLoginsth;
    private Switch mAccountVisiblesth;
    private Switch mReciveStrangerMSGsth;

    @Override
    protected void setFragmentName() {
        mFragmentName = "隐私管理";

    }

    @Override
    protected void initViews() {

        mSavePasswordsth = (Switch) mView.findViewById(R.id.save_password_sth);
        mAccountVisiblesth = (Switch) mView.findViewById(R.id.account_visible_sth);
        mAddFriendsth = (Switch) mView.findViewById(R.id.add_friend_sth);
        mAssociateDevicesth = (Switch) mView.findViewById(R.id.associate_device_sth);
        mAutoLoginsth = (Switch) mView.findViewById(R.id.auto_login_sth);
        mReciveStrangerMSGsth = (Switch) mView.findViewById(R.id.receive_stranger_MSG_sth);
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {



        mReciveStrangerMSGsth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许接受陌生人消息
            }
        });
        mAutoLoginsth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许自动登录
            }
        });
        mAssociateDevicesth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关联设备需要验证
            }
        });
        mAddFriendsth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友需要验证
            }
        });
        mAccountVisiblesth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //账户他人可见
            }
        });
        mSavePasswordsth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许保存登录密码
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_privacy_mange;
    }
}
