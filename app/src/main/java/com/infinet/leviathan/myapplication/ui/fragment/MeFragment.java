package com.infinet.leviathan.myapplication.ui.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.infinet.leviathan.myapplication.R;
import com.infinet.leviathan.myapplication.app.Constant;
import com.infinet.leviathan.myapplication.bean.UserBean;
import com.infinet.leviathan.myapplication.ui.activity.LoginActivity;
import com.infinet.leviathan.myapplication.ui.base.BaseFragment;
import com.infinet.leviathan.myapplication.ui.custom.CircleImageView;
import com.infinet.leviathan.myapplication.ui.custom.OptionItemView;
import com.infinet.leviathan.myapplication.utils.SharedPreferencesUtil;
import com.infinet.leviathan.myapplication.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

/**
 * Created by xk on 2016/6/2 20:05.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private OptionItemView ov_exit;
    private OptionItemView my_trucks;
    private FrameLayout fl_tochangeInfo;
    private CircleImageView civ_owner_phone;
    private TextView tv_name, tv_introduce;
    private Gson gson = new Gson();

    @Override
    public void onResume() {
        super.onResume();
        String userInfo = PersistenceUtil.getStringFromFile(SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
        if (!userInfo.equals("")) {
            UserBean userBean = gson.fromJson(userInfo, UserBean.class);
            civ_owner_phone.setImageURL(userBean.getAvatarurl(),getActivity());
            tv_name.setText(userBean.getUsername());
            tv_introduce.setText(userBean.getIntroduce());
        }

    }

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_me;
    }

    @Override
    protected void findViews(View v) {
        ov_exit = ViewUtils.findViewById(v, R.id.ov_exit);
        my_trucks = ViewUtils.findViewById(v, R.id.my_trucks);
        fl_tochangeInfo = ViewUtils.findViewById(v, R.id.fl_tochangeInfo);
        tv_name = ViewUtils.findViewById(v, R.id.tv_name);
        tv_introduce = ViewUtils.findViewById(v, R.id.tv_introduce);
        civ_owner_phone = ViewUtils.findViewById(v, R.id.civ_owner_phone);
    }

    @Override
    protected void setupViews(View v) {

    }

    @Override
    protected void setListener(View v) {
        ov_exit.setOnClickListener(this);
        my_trucks.setOnClickListener(this);
        fl_tochangeInfo.setOnClickListener(this);
    }

    @Override
    protected void fetchData(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ov_exit:
                Request<JSONObject> logoutRequest = NoHttp.createJsonObjectRequest(Constant.url_logout, RequestMethod.POST);
                logoutRequest.add("phoneNumber", SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
                SharedPreferencesUtil.saveString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER, "-1");
                CallServer.getRequestInstance().add(getContext(), Constant.reuqest_what_logout, logoutRequest, new HttpListener<JSONObject>() {
                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        getActivity().finish();
                        toActivity(LoginActivity.class);
                    }

                    @Override
                    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                        getActivity().finish();
                        toActivity(LoginActivity.class);
                    }
                }, true, false, "");

                break;
            case R.id.my_trucks:
                toActivity(MyTrucksActivity.class);
                break;
            case R.id.fl_tochangeInfo:
                toActivity(ChangeMyInfoActivity.class);
                break;
        }
    }
}
