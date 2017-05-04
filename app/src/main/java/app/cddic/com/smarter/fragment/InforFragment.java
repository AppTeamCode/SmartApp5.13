package app.cddic.com.smarter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import app.cddic.com.smarter.R;

/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.fragment
 * 文件名：  ImforFragment
 * 创建者：
 * 创建时间： 2017/4/15 18:16
 * 描述：用户其他信息页面
 */

public class InforFragment extends BaseFragment {
    private TextView next2;
    private TextView back2;
    private Spinner mSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info,null);
        next2 = (TextView) v.findViewById(R.id.tv_next2);
        mSpinner = (Spinner) v.findViewById(R.id.sp_sexSpinner);
        back2 = (TextView) v.findViewById(R.id.tv_back2);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,new CheckPhoneFragment()).commit();
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.container,new ACNumberFragment()).commit();
            }
        });
        return v;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {

    }

    @Override
    protected int setLayoutRes() {
        return 0;
    }
}
