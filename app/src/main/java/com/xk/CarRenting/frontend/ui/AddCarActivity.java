package com.xk.CarRenting.frontend.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.xk.CarRenting.frontend.baseActivity.BaseActivity;
import com.xk.CarRenting.frontend.tools.MToolbar;
import com.xk.CarRenting.utils.SharedPreferencesUtil;
import com.xk.CarRenting.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by xk on 2016/6/26 16:12.
 */
public class AddCarActivity extends BaseActivity implements View.OnClickListener {
    private MToolbar toolbar;
    private LinearLayout ll_select_truck_type, ll_factory_date;
    private TextView tv_factory_date,tv_truck_type;
    private Button btn_add_truck;
    private EditText et_truck_card_number,et_weight,et_height,et_length,et_width;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_addcar);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        ll_select_truck_type = ViewUtils.findViewById(this, R.id.ll_select_truck_type);
        ll_factory_date = ViewUtils.findViewById(this, R.id.ll_factory_date);
        tv_factory_date = ViewUtils.findViewById(this, R.id.tv_factory_date);
        tv_truck_type = ViewUtils.findViewById(this, R.id.tv_truck_type);
        btn_add_truck = ViewUtils.findViewById(this, R.id.btn_add_truck);
        et_truck_card_number = ViewUtils.findViewById(this, R.id.et_truck_card_number);
        et_weight = ViewUtils.findViewById(this, R.id.et_weight);
        et_height = ViewUtils.findViewById(this, R.id.et_height);
        et_length = ViewUtils.findViewById(this, R.id.et_length);
        et_width = ViewUtils.findViewById(this, R.id.et_width);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("add a new car");
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
        ll_select_truck_type.setOnClickListener(this);
        ll_factory_date.setOnClickListener(this);
        btn_add_truck.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_truck_type:
                OptionPicker optionPicker = new OptionPicker(this, new String[]{
                        "van", "van", "sedan", "SUV", "bus"
                });
                optionPicker.setOffset(2);
                optionPicker.setSelectedIndex(0);
                optionPicker.setTextSize(11);
                optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_truck_type.setText(option);
                    }
                });
                optionPicker.show();
                break;
            case R.id.ll_factory_date:
                DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
                picker.setRange(2000, 2020);//
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        tv_factory_date.setText(year+"year"+month+"month");
                    }
                });
                Calendar calendar = Calendar.getInstance();
                picker.setSelectedItem(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1);
                picker.show();
                break;
            case R.id.btn_add_truck:
                addTruck();
                break;
        }
    }

    private void addTruck() {
        Log.e("AddTruckActivity","addTruck");
        if (TextUtils.isEmpty(et_truck_card_number.getText().toString())) {
            toast("please input car id");
            return;
        }
        if (tv_truck_type.getText().toString().contains("please")) {
            toast("please select a model");
            return;
        }
        if (TextUtils.isEmpty(et_weight.getText().toString())) {
            toast("please input width");
            return;
        }
        if (TextUtils.isEmpty(et_length.getText().toString())) {
            toast("please input length");
            return;
        }if (TextUtils.isEmpty(et_width.getText().toString())) {
            toast("please input length");
            return;
        }if (TextUtils.isEmpty(et_height.getText().toString())) {
            toast("please input hight");
            return;
        }
        if (tv_factory_date.getText().toString().contains("please")) {
            toast("Please select the date of manufacture");
            return;
        }
        //
        requestAddTruck();
    }

    /**
     *
     * @author xk
     * @time 2016/7/20 16:23
     */
    private void requestAddTruck() {
        Request<JSONObject> registerRequest = NoHttp.createJsonObjectRequest(Constant.url_truck, RequestMethod.POST);
        registerRequest.add("action", "add");
        registerRequest.add("width",et_width.getText().toString() );
        registerRequest.add("height",et_height.getText().toString() );
        registerRequest.add("weight", et_weight.getText().toString());
        registerRequest.add("truckbirth",tv_factory_date.getText().toString() );
        registerRequest.add("variety", tv_truck_type.getText().toString());
        registerRequest.add("length", et_length.getText().toString());
        registerRequest.add("truckcardnumber", et_truck_card_number.getText().toString());
        registerRequest.add("userId",SharedPreferencesUtil.getString(this,Constant.SPKEY_CURRENTUSERPHONENUMBER));

        CallServer.getRequestInstance().add(this, Constant.reuqest_addtruck, registerRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                try {
                    Boolean  addTruckState= jsonObject.getBoolean("addtruckstate");
                    if (addTruckState) {
                            toast("successful");
                            AddCarActivity.this.finish();

                    } else {
                        toast("failure");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                toast("failure");
            }
        }, true, true, "Adding. . .");

    }
}
