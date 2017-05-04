package app.cddic.com.smarter.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.fragment.CheckPhoneFragment;

/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.activity.base
 * 文件名：  RegisterActivity
 * 创建者：
 * 创建时间： 2017/4/11 16:08
 * 描述：
 */

public class RegisterActivity extends BaseActivity {

    @Override
    public void onHandleMsg(int MsgType) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFragmentManager.beginTransaction().add(R.id.container,new CheckPhoneFragment(),null).commit();
    }
}
