package com.xk.CarRenting.frontend.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.xk.CarRenting.frontend.ui.HomeActivity;
import com.xk.CarRenting.frontend.baseActivity.BaseFragment;
import com.xk.CarRenting.utils.SharedPreferencesUtil;
import com.xk.CarRenting.utils.MyLocationListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ，
 * Created by LanKoXv on 2015/11/23.
 */
public class SplashFragment extends BaseFragment {
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    private ImageButton toolbarLeft;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_splash;
    }

    @Override
    protected void findViews(View v) {

    }

    @Override
    protected void setupViews(View v) {

    }

    @Override
    protected void setListener(View v) {

    }

    @Override
    protected void fetchData(View v) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            savePhoneIMEI();
            getLocate();
        }

        autoLogin();
    }

    private void savePhoneIMEI() {
        TelephonyManager telephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        SharedPreferencesUtil.saveString(getContext().getApplicationContext(), Constant.SPKEY_IMEI, telephonyMgr.getDeviceId());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePhoneIMEI();
                } else {
                    getActivity().finish();
                }
                break;
            default:
        }
    }
    /**
     * splashFragment
     *
     * @author xk
     * @time 2016/6/24 10:16
     */
    private void autoLogin() {
        Request<JSONObject> autoLoginRequest = NoHttp.createJsonObjectRequest(Constant.url_autologin, RequestMethod.POST);
        String currentUserPhonenumber = SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER);
        if (currentUserPhonenumber.equals("")) {
            //
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //fragment
                    getActivity().onBackPressed();
                }
            }, 800);
            return;
        }
        final long startAutoLoginDate = System.currentTimeMillis();

        autoLoginRequest.add("phoneNumber", currentUserPhonenumber);
        autoLoginRequest.add("IMEI", SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_IMEI));
        CallServer.getRequestInstance().add(getContext(), Constant.reuqest_autologin, autoLoginRequest, new HttpListener<JSONObject>() {

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                long stopAutoLoginDate = System.currentTimeMillis();
                //
                JSONObject jsonObject = response.get();
                Log.e("SplashFragment", "onSucceed" + response.get().toString());
                try {
                    if (jsonObject.getBoolean("isLogin")) {
                        if (isAdded()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toActivity(HomeActivity.class);
                                    getActivity().finish();
                                }
                            }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0);
                        }
                    } else {
                        new Handler().postDelayed(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          //fragment
                                                          getActivity().onBackPressed();
                                                      }
                                                  }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                //
                long stopAutoLoginDate = System.currentTimeMillis();
                if (SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER).equals("-1")) {
                    new Handler().postDelayed(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      //fragment
                                                      getActivity().onBackPressed();
                                                  }
                                              }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0
                    );
                } else {
                    if (isAdded()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toActivity(HomeActivity.class);
                                getActivity().finish();
                            }
                        }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0);
                    }
                }
            }
        }, false, false, "");
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//，，，，，
        option.setCoorType("bd09ll");//，gcj02，
        int span = 1000;
        option.setScanSpan(span);//，0，，1000ms
        option.setIsNeedAddress(false);//，，
        option.setOpenGps(true);//，false,gps
        option.setLocationNotify(true);//，false，gps1S1GPS
        option.setIsNeedLocationDescribe(true);//，false，，BDLocation.getLocationDescribe，“”
        option.setIsNeedLocationPoiList(true);//，false，POI，BDLocation.getPoiList
        option.setIgnoreKillProcess(false);//，true，SDKSERVICE，，stop，
        option.SetIgnoreCacheException(false);//，false，CRASH，
        option.setEnableSimulateGps(false);//，false，gps，
        mLocationClient.setLocOption(option);
    }

    public void getLocate() {
        mLocationClient = new LocationClient(getContext().getApplicationContext());     //LocationClient
        myListener = new MyLocationListener(mLocationClient);
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //
        mLocationClient.start();
    }
}
