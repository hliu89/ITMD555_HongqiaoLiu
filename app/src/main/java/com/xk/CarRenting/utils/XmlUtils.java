package com.xk.CarRenting.utils;

import android.content.Context;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cn.qqtheme.framework.picker.AddressPicker.*;


/**
 * xml
 * Created by WilliamHao on 15/10/10.
 */
public class XmlUtils {

    public static ArrayList<Province> parseArea(Context context, String xmlPath) {
        ArrayList<Province> provinces = new ArrayList<>();
        Province province = null;

        ArrayList<City> cities = new ArrayList<>();
        City city = null;

        ArrayList<County> districts = new ArrayList<>();
        County county = null;

        XmlPullParser xmlParser = Xml.newPullParser();

        try {
            //，
            InputStream inputStream = context.getResources().getAssets().open(xmlPath);
            xmlParser.setInput(inputStream, "utf-8");
            //，，，，，。
            int evtType = xmlParser.getEventType();
            //，
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParser.getName();
                        //river，
                        if (tag.equalsIgnoreCase("province")) {
                            province = new Province();
                            //river
                            province.setAreaId(xmlParser.getAttributeValue(null, "provinceID"));
                            province.setAreaName(xmlParser.getAttributeValue(null, "province"));
                        }
                        if (tag.equalsIgnoreCase("City")) {
                            city = new City();
                            city.setAreaId(xmlParser.getAttributeValue(null, "CityID"));
                            city.setAreaName(xmlParser.getAttributeValue(null, "City"));
                        }
                        if (tag.equalsIgnoreCase("Piecearea")) {
                            county = new County();
                            county.setAreaId(xmlParser.getAttributeValue(null, "PieceareaID"));
                            county.setAreaName(xmlParser.getAttributeValue(null, "Piecearea"));
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        //river，river
                        if (xmlParser.getName().equalsIgnoreCase("Piecearea") && county != null) {
                            districts.add(county);
                            county = null;
                        }
                        if (xmlParser.getName().equalsIgnoreCase("City") && city != null) {
                            city.setCounties(districts);
                            cities.add(city);
                            city = null;
                            districts = new ArrayList<>();
                        }
                        if (xmlParser.getName().equalsIgnoreCase("province") && province != null) {
                            province.setCities(cities);
                            provinces.add(province);
                            province = null;
                            cities = new ArrayList<>();
                        }
                        break;
                    default:
                        break;

                }
                //xml，river
                evtType = xmlParser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return provinces;
    }

}
