package app.cddic.com.smarter.activity.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import app.cddic.com.smarter.fragment.AddContactFragment;
import app.cddic.com.smarter.fragment.ContactDetailFragment;

/**
 * SmartApp
 * app.cddic.com.smarter.activity.base
 * Created by Pantiy on 2017/5/11.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public class ContactActivity extends SingleFragmentActivity {

    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_CONTACT_NAME = "contactName";

    public static Intent newInstance(Context context, int type) {
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    public static Intent newInstance(Context context, int type, String contactName) {
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_CONTACT_NAME, contactName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        switch (getIntent().getIntExtra(EXTRA_TYPE, -1)) {
            case Type.ADD_CONTACT:
                return new AddContactFragment();
            case Type.CONTACT_DETAIL:
                return ContactDetailFragment.newInstance(getIntent().getStringExtra(EXTRA_CONTACT_NAME));
            default:
                return null;
        }
    }

    public static final class Type {
        public static final int ADD_CONTACT = 0;
        public static final int CONTACT_DETAIL = 1;
    }
}
