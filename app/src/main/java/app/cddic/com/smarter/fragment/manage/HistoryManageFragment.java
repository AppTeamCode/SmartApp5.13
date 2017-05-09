package app.cddic.com.smarter.fragment.manage;

import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;

/**
 * SmartSecurity-Manager
 * app.edu.cdu.com.smartsecurity_manager.fragment.manage
 * Created by Pantiy on 2017/3/30.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public class HistoryManageFragment extends ManageFragment {


    private ImageButton mLeadoutAllMSGbtn;
    private ImageButton mDelAnnouncementMSGbtn;
    private ImageButton mDelNotificationMSGbtn;
    private ImageButton mDelChatMSGbtn;
    private ImageButton mDelWarnngMSGbtn;
    private ImageButton mDelAllMSGbtn;
    private ProgressBar mProgressBar;


    @Override
    protected void setFragmentName() {
      mFragmentName="记录设置";
    }

    @Override
    protected void initViews() {
        mLeadoutAllMSGbtn=(ImageButton)mView.findViewById(R.id.leadout_all_MSG_btn);

        mDelAnnouncementMSGbtn=(ImageButton)mView.findViewById(R.id.del_announcement_MSG_btn);
        mDelNotificationMSGbtn=(ImageButton)mView.findViewById(R.id.del_notification_MSG_btn);
        mDelChatMSGbtn=(ImageButton)mView.findViewById(R.id.del_chat_MSG_btn);
        mDelWarnngMSGbtn=(ImageButton)mView.findViewById(R.id.del_warning_MSG_btn);
        mDelAllMSGbtn=(ImageButton)mView.findViewById(R.id.del_all_MSG_btn);
        mProgressBar=(ProgressBar)mView.findViewById(R.id.progressBar);
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {

        mLeadoutAllMSGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //导出所有消息
            }
        });
        mDelAnnouncementMSGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空通告消息
            }
        });
        mDelNotificationMSGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空通知消息
            }
        });
        mDelWarnngMSGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空警报消息
            }
        });
        mDelChatMSGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空聊天消息
            }
        });
        mDelAllMSGbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空所有消息记录
            }
        });
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取内存数据
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_history_manage;
    }
}
