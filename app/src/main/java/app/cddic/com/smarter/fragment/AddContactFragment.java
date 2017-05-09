package app.cddic.com.smarter.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

;
import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by VileWind on 2017/5/3 0003.
 */

public class AddContactFragment extends BaseFragment {
    TopView mTopView;
    private ImageButton mScanQRCode;
    private ImageButton mConditionalSearch;
    @Override
    protected void initViews() {

        mTopView=(TopView)mView.findViewById(R.id.add_contact_topView);
        mTopView.setText("联系人","添加",null);


        mScanQRCode=(ImageButton)mView.findViewById(R.id.Scan_QR_code_btn);

        mConditionalSearch=(ImageButton)mView.findViewById(R.id.conditional_search_btn);
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
        },null);

        mScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫描二维码
            }
        });
        mConditionalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按条件查找
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_add_contact;
    }
}
