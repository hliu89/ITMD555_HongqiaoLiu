package com.baiiu.filter.adapter;

import android.view.View;
import android.widget.FrameLayout;

/**
 * author: baiiu
 * date: on 16/1/17 21:03
 * description:
 */
public interface MenuAdapter {

    /**
     *
     */
    int getMenuCount();

    /**
     * Title
     */
    String getMenuTitle(int position);

    /**
     *
     */
    int getBottomMargin(int position);


    /**
     * View
     */
    View getView(int position, FrameLayout parentContainer);
}
