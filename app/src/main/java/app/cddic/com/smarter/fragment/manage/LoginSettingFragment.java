package app.cddic.com.smarter.fragment.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.widget.TopView;

/**
 * Created by asus on 2017/3/28.
 */

public class LoginSettingFragment extends ManageFragment {
    private FragmentManager fm;
    private Fragment fragment;
    private TopView mTopView;
    private TextView textView_login_setting_in;
    private TextView textView_login_setting_out;
    private TextView textview_login_setting_test1;
    private TextView textview_login_setting_test2;
    @Override
    protected void setFragmentName() {
        mFragmentName = "登录设置";

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "LoginSetting create", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    protected void initViews() {

        mTopView = (TopView) mView.findViewById(R.id.login_setting_topView);
        mTopView.setText("返回", "登录设置", null);
        textView_login_setting_in = (TextView) mView.findViewById(R.id.textView_login_setting_in);
        textView_login_setting_out = (TextView)mView.findViewById(R.id.textView_login_setting_out);
        textview_login_setting_test1 = (TextView)mView.findViewById(R.id.textview_login_setting_test1);
        textview_login_setting_test2 = (TextView)mView.findViewById(R.id.textview_login_setting_test2);
        textview_login_setting_test1.setVisibility(View.VISIBLE);
        textview_login_setting_test2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {
        fm = getActivity().getSupportFragmentManager();
        mTopView.setupListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AccountManageFragment();
                fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        },null);
        textView_login_setting_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview_login_setting_test1.setVisibility(View.VISIBLE);
                textview_login_setting_test2.setVisibility(View.INVISIBLE);
            }
        });
        textView_login_setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview_login_setting_test1.setVisibility(View.INVISIBLE);
                textview_login_setting_test2.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_login_setting;
    }
}
