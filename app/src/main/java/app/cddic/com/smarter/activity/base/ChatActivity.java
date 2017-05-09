package app.cddic.com.smarter.activity.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import app.cddic.com.smarter.fragment.ChatFragment;

/**
 * SmartApp
 * app.cddic.com.smarter.activity.base
 * Created by Pantiy on 2017/5/9.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public class ChatActivity extends SingleFragmentActivity {

    public static final String EXTRA_CONTACT_NAME = "contactName";

    public static Intent newInstance(Context context, String contactName) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CONTACT_NAME, contactName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String contactName = getIntent().getStringExtra(EXTRA_CONTACT_NAME);
        return ChatFragment.newInstance(contactName);
    }
}
