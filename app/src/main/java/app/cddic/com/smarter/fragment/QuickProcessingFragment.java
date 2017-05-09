package app.cddic.com.smarter.fragment;

/**
 * Created by 小帆哥 on 2017/5/9.
 */

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import app.cddic.com.smarter.R;
        import app.cddic.com.smarter.activity.base.MainActivity;
        import app.cddic.com.smarter.widget.TopView;


public class QuickProcessingFragment extends BaseFragment {
    private TopView mTopView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "QuickProcessing create", Toast.LENGTH_SHORT).show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected void initViews() {

        mTopView =findView(R.id.quick_processing_topView);
        mTopView.setText("联系人","设备关联人",null);
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
        },null);

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_quick_processing;
    }
}

