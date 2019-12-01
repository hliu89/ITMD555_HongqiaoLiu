package com.xk.CarRenting.frontend.tools.autoqueryedittext;

import android.content.Context;
import android.widget.EditText;

import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xk on 2016/8/8 2:02.
 */
public class SearchUtil {
    private String currentSearchContent;
    private Map<String, String> searchMap;
    private EditText editText;
    private Context context;

    public SearchUtil(EditText editText, Context context) {
        searchMap = new HashMap<>();
        this.editText = editText;
        this.context = context;
    }

    public String getCurrentSearchContent() {
        return currentSearchContent;
    }

    public void setCurrentSearchContent(String currentSearchContent) {
        this.currentSearchContent = currentSearchContent;
    }

    /**
     *
     *
     * @param searchContent
     */
    public void search(final String searchContent) {
        currentSearchContent = searchContent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (searchMap.get(searchContent) != null && !searchMap.get(searchContent).equals("")) {
                    //，
                    if (currentSearchContent.equals(searchContent)) {
                        //
                        String result = searchMap.get(searchContent);
                        if (onSearchListener != null) {
                            onSearchListener.onSearchSuccess(result);
                        }
                    }
                } else {
                    //， 200
                    try {
                        Thread.sleep(200);
                        requestSearchContent(searchContent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void requestSearchContent(final String searchContent) {
        if (searchContent.equals("")) {
            if (onSearchListener != null) {
                onSearchListener.onSearchSuccess(null);
            }
            return;
        }
        if (searchMap.containsKey(searchContent)) {
            // return
            return;
        }
        //，map，
        searchMap.put(searchContent, "");


        //
        Request<JSONArray> queryUserByPhone = NoHttp.createJsonArrayRequest(Constant.url_my_friend, RequestMethod.POST);
        queryUserByPhone.add("action","queryFriend");
        queryUserByPhone.add("queryPhone",searchContent);
        CallServer.getRequestInstance().add(context, 0, queryUserByPhone, new HttpListener<JSONArray>() {
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                //
                JSONArray jsonArray= response.get();
                searchMap.put(searchContent, jsonArray.toString());
                //，
                if (currentSearchContent.equals(searchContent)) {
                    //
                    if (onSearchListener != null) {

                        onSearchListener.onSearchSuccess(jsonArray.toString());
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                //
                searchMap.remove(searchContent);
            }
        }, false, false, "");




    }

    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public interface OnSearchListener {
        void onSearchSuccess(String result);
    }
}
