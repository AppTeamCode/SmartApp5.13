package app.cddic.com.smarter.fragment;

import android.content.Intent;
import android.view.View;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;


/**
 * Created by 小帆哥 on 2017/4/25.
 */

public class AboutAndHelpFragment extends BaseFragment {
    private TopView mTopView;

    @Override
    protected void initViews() {
        mTopView = (TopView)findView(R.id.about_and_help_topView);
        mTopView.setText("设置", "关于与帮助", null);
    }

    @Override
    protected void setupAdapters() {

    }

    @Override
    protected void setupListeners() {
        mTopView.setupListeners(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        },null);
        }



            @Override
            protected int setLayoutRes() {
                return R.layout.fragment_about_and_help;
            }
        }