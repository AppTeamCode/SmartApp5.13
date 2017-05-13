package app.cddic.com.smarter.fragment.manage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by asus on 2017/3/28.
 */

public class AccountManageFragment extends ManageFragment{
    private TopView mTopView;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    private TextView mTextView7;
    private TextView mTextView_exit;
    private android.support.v4.app.FragmentManager fm;
    private Fragment fragment;
    private static final String DIALOG_DATE = "DialogDate";
    @Override
    protected void setFragmentName() {
        mFragmentName = "账号管理";

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "AccoutManage create", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }        @Override
    protected void initViews() {
        mTextView1 = (TextView) mView.findViewById(R.id.textview_id_manage_add);
        mTextView2 = (TextView)mView.findViewById(R.id.textview_id_manage_login_setting);
        mTextView3 = (TextView)mView.findViewById(R.id.textview_id_manage_exit);
        mTextView4 = (TextView)mView.findViewById(R.id.textView_id_manage_test1);
        mTextView5 = (TextView)mView.findViewById(R.id.textView_account_manage_test1);
        mTextView6 = (TextView)mView.findViewById(R.id.textview_id_manage_name1);
        mTextView7 = (TextView)mView.findViewById(R.id.textview_account_manage_name1);
        mTextView_exit =(TextView)mView.findViewById(R.id.textview_id_manage_exit);
        mTextView4.setVisibility(View.VISIBLE);
        mTextView5.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {

        fm = getActivity().getSupportFragmentManager();
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fragment = new AddDeviceFragment();
                fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });


        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                fragment = new LoginSettingFragment();
                fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
        mTextView3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
            }
        });
        mTextView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView4.setVisibility(View.VISIBLE);
                mTextView5.setVisibility(View.INVISIBLE);
            }
        });
        mTextView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView4.setVisibility(View.INVISIBLE);
                mTextView5.setVisibility(View.VISIBLE);
            }
        });

        mTextView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = null;
                ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("是否确定退出当前设备");
                ad.setMessage("登录时间:7月9号12：23-10号15：23\n\n远程登录         时长:27小时");
                ad.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ad.setNegativeButton("确认退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ad.show();

            }
        });

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_account_manage;
    }
}
