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

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;


/**
 * Created in Oct 23, 2015 1:00:56 PM.
 *
 * @author YOLANDA;
 */
public class CallServer {

    private static CallServer callServer;

    /**
     * .
     */
    private RequestQueue requestQueue;

    /**
     * .
     */
    private static DownloadQueue downloadQueue;

    private CallServer() {
        requestQueue = NoHttp.newRequestQueue();
    }

    /**
     * .
     */
    public synchronized static CallServer getRequestInstance() {
        if (callServer == null)
            callServer = new CallServer();
        return callServer;
    }

    /**
     * .
     */
    public static DownloadQueue getDownloadInstance() {
        if (downloadQueue == null)
            downloadQueue = NoHttp.newDownloadQueue();
        return downloadQueue;
    }

    /**
     * .
     *
     * @param context   contextdialog.
     * @param what      , {@link HttpListener}, what.
     * @param request   .
     * @param callback  .
     * @param canCancel .
     * @param isLoading dialog.
     */
    public <T> void add(Context context, int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean isLoading,String dialogMsg) {
        requestQueue.add(what, request,  new HttpResponseListener<T>(context, request, callback, canCancel, isLoading,dialogMsg));
    }

    /**
     * sign.
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * .
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    /**
     * app.
     */
    public void stopAll() {
        requestQueue.stop();
    }

}
