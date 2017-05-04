package app.cddic.com.smarter.activity.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.entity.ContactMsg;


/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.ui
 * 文件名：  LoginActivity
 * 创建者：
 * 创建时间： 2017/3/24 15:13
 * 描述：
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private Button login;
    private Button toRegister;
    private Button noLogin;
    private Button forget;
    private EditText username;
    private EditText password;
    private TextView tv_loginFailed;
    private Intent intent;
    //用于测试
    private ContactMsg contactMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        initData();
        initView();
    }

    private void initData() {
        intent = new Intent();
        contactMsg = new ContactMsg();
    }

    public void initView(){
        login = (Button) findViewById(R.id.btn_loginApp);
        noLogin = (Button) findViewById(R.id.btn_noLogin);
        toRegister = (Button) findViewById(R.id.btn_gotoRegister);
        forget = (Button) findViewById(R.id.btn_forget);

        login.setOnClickListener(this);
        noLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);
        forget.setOnClickListener(this);

        username = (EditText) findViewById(R.id.edt_username);
        password = (EditText) findViewById(R.id.edt_password);
        tv_loginFailed = (TextView) findViewById(R.id.tv_networkInfo);
        tv_loginFailed.setVisibility(View.GONE);
    }



    //不同的activity有不同的实现
    @Override
    public void onHandleMsg(int MsgType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_loginApp:
                mAPP.setUserName(username.getText().toString());
                mAPP.setPassWord(password.getText().toString());
                contactMsg.setType(1);
                if (mService.execMsg(contactMsg)){
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    tv_loginFailed.setVisibility(View.VISIBLE);
                    break;
                }
            case R.id.btn_noLogin:
                break;
            case R.id.btn_gotoRegister:
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);
                finish();
            case R.id.btn_forget:
                break;
        }
    }
}
