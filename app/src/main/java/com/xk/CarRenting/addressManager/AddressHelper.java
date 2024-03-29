package com.xk.CarRenting.addressManager;

import android.content.Context;


import com.xk.CarRenting.app.App;
import com.xk.CarRenting.utils.XmlUtils;

import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.AddressPicker.*;

/**
 * Created by warren on 11/7/15.
 *
 */
public class AddressHelper {
    private List<AddressPicker.Province> provinces;

    public AddressHelper() {
        provinces = XmlUtils.parseArea(App.getInstance(), "China_area.xml");
    }

    public AddressHelper(Context context) {
        provinces = XmlUtils.parseArea(context, "China_area.xml");
    }

    /**
     *  "pcode:11025"  11025
     *
     * @param rawString
     * @return
     */
    public static String getCodeInRaw(String rawString) {
        if (!rawString.contains(":"))
            return "";

        String[] code = rawString.split(":");
        if (code.length < 2)
            return "";

        return code[1];
    }

    // PCODE
    public AddressPicker.Province getProvinceByPCode(String pCode) {
        if (pCode == null)
            return null;
        for (int i = 0; i < provinces.size(); i++) {
            if (provinces.get(i).getAreaId().equals(pCode)) {
                return provinces.get(i);
            }
        }
        return null;
    }

    public AddressPicker.City getCity_Code(Province province, String cCode) {
        if (cCode == null)
            return null;
        for (int i = 0; i < province.getCities().size(); i++) {
            if (province.getCities().get(i).getAreaId().equals(cCode)) {
                return province.getCities().get(i);
            }
        }
        return null;
    }

    public County getBydCodeToCountry(City city, String dCode) {
        if (dCode == null)
            return null;
        for (County county : city.getCounties())
            if (county.getAreaId().equals(dCode))
                return county;
        return null;
    }

    /**
     *  LeanCloud
     *
     * @param rawAddress
     * @return
     */
    public String getFullAddress(List<String> rawAddress) {

        StringBuilder address = new StringBuilder();

        Province province = null;
        City city = null;
        County county;
        String detail;

        for (String rawData : rawAddress) {
            if (rawData.contains("pcode")) {
                province = getProvinceByPCode(getCodeInRaw(rawData));
                address.append(province.getAreaName()).append(" ");
            }

            if (rawData.contains("ccode") && province != null) {
                city = getCity_Code(province, getCodeInRaw(rawData));
                address.append(city.getAreaName()).append(" ");
            }

            if (rawData.contains("dcode") && city != null) {
                county = getBydCodeToCountry(city, getCodeInRaw(rawData));
                address.append(county.getAreaName()).append(" ");
            }
            if (rawData.contains("details")) {
                detail = getCodeInRaw(rawData);
                address.append(detail);
            }
        }

        return address.toString();
    }

    /**
     *  LeanCloud
     *  {@link #getFullAddress(List)}
     *
     * @param rawAddress
     * @return
     */
    public String getCityAddress(List<String> rawAddress) {

        StringBuilder address = new StringBuilder();

        Province province = null;
        City city;

        for (String rawData : rawAddress) {
            if (rawData.contains("pcode")) {
                province = getProvinceByPCode(getCodeInRaw(rawData));
//                address.append(province.getName()).append(" ");
            }

            if (rawData.contains("ccode") && province != null) {
                city = getCity_Code(province, getCodeInRaw(rawData));
                address.append(city.getAreaName()).append(" ");
            }
        }

        return address.toString();
    }
}
