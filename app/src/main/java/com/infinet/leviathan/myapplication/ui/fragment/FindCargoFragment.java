package com.infinet.leviathan.myapplication.ui.fragment;

import android.view.View;

import com.infinet.leviathan.myapplication.R;
import com.infinet.leviathan.myapplication.ui.adapter.CargoInfoAdapter;


/**
 * Created by xk on 2016/6/2 20:05.
 */
public class FindCargoFragment extends TabTopRefreshBaseFragment {
    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_findcargo;
    }

    @Override
    protected void findViews(View v) {
        super.findViews(v);
    }

    @Override
    protected void setupViews(View v) {
        super.setupViews(v);     
        rv_list.setAdapter(new CargoInfoAdapter(getContext()));

    }

    @Override
    protected void setListener(View v) {
        super.setListener(v);

    }

    @Override
    protected void fetchData(View v) {
        super.fetchData(v);
    }



}
