package app.cddic.com.smarter.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by asus on 2017/3/28.
 */

public class LoginandOutFragment extends BaseFragment {

    private TopView mTopView;
    private Button mLoginButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "LoginandOut create", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected void initViews() {

        mTopView = (TopView)mView.findViewById(R.id.login_and_out_topView);
        mTopView.setText("返回","设备登陆","退出当前设备");
        mLoginButton=(Button)mView.findViewById(R.id.login_btn);
    }
    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {

        mTopView.setupListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        }, new View.OnClickListener() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            @Override
            public void onClick(View v) {

                builder.setTitle("退出当前设备").setMessage("退出当前设备可能会影响您的。。。确定退出？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     Intent intent=new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //取消按钮的点击事件
                    }
                }).show();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_login_and_out;
    }
}
