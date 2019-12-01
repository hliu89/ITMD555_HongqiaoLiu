package com.xk.CarRenting.frontend.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xk.CarRenting.utils.ViewUtils;

public class DividerDecoration extends RecyclerView.ItemDecoration {
    /**
     *
     */
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    /**
     *
     */
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    //
    private Paint paint;

    //
    private int orientation;
    //
    private int color = 0xffB6B6B6;
    //
    private int size = 1;

    private boolean isDifferent;
    private Context context;

    public DividerDecoration(Context context) {
        this(VERTICAL, false, context);
    }

    /**
     * boolean
     *
     * @author xk
     * @time 2016/6/26 21:35
     */
    public DividerDecoration(int orientation, boolean isDifferent, Context context) {
        this.orientation = orientation;
        this.isDifferent = isDifferent;
        paint = new Paint();
        paint.setColor(color);
        this.context = context;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (orientation == VERTICAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    /**
     *
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    /**
     *
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    //
    protected void drawVertical(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + size;

            c.drawRect(left, top, right, bottom, paint);
        }
    }

    //
    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() + params.bottomMargin;
            int bottom = top + size;
//            c.drawRect(40, top, right-40, bottom, paint);
            c.drawRect(0, top, right, bottom, paint);
            if (isDifferent) {
                if (i == childCount) {
                    top = child.getTop() + params.bottomMargin + ViewUtils.dip2px(context, 10);
                    bottom = top + size;
                    c.drawRect(0, top, right, bottom, paint);
                }
            }
        }


    }
}