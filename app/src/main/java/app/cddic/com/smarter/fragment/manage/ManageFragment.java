package app.cddic.com.smarter.fragment.manage;


import app.cddic.com.smarter.fragment.BaseFragment;

/**
 * SmartSecurity-Manager
 * app.edu.cdu.com.smartsecurity_manager.fragment
 * Created by Pantiy on 2017/3/30.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public abstract class ManageFragment extends BaseFragment {

    protected String mFragmentName;

    public ManageFragment() {
        setFragmentName();
    }

    protected abstract void setFragmentName();

    public String getFragmentName() {
        return mFragmentName;
    }

    public static class Type {
        public static final int ACCOUNT = 0;
        public static final int MESSAGE = 1;
        public static final int DATA = 2;
        public static final int HISTORY = 3;
        public static final int PRIVACY = 4;
        public static final int PLUGIN = 5;
    }
}
