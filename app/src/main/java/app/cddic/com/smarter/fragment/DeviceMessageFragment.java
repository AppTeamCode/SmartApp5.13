package app.cddic.com.smarter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.utils.CommonViewHolder;
import app.cddic.com.smarter.widget.TopView;

/**
 * Created by Hai on 2017/4/26.
 */

public class DeviceMessageFragment extends BaseFragment{
    private FragmentManager fm;
    private Fragment fragment;
    private TopView mTopView;
    private Button SendMessageBtn;

    @Override
    protected void initViews() {
        mTopView = (TopView) CommonViewHolder.get(mView, R.id.device_message_topView);
        mTopView.setText("返回","设备消息",null);
        SendMessageBtn = (Button)CommonViewHolder.get(mView, R.id.send_message_btn);

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
                fragment = new DeviceFragment();
                fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        },null);

        SendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_device_message;
    }
}
