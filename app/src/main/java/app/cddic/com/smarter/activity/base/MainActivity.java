package app.cddic.com.smarter.activity.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.DrawerActivity.Type;
import app.cddic.com.smarter.adapter.DrawerFragmentPagerAdapter;
import app.cddic.com.smarter.adapter.DrawerItemsAdapter;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private static final int UN_CHOOSE_COLOR = 0xff3f51b5;
    private static final int CHOOSE_COLOR = 0xff47479f;
    private static final int DEVICE = 0;
    private static final int CONTACT = 1;
    private static final int MESSAGE = 2;

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private ImageView mUserAvatarOnTopBar;
    private ImageView mMoreIv;
    private ImageView mUserAvatarIv;
    private RadioGroup mBottomBarRadioGroup;
    private TextView mCurrentFragmentNameTv;
    private SearchView mSearchView;
    private ExpandableListView mDrawerItemsElv;

    private int mCurrent = -1;

    private int[] mFragmentName = new int[] {R.string.device, R.string.contact, R.string.message};
    private List<RadioButton> mRadioButtonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupAdapters();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.clearFocus();
    }

    @Override
    public void onHandleMsg(int MsgType) {

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mUserAvatarOnTopBar = (ImageView) findViewById(R.id.userAvatarOnTopBar_ImageView);
        mMoreIv = (ImageView) findViewById(R.id.more_imageView);
        mUserAvatarIv = (ImageView) findViewById(R.id.userAvatar_ImageView);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        changeSearchViewStyle();

        mCurrentFragmentNameTv = (TextView) findViewById(R.id.currentFragmentName_textView);
        mCurrentFragmentNameTv.setText(R.string.device);

        mDrawerItemsElv =
                (ExpandableListView) findViewById(R.id.drawerItems_expandableListView);
        mDrawerItemsElv.setGroupIndicator(null);
        mDrawerItemsElv.setDivider(null);
        mBottomBarRadioGroup = (RadioGroup) findViewById(R.id.bottomBar_RadioGroup);

        RadioButton radioButton = (RadioButton) findViewById(R.id.device_radioButton);
        radioButton.setBackgroundColor(CHOOSE_COLOR);
        mRadioButtonList.add(DEVICE, radioButton);
        radioButton = (RadioButton) findViewById(R.id.contact_radioButton);
        mRadioButtonList.add(CONTACT, radioButton);
        radioButton = (RadioButton) findViewById(R.id.message_radioButton);
        mRadioButtonList.add(MESSAGE, radioButton);
    }

    private void setupAdapters() {
        DrawerItemsAdapter drawerItemsAdapter = new DrawerItemsAdapter(this);
        mDrawerItemsElv.setAdapter(drawerItemsAdapter);
        DrawerFragmentPagerAdapter drawerFragmentPagerAdapter =
                new DrawerFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(drawerFragmentPagerAdapter);
    }

    private void setupListeners() {

        mUserAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUserAvatarOnTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        mMoreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrent) {
                    case CONTACT:
                        new AlertDialog.Builder(MainActivity.this)
                                .setItems(new String[]{"添加新联系人"},
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = ContactActivity.newInstance(MainActivity.this,
                                                        ContactActivity.Type.ADD_CONTACT);
                                                startActivity(intent);
                                            }
                                        })
                                .show();
                        break;
                    default:
                        break;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchCurrentState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomBarRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.device_radioButton:
                        mCurrent = DEVICE;
                        break;
                    case R.id.contact_radioButton:
                        mCurrent = CONTACT;
                        break;
                    case R.id.message_radioButton:
                        mCurrent = MESSAGE;
                        break;
                    default:
                        mCurrent = DEVICE;
                        break;
                }
                switchCurrentState(mCurrent);
            }
        });

        mDrawerItemsElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent intent;
                switch (groupPosition) {
                    case Type.MY_INFORMATION:
                        intent = DrawerActivity.newInstance(MainActivity.this,
                                Type.MY_INFORMATION);
                        startActivity(intent);
                        break;

                    case Type.MY_COLLECTION:
                        intent = DrawerActivity.newInstance(MainActivity.this,
                                Type.MY_COLLECTION);
                        startActivity(intent);
                        break;

                    case Type.SUPPORT:
                        intent = DrawerActivity.newInstance(MainActivity.this,
                                Type.SUPPORT);
                        startActivity(intent);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        mDrawerItemsElv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                skipToChild(groupPosition, childPosition);
                return true;
            }
        });
    }

    private void skipToChild(int groupPosition, int childPosition) {
        if (groupPosition != DrawerItemsAdapter.EXPANDABLE_POSITION) {
            return;
        }
        Intent intent = SettingActivity.newInstance(this, childPosition);
        startActivity(intent);
    }

    private void switchCurrentState(int current) {
        resetBackgroundColor();
        mRadioButtonList.get(current).setBackgroundColor(CHOOSE_COLOR);
        mCurrentFragmentNameTv.setText(mFragmentName[current]);
        mViewPager.setCurrentItem(current);
    }

    private void resetBackgroundColor() {
        for (int i = 0; i < mRadioButtonList.size(); i++) {
            mRadioButtonList.get(i).setBackgroundColor(UN_CHOOSE_COLOR);
        }
    }

    private void changeSearchViewStyle() {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        mSearchView.setBackground(getDrawable(R.drawable.search_view));
        try {
            Class<?> searchViewClass = mSearchView.getClass();
            Field field = searchViewClass.getDeclaredField("mSearchPlate");
            field.setAccessible(true);
            View view = (View) field.get(mSearchView);
            view.setBackground(null);
            field.setAccessible(false);
        } catch (NoSuchFieldException ne) {
            Log.e(TAG, "get field ", ne);
        } catch (IllegalAccessException iae) {
            Log.e(TAG, "field.get() ", iae);
        }
    }
}
