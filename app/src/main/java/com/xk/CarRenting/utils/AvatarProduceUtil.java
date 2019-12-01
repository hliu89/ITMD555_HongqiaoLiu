package com.xk.CarRenting.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xk.CarRenting.R;

import java.util.Random;

/**
 * Created by xk on 2016/7/28 17:23.
 */
public class AvatarProduceUtil {
    private int color;
    private Context context;
    private Random random;
    private int tag=1;

    public AvatarProduceUtil(Context context) {
        random = new Random();
        this.context=context;
    }

    public Bitmap getBitmapByText(String sText) {
        tag++;
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.layout_avatar, null);
        View root = view.findViewById(R.id.root);
        int i = random.nextInt(5);
        switch (tag%4) {
            case 0:
                color = R.color.indigo_500;
                break;
            case 1:
                color = R.color.red_500;
                break;
            case 2:
                color = R.color.blue_500;
                break;
            case 3:
                color = R.color.teal_500;
                break;
        }
        root.setBackgroundColor(context.getResources().getColor(color));
        //TextView
        TextView text = (TextView) view.findViewById(R.id.text);
        if (sText.length()==1) {
            text.setText(sText.substring(0,1));
            text.setTextSize(50);
        }else{
            text.setText(sText.substring(0,1));
            text.setTextSize(50);
        }
        //
        view.setDrawingCacheEnabled(true);
        //，，bitmapnull
        view.measure(View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY));
        //，
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //Bitmap
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;

    }
}
