package com.xk.CarRenting.frontend.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.bean.TruckBean;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.xk.CarRenting.frontend.adapter.MyTrucksAdapter;
import com.xk.CarRenting.frontend.baseActivity.BaseActivity;
import com.xk.CarRenting.frontend.tools.DividerDecoration;
import com.xk.CarRenting.frontend.tools.MToolbar;
import com.xk.CarRenting.utils.SharedPreferencesUtil;
import com.xk.CarRenting.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xk on 2016/6/23 9:45.
 */
public class MyCarActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_SELECT_TRUCK_BACK = 0;
    private static final int RESULT_SELECT_TRUCK_SAVE = 1;
    private MToolbar toolbar;
    private RecyclerView recyclerView;
    private MyTrucksAdapter myTrucksAdapter;
    private boolean isSelectTruck;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_my_car);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        recyclerView = ViewUtils.findViewById(this, R.id.rl_mytrucks);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setRightTextView("Edit");
        toolbar.setTitle("my vehicle");
        myTrucksAdapter = new MyTrucksAdapter(mContext, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerDecoration(DividerDecoration.VERTICAL, true, mContext));
        recyclerView.setAdapter(myTrucksAdapter);
        if (mBundle != null) {
            isSelectTruck = mBundle.getBoolean("isSelectTruck", false);
        }


    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {
            }
        });
        toolbar.setOnTextViewClickListener(new MToolbar.OnTextViewClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
// TODO: by xk 2016/6/25 15:25
            }
        });

        myTrucksAdapter.setOnItemClickListener(new MyTrucksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (myTrucksAdapter.getItemCount() == position + 1) {
                    toActivity(AddCarActivity.class);
                } else {
                    if (isSelectTruck) {
                        //finish （）
                        String id = myTrucksAdapter.getDataList().get(position ).get_id();
                        String truckCardNumber = myTrucksAdapter.getDataList().get(position ).getTruckCardNumber();
                        getIntent().putExtra("truckid",id);
                        getIntent().putExtra("truckname",truckCardNumber);
                        setResult(RESULT_SELECT_TRUCK_SAVE,getIntent());
                        finish();
                    } else {
                        //addtruck

                    }
                }
            }
        });
    }

    @Override
    protected void fetchData() {
    }

    /**
     *
     */
    private void requestMyTrucksInfo() {
        Request<JSONArray> registerRequest = NoHttp.createJsonArrayRequest(Constant.url_truck, RequestMethod.POST);

        registerRequest.add("action", "queryAllTrucks");
        registerRequest.add("userid", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
        CallServer.getRequestInstance().add(this, Constant.reuqest_queryalltruck, registerRequest, new HttpListener<JSONArray>() {
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                ArrayList<TruckBean> truckBeens = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.get();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = (JSONObject) jsonArray.get(i);
                        TruckBean truckBean = new TruckBean();
                        truckBean.setHight(json.getString("height"));
                        truckBean.setLength(json.getString("length"));
                        truckBean.setTruckBrith(json.getString("truckbirth"));
                        truckBean.setTruckCardNumber(json.getString("truckcardnumber"));
                        truckBean.setVariety(json.getString("variety"));
                        truckBean.setWeight(json.getString("weight"));
                        truckBean.setWidth(json.getString("width"));
                        truckBean.set_id(json.getString("id"));
                        truckBeens.add(truckBean);
                    }
                    myTrucksAdapter.setDataList(truckBeens);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, true, true, "get my vehicle");

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestMyTrucksInfo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_truck:
                //activity

                break;
        }
    }
}
