package com.xk.CarRenting.frontend.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.bean.CarBean;
import com.xk.CarRenting.bean.CarSourceBean;
import com.xk.CarRenting.bean.User;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.xk.CarRenting.frontend.adapter.TruckInfoAdapter;
import com.xk.CarRenting.frontend.tools.dropdownmenu.DropMenuAdapter;
import com.xk.CarRenting.frontend.tools.dropdownmenu.entity.FilterUrl;
import com.xk.CarRenting.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by xk on 2016/6/2 20:05.
 */
public class FindTruckFragment extends TabTopRefreshBaseFragment implements OnFilterDoneListener {
    private TruckInfoAdapter truckInfoAdapter;
    private DropDownMenu dropDownMenu;
    private String startplace = "%", stopplace = "%", starttime = "%";
    private TextView mFilterContentView;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_findcar;
    }

    @Override
    protected void findViews(View v) {
        super.findViews(v);
        dropDownMenu = ViewUtils.findViewById(v, R.id.dropDownMenu);
//        mFilterContentView = ViewUtils.findViewById(v, R.id.mFilterContentView);
    }

    @Override
    protected void setupViews(View v) {
        super.setupViews(v);
        truckInfoAdapter = new TruckInfoAdapter(getActivity(), rv_list);
        rv_list.setAdapter(truckInfoAdapter);
        initFilterDropDownView();

    }

    @Override
    protected void setListener(View v) {
        super.setListener(v);
        truckInfoAdapter.setOnDataEmptyListener(new TruckInfoAdapter.OnDataEmptyListener() {
            @Override
            public void onDataEmpty() {
                tv_without_info.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDataNotEmpty() {
                tv_without_info.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FilterUrl.instance().clear();
    }

    @Override
    protected void fetchData(View v) {
        super.fetchData(v);
        requestTruckSource(startplace, stopplace,starttime);

    }

    @Override
    public void onRefresh() {
        requestTruckSource(startplace, stopplace,starttime);

    }

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"Departure", "destination", "date"};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(getContext(), titleList, this));
    }

    /**
     *
     */
    private void requestTruckSource(String startplace, String stopplace, String starttime) {
        Request<JSONArray> registerRequest = NoHttp.createJsonArrayRequest(Constant.url_trucksource, RequestMethod.POST);
        registerRequest.add("action", "queryAllTruckSources");
        registerRequest.add("startplace", startplace);
        registerRequest.add("stopplace", stopplace);
        registerRequest.add("starttime", starttime);
        CallServer.getRequestInstance().add(getContext(), Constant.reuqest_queryalltrucksource, registerRequest, new HttpListener<JSONArray>() {
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                ArrayList<CarSourceBean> carSourceBeans = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.get();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = (JSONObject) jsonArray.get(i);
                        User userBean = new User();
                        JSONObject userJson = json.getJSONObject("user");
                        userBean.setPhone(userJson.getString("phoneNumber"));
                        userBean.setUsername(userJson.getString("username"));
                        userBean.setProvince(userJson.getString("province"));
                        userBean.setCity(userJson.getString("city"));
                        userBean.setPiecearea(userJson.getString("piecearea"));
                        userBean.setIdcard(userJson.getString("idcard"));
                        userBean.setAvatarurl(userJson.getString("avatarurl"));
                        userBean.setIntroduce(userJson.getString("introduce"));

                        CarBean carBean = new CarBean();
                        JSONObject truckJson = json.getJSONObject("truck");
                        carBean.setTruckBrith(truckJson.getString("truckbirth"));
                        carBean.setLength(truckJson.getString("length"));
                        carBean.setWidth(truckJson.getString("width"));
                        carBean.setHight(truckJson.getString("height"));
                        carBean.setWeight(truckJson.getString("weight"));
                        carBean.setTruckCardNumber(truckJson.getString("truckcardnumber"));
                        carBean.setVariety(truckJson.getString("variety"));

                        CarSourceBean carSourceBean = new CarSourceBean();
                        carSourceBean.setIntrocd(json.getString("introcduce"));
                        carSourceBean.setLoad_date(json.getString("load_date"));
                        carSourceBean.setPublish_date(json.getString("publish_date"));
                        carSourceBean.setStart_place(json.getString("start_place"));
                        carSourceBean.setStart_place_point(json.getString("start_place_point"));
                        carSourceBean.setState(json.getString("state"));
                        carSourceBean.setStop_place(json.getString("stop_place"));
                        carSourceBean.setCarBean(carBean);
                        carSourceBean.setUserBean(userBean);
                        carSourceBeans.add(carSourceBean);
                    }

                    truckInfoAdapter.setData(carSourceBeans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, true, false, "");
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }
        dropDownMenu.close();
        switch (position) {
            case 0:
                startplace = positionTitle;
                break;
            case 1:
                stopplace = positionTitle;
                break;
            case 2:
                starttime = convertTime(positionTitle);
                break;
        }
        requestTruckSource(startplace,stopplace,starttime);
        Log.e("FindTruckFragment", startplace + "-" + stopplace + "-" + starttime);
    }

    private String convertTime(String positionTitle) {
        String[] one = positionTitle.split("month");
        String[] two = one[1].split("day");
        return Calendar.getInstance().get(Calendar.YEAR) + "/" + figureConvert(one[0]) + "/" + figureConvert(two[0]) + "";
    }

    private String figureConvert(String big) {
        String result = "";
        if (big.endsWith("十")) {
            //10  20  30  40  50  60
            if (big.length() == 1) {
                return "10";
            } else {
                return getFigure(big.charAt(0) + "") + "0";
            }
        } else {
            if (!big.startsWith("十")) {
                big = big.replace("十", "");
            }
        }

        for (int i = 0; i < big.length(); i++) {
            result += getFigure(big.charAt(i) + "");
        }

        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    private String getFigure(String s) {
        switch (s) {
            case "一":
                return "1";
            case "二":
                return "2";
            case "三":
                return "3";
            case "四":
                return "4";
            case "五":
                return "5";
            case "六":
                return "6";
            case "七":
                return "7";
            case "八":
                return "8";
            case "九":
                return "9";
            case "十":
                return "1";
        }
        return "";
    }
}
