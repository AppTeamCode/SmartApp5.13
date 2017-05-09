package app.cddic.com.smarter.fragment.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by asus on 2017/3/28.
 */

public class AddDeviceFragment extends ManageFragment {
    private FragmentManager fm;
    private Fragment fragment;
    private TopView mTopView;
    @Override
    protected void setFragmentName() {
        mFragmentName = "添加设备";

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "AddDevice create", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected void initViews() {

        mTopView = (TopView)mView.findViewById(R.id.add_device_topView);
        mTopView.setText("返回","添加设备",null);
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

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_add_device;
    }
}
