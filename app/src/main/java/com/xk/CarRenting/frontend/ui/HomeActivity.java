package com.xk.CarRenting.frontend.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.frontend.baseActivity.BaseActivity;
import com.xk.CarRenting.frontend.tools.MToolbar;
import com.xk.CarRenting.frontend.fragment.FragmentFactory;
import com.xk.CarRenting.frontend.fragment.HomeBottomTabFragment;
import com.xk.CarRenting.utils.ViewUtils;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private MToolbar toolbar;
    private FragmentFactory fragmentFactory;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this,R.id.toolbar);
    }

    @Override
    protected void setupViews(Bundle bundle) {
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void fetchData() {
        ((HomeBottomTabFragment) getSupportFragmentManager().findFragmentByTag("HomeBottomTabFragment")).selectBottomTab(1);
    }


    @Override
    public void onClick(View v) {

    }


    /**
     * fragment
     *
     * @author xk
     * @time 2016/6/4 11:25
     */
    public void selectContentFragment(int tabPosition) {
        FragmentManager fm = getSupportFragmentManager();
        if (fragmentFactory == null) {
            fragmentFactory = new FragmentFactory();
        }
        if (!fragmentFactory.getFragment(tabPosition).isAdded()) {
//            Log.e("HomeActivity",""+tabPosition);
            fm.beginTransaction().add(R.id.home_content, fragmentFactory.getFragment(tabPosition), fragmentFactory.getFragment(tabPosition).getTag()).commit();
        }
        for (int i = 1; i < 5; i++) {
            if (i == tabPosition) {
//                Log.e("HomeActivity",""+i);
                fm.beginTransaction().show(fragmentFactory.getFragment(i)).commit();
            } else {
//                Log.e("HomeActivity",""+i);

                fm.beginTransaction().hide(fragmentFactory.getFragment(i)).commit();
            }
        }
    }

    public void setToolbar(int position) {
        if (position == 4) {
            toolbar.setVisibility(View.GONE);
            return;
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
        switch (position) {
            case 1:
                toolbar.setLeftTextView("Find car");
                toolbar.setRightTextView(null);
                break;
            case 2:
                toolbar.setLeftTextView("Find customer");
                toolbar.setRightTextView(null);

                break;
            case 3:
                toolbar.setRightTextView("more");
                toolbar.setLeftTextView(null);
                break;
        }
        toolbar.setTitle(Constant.tab_names[position - 1]);
    }
}
