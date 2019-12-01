/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xk.CarRenting.nohttpHelper;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;


/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author YOLANDA;
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {
    private static final String TAG = "HttpResponseListener";
    /**
     * Dialog.
     */
    private WaitDialog mWaitDialog;

    private Request<?> mRequest;

    /**
     * .
     */
    private HttpListener<T> callback;

    /**
     * dialog.
     */
    private boolean isLoading;

    /**
     * @param context      contextdialog.
     * @param request      .
     * @param httpCallback .
     * @param canCancel    .
     * @param isLoading    dialog.
     */
    public HttpResponseListener(Context context, Request<?> request, HttpListener<T> httpCallback, boolean canCancel, boolean isLoading,String dialogMsg) {
        this.mRequest = request;
        if (context != null && isLoading) {
            mWaitDialog = new WaitDialog(context,dialogMsg);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel(true);
                }
            });
        }
        this.callback = httpCallback;
        this.isLoading = isLoading;
    }

    /**
     * , dialog.
     */
    @Override
    public void onStart(int what) {
        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing())
            mWaitDialog.show();
    }

    /**
     * , dialog.
     */
    @Override
    public void onFinish(int what) {
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }

    /**
     * .
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null){

            Log.e(TAG,"onSucceed----->"+response.get().toString());
            callback.onSucceed(what, response);
        }
    }

    /**
     * .
     */
    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        if (exception instanceof ServerError) {//
            if (500 == responseCode) {
//                Toast.show("。");
            } else if (501 == responseCode) {
//                Toast.show("。");
            } else if (502 == responseCode) {
//                Toast.show("。");
            } else if (503 == responseCode) {
//                Toast.show("。");
            } else if (504 == responseCode) {
//                Toast.show("。");
            } else if (505 == responseCode) {
//                Toast.show("HTTP。");
            } else {
//                Toast.show("。");
            }
        } else if (exception instanceof NetworkError) {//
//            Toast.show("。");
        } else if (exception instanceof TimeoutError) {//
//            Toast.show("，。");
        } else if (exception instanceof UnKnownHostError) {//
//            Toast.show("。");
        } else if (exception instanceof URLError) {// URL
//            Toast.show("URL。");
        } else if (exception instanceof NotFoundCacheError) {
            //
//            Toast.show("。");
        } else {//
            if (responseCode == 400) {//。
//                Toast.show("，。");
            } else if (responseCode == 403) {//
//                Toast.show("，。");
            } else if (responseCode == 404) {//
//                Toast.show("，。");
            } else {// 400-417，
//                Toast.show("，。");

//                Toast.show("。");}
            }
        }
        Logger.e("error：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, url, tag, exception, responseCode, networkMillis);
    }

}
