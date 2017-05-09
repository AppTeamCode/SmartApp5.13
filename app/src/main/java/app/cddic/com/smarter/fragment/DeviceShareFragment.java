package app.cddic.com.smarter.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by 小帆哥 on 2017/4/26.
 */

public class DeviceShareFragment extends BaseFragment {

    private TopView mTopView;
    private Button mConfirmShareButton;
    private EditText mLeaveWord;
    @Override
    protected void initViews(){
        mTopView = (TopView)findView(R.id.device_share_topView);
        mTopView.setText("设备","分享","取消");
        mConfirmShareButton =(Button)findView(R.id.confirm_share_button);
        mLeaveWord  = (EditText)findView(R.id.leave_word);
        mLeaveWord.setSelection(mLeaveWord.getText().length());
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {
        mTopView.setupListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        },new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        mConfirmShareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_device_share;
    }
}
