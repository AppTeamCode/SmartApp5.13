package app.cddic.com.smarter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.utils.CommonViewHolder;
import app.cddic.com.smarter.widget.TopView;

/**
 * Created by Hai on 2017/4/24.
 */

public class DevicesSettingsFragment extends BaseFragment{
    private FragmentManager fm;
    private Fragment fragment;
    private TopView mTopView;
    private Button NoAmendBtn,LogAgainBtn;

    @Override
    protected void initViews() {
        mTopView = (TopView) CommonViewHolder.get(mView,R.id.devices_settings_topView);
        mTopView.setText("返回","设备设置",null);
        NoAmendBtn = (Button)CommonViewHolder.get(mView, R.id.abandon_modification_button);
        LogAgainBtn = (Button)CommonViewHolder.get(mView,R.id.login_again_button);
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

        NoAmendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LogAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_devices_settings;
    }
}
