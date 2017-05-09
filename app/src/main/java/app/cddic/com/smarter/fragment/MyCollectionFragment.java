package app.cddic.com.smarter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.MainActivity;
import app.cddic.com.smarter.widget.TopView;

/**
 * Created by 小帆哥 on 2017/5/9.
 */

public class MyCollectionFragment extends BaseFragment {

    private TopView mTopView;
    private EditText mSerach;
    private Button mEdit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "MyCollection create", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        mTopView =findView(R.id.mycollection_topView);
        mTopView.setText("设置", "我的收藏", null);
        mSerach=findView(R.id.search_editText);
        mEdit=findView(R.id.edit_button);
    }

    @Override
    protected void setupAdapters() {

    }
    @Override
    protected void setupListeners() {
        mTopView.setupListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        }, null);
    }
    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_mycollection;
    }
}
