package com.xk.CarRenting.frontend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.xk.CarRenting.frontend.baseActivity.BaseActivity;
import com.xk.CarRenting.frontend.tools.MToolbar;
import com.xk.CarRenting.frontend.tools.OptionItemView;
import com.xk.CarRenting.utils.SharedPreferencesUtil;
import com.xk.CarRenting.utils.ViewUtils;
import com.xk.CarRenting.utils.XmlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by xk on 2016/6/6 17:57.
 */
public class PublishSourceActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_SELECT_EXACT_POSITION_OK = 0;
    private static final int RESULT_SELECT_EXACT_POSITION_CANCEL = 1;
    private static final int REQUEST_INTRODUCE = 0;
    private static final int RESULT_INTRODUCE_SAVE = 0;
    private static final int RESULT_INTRODUCE_BACK = 1;

    private static final int RESULT_SELECT_TRUCK_BACK = 0;
    private static final int RESULT_SELECT_TRUCK_SAVE = 1;

    private static final int REQUEST_SELECT_TRUCK = 1;
    private static final int REQUEST_SELECT_EXACT_POSITION = 2;

    private OptionItemView otv_select_load_date, otv_select_stop_place, otv_select_truck, otv_add_introduce, otv_select_exact_start_place, otv_select_start_place;
    private MToolbar toolbar;
    private Button btn_publish;
    private AlertDialog tipsDialog;
    private String detailPosition;
    private String introduce;
    private String truckid;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_publish);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        otv_select_stop_place = ViewUtils.findViewById(this, R.id.otv_select_stop_place);
        otv_select_truck = ViewUtils.findViewById(this, R.id.otv_select_truck);
        otv_add_introduce = ViewUtils.findViewById(this, R.id.otv_add_introduce);
        otv_select_exact_start_place = ViewUtils.findViewById(this, R.id.otv_select_exact_start_place);
        otv_select_load_date = ViewUtils.findViewById(this, R.id.otv_select_load_date);
        otv_select_start_place = ViewUtils.findViewById(this, R.id.otv_select_start_place);
        btn_publish = ViewUtils.findViewById(this, R.id.btn_publish);

    }

    @Override
    protected void setupViews(Bundle bundle) {
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        if (mBundle != null) {
            toolbar.setTitle(mBundle.getCharSequence("title").toString());
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
        otv_select_load_date.setOnClickListener(this);
        otv_select_exact_start_place.setOnClickListener(this);
        otv_add_introduce.setOnClickListener(this);
        otv_select_truck.setOnClickListener(this);
        otv_select_stop_place.setOnClickListener(this);
        otv_select_start_place.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otv_select_load_date:
                selectLoadDate();
                break;
            case R.id.otv_select_exact_start_place:
                toActivityForResult(SelectExactPositionActivity.class, REQUEST_SELECT_EXACT_POSITION);
                break;
            case R.id.otv_add_introduce:
                toActivityForResult(IntroduceActivity.class, REQUEST_INTRODUCE);
                break;
            case R.id.otv_select_truck:
                Bundle bundle = new Bundle();
                bundle.putBoolean("isSelectTruck", true);
                //bundle mytruckitem
                toActivityForResult(MyCarActivity.class, REQUEST_SELECT_TRUCK, bundle);
                break;
            case R.id.otv_select_stop_place:
                selectStopPlace();
                break;
            case R.id.otv_select_start_place:
                selectStartPlace();
                break;
            case R.id.btn_publish:
                publish();
                break;
        }

    }

    private void publish() {
        if (mBundle != null) {
            if (mBundle.getCharSequence("title").toString().equals("release news")) {
                //
                if (otv_select_load_date.getLeftText().equals("choose a date")) {
                    toast("please choose a date");
                    return;
                }
                if (otv_select_start_place.getLeftText().equals("select a starting address")) {
                    toast("please select a starting address");
                    return;
                }
                if (otv_select_exact_start_place.getLeftText().equals("select an exact address")) {
                    toast("Please select an exact address");
                    return;
                }
                if (otv_select_stop_place.getLeftText().equals("")) {
                    toast("Please select the arrival address");
                    return;
                }
                if (otv_select_truck.getLeftText().equals("Choose the starting vehicle")) {
                    toast("Please select the starting vehicle");
                    return;
                }

                requestPublishTruckSource();
            } else {
                //
            }
        }
    }

    private void requestPublishTruckSource() {
        Request<JSONObject> registerRequest = NoHttp.createJsonObjectRequest(Constant.url_trucksource, RequestMethod.POST);
        registerRequest.add("action", "add");
        registerRequest.add("userid", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
        registerRequest.add("load_date", otv_select_load_date.getLeftText());
        registerRequest.add("start_place", otv_select_start_place.getLeftText());
        registerRequest.add("stop_place", otv_select_stop_place.getLeftText());
        registerRequest.add("start_place_point", detailPosition);
        registerRequest.add("introcduce", introduce);
        registerRequest.add("state", "goof");
        registerRequest.add("truck_id", truckid);

        CallServer.getRequestInstance().add(this, Constant.reuqest_addtrucksource, registerRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    JSONObject jsonObject = response.get();
                    boolean addtrucksourcestate = jsonObject.getBoolean("addtrucksourcestate");
                    if (addtrucksourcestate) {
                        finish();
                        toast("successful");
                    }else{
                        toast("failure");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                tipsDialog.dismiss();
                toast("failure");
            }
        }, true, true, "updating");

    }

    private void selectStopPlace() {
        AddressPicker picker = new AddressPicker(this, XmlUtils.parseArea(this, "China_area.xml"));
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                otv_select_stop_place.setLeftText((city + county).trim());
            }
        });
        picker.show();
    }

    private void selectStartPlace() {
        AddressPicker picker = new AddressPicker(this, XmlUtils.parseArea(this, "China_area.xml"));
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                otv_select_start_place.setLeftText((city + county).trim());
            }
        });
        picker.show();
    }

    private void selectLoadDate() {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        picker.setLabel("year", "month", "day");
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                otv_select_load_date.setLeftText(year + "year" + month + "month" + day + "day");
            }
        });
        Calendar calendar = Calendar.getInstance();
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_INTRODUCE) {
            if (resultCode == RESULT_INTRODUCE_SAVE) {
                //
                introduce = data.getStringExtra("introduce");
                if (introduce.length() > 0) {
                    otv_add_introduce.setLeftText(introduce);
                }
            }
        } else if (requestCode == REQUEST_SELECT_EXACT_POSITION) {
            if (resultCode == RESULT_SELECT_EXACT_POSITION_OK) {
                detailPosition =
                        data.getStringExtra("position");
                String address = data.getStringExtra("address");
                otv_select_exact_start_place.setLeftText(address);
            }
        } else if (requestCode == REQUEST_SELECT_TRUCK) {
            if (resultCode == RESULT_SELECT_TRUCK_SAVE) {
                truckid = data.getStringExtra("truckid");
                String truckname = data.getStringExtra("truckname");
                otv_select_truck.setLeftText(truckname);
            }
        }
    }
}




