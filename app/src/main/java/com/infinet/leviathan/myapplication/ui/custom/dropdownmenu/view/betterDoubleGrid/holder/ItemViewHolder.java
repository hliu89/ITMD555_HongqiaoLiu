package com.infinet.leviathan.myapplication.ui.custom.dropdownmenu.view.betterDoubleGrid.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.infinet.leviathan.myapplication.R;
import com.infinet.leviathan.myapplication.utils.ViewUtils;

import filter.util.UIUtil;
import filter.view.FilterCheckedTextView;


/**
 * auther: baiiu
 * time: 16/6/5 05 23:35
 * description:
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final FilterCheckedTextView textView;
    private View.OnClickListener mListener;

    public ItemViewHolder(Context mContext, ViewGroup parent, View.OnClickListener mListener) {
        super(UIUtil.infalte(mContext, R.layout.holder_item, parent));
        textView =  ViewUtils.findViewById(itemView,R.id.tv_item);
        this.mListener = mListener;
    }


    public void bind(String s) {
        textView.setText(s);
        textView.setTag(s);
        textView.setOnClickListener(mListener);
    }
}
