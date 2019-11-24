package com.infinet.leviathan.myapplication.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.infinet.leviathan.myapplication.R;
import com.infinet.leviathan.myapplication.ui.adapter.LoginPagerAdapter;
import com.infinet.leviathan.myapplication.ui.base.BaseActivity;
import com.infinet.leviathan.myapplication.ui.fragment.SplashFragment;
import com.infinet.leviathan.myapplication.utils.ViewUtils;


public class LoginActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LoginPagerAdapter adapter;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        tabLayout = ViewUtils.findViewById(this, R.id.tl_login_tab_layout);
        viewPager = ViewUtils.findViewById(this, R.id.vp_login_pager);
    }

    @Override
    protected void setupViews(Bundle bundle) {

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("Splash")
                .add(R.id.root, new SplashFragment())
                .commit();

        tabLayout.addTab(tabLayout.newTab().setText("登录"));
        tabLayout.addTab(tabLayout.newTab().setText("注册"));

        adapter = new LoginPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void fetchData() {


    }

}
