/**
 * Project Name:Gokit
 * File Name:NetUtils.java
 * Package Name:com.xpg.gokit.utils
 * Date:2014-11-18 10:06:37
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xk.CarRenting.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * .
 *
 * @author Sunny Ding
 * 
 *         *
 */
public class NetUtils {

	/**
	 * Wifi.
	 *
	 * @param context
	 *
	 * @return boolean
	 * 
	 *         *
	 */
	static public boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				if (mWiFiNetworkInfo.isAvailable()) {
					return mWiFiNetworkInfo.isConnected();
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * .
	 *
	 * @param context
	 *
	 * @return boolean
	 * 
	 *         *
	 */
	static public boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				if (mMobileNetworkInfo.isAvailable()) {
					return mMobileNetworkInfo.isConnected();
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * WIFI.
	 *
	 * @param context
	 *
	 * @return ConnectedType
	 * 
	 *         *
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			// NetWorkInfo
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			// NetWorkInfo；
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * WIFISSID.
	 *
	 * @param context
	 *
	 * @return ssid
	 * 
	 *         *
	 */
	public static String getCurentWifiSSID(Context context) {
		String ssid = "";
		if (context != null) {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			ssid = wifiInfo.getSSID();
			if (ssid.substring(0, 1).equals("\"") && ssid.substring(ssid.length() - 1).equals("\"")) {
				ssid = ssid.substring(1, ssid.length() - 1);
			}
		}
		return ssid;
	}

	/**
	 * wifi.
	 *
	 * @param c
	 *
	 * @return the current wifi scan result
	 */
	static public List<ScanResult> getCurrentWifiScanResult(Context c) {
		WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		wifiManager.startScan();
		return wifiManager.getScanResults();
	}

}
