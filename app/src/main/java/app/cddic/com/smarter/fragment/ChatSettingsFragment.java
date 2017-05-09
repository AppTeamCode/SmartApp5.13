package app.cddic.com.smarter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import app.cddic.com.smarter.R;
import app.cddic.com.smarter.utils.CommonViewHolder;
import app.cddic.com.smarter.widget.TopView;

/**
 * Created by Hai on 2017/4/25.
 */

public class ChatSettingsFragment extends BaseFragment{
    private FragmentManager fm;
    private Fragment fragment;
    private TopView mTopView;
    private TextView ChatLogTv,ChatFileTv,ClearChatInformationTv;
    private Button DeleteContactBtn;

    @Override
    protected void initViews() {
        mTopView =  CommonViewHolder.get(mView,R.id.chat_setting_topView);
        mTopView.setText("返回","聊天设置",null);
        ChatLogTv = CommonViewHolder.get(mView, R.id.chat_log_tv);
        ChatFileTv =  CommonViewHolder.get(mView,R.id.chat_file_tv);
        ClearChatInformationTv = CommonViewHolder.get(mView,R.id.clear_information_tv);
        DeleteContactBtn = CommonViewHolder.get(mView,R.id.delete_contact_btn);
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {
         fm = getActivity().getSupportFragmentManager();
        mTopView.setupListeners(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fragment = new  DeviceFragment();/*这个返回界面还没写，应该是聊天界面*/
                fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        },null);

        ChatLogTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ChatFileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ClearChatInformationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DeleteContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_chat_settings;
    }
}
