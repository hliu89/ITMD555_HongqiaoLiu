package com.baiiu.filter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiiu.filter.R;
import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.util.UIUtil;

import java.util.Arrays;
import java.util.List;

public class FixedTabIndicator extends LinearLayout {

    private Context context;
    private int mTabVisibleCount = 4;// tab

    /*
     *
     */
    private Paint mDividerPaint;
    private int mDividerColor = 0xFFdddddd;//
    private int mDividerPadding = 13;// padding

    /*
     *
     */
    private Paint mLinePaint;
    private float mLineHeight = 1;
    private int mLineColor = 0xFFeeeeee;


    private int mTabTextSize = 13;// ,sp
    private int mTabDefaultColor = 0xFF666666;//
    private int mTabSelectedColor = 0xFF008DF2;//
    private int drawableRight = 10;

    private int measureHeight;
    private int measuredWidth;

    private int mTabCount;//
    private int mCurrentIndicatorPosition;//
    private int mLastIndicatorPosition;//
    private OnItemClickListener mOnItemClickListener;


    public FixedTabIndicator(Context context) {
        this(context, null);
    }

    public FixedTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FixedTabIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    /**
     *
     */
    public interface OnItemClickListener {
        /**
         *
         *
         * @param v        view
         * @param position position
         * @param open     ，open,
         */
        void onItemClick(View v, int position, boolean open);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListenner) {
        this.mOnItemClickListener = itemClickListenner;
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.WHITE);
        setWillNotDraw(false);

        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setColor(mDividerColor);

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);

        mDividerPadding = UIUtil.dp(context, mDividerPadding);
        drawableRight = UIUtil.dp(context, drawableRight);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mTabCount - 1; ++i) {// tab
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == View.GONE) {
                continue;
            }

            canvas.drawLine(child.getRight(), mDividerPadding, child.getRight(), measureHeight - mDividerPadding, mDividerPaint);
        }


        //
        canvas.drawRect(0, 0, measuredWidth, mLineHeight, mLinePaint);

        //
        canvas.drawRect(0, measureHeight - mLineHeight, measuredWidth, measureHeight, mLinePaint);
    }

    /**
     *
     */
    public void setTitles(List<String> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalStateException("条目数量位空");
        }
        this.removeAllViews();

        mTabCount = list.size();
        for (int pos = 0; pos < mTabCount; ++pos) {
            addView(generateTextView(list.get(pos), pos));
        }

        postInvalidate();
    }

    public void setTitles(String[] list) {
        setTitles(Arrays.asList(list));
    }

    public void setTitles(MenuAdapter menuAdapter) {
        if (menuAdapter == null) {
            return;
        }
        this.removeAllViews();

        mTabCount = menuAdapter.getMenuCount();
        for (int pos = 0; pos < mTabCount; ++pos) {
            addView(generateTextView(menuAdapter.getMenuTitle(pos), pos));
        }
        postInvalidate();
    }


    private void switchTab(int pos) {
        TextView tv = getChildAtCurPos(pos);

        Drawable drawable = tv.getCompoundDrawables()[2];
        int level = drawable.getLevel();

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(tv, pos, level == 1);
        }

        if (mLastIndicatorPosition == pos) {
            //
            tv.setTextColor(level == 0 ? mTabSelectedColor : mTabDefaultColor);
            drawable.setLevel(1 - level);

            return;
        }

        mCurrentIndicatorPosition = pos;
        resetPos(mLastIndicatorPosition);

        //highLightPos(pos);
        tv.setTextColor(mTabSelectedColor);
        tv.getCompoundDrawables()[2].setLevel(1);

        mLastIndicatorPosition = pos;
    }

    /**
     *
     */
    public void resetPos(int pos) {
        TextView tv = getChildAtCurPos(pos);
        tv.setTextColor(mTabDefaultColor);
        tv.getCompoundDrawables()[2].setLevel(0);
    }

    /**
     *
     */
    public void resetCurrentPos() {
        resetPos(mCurrentIndicatorPosition);
    }

    /**
     * posTextView
     */
    public TextView getChildAtCurPos(int pos) {
        return (TextView) ((ViewGroup) getChildAt(pos)).getChildAt(0);
    }

    /**
     * TextViewweight，
     */
    private View generateTextView(String title, int pos) {
        // TextView
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTabTextSize);
        tv.setTextColor(mTabDefaultColor);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setMaxEms(6);//4
        Drawable drawable = getResources().getDrawable(R.drawable.level_filter);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        tv.setCompoundDrawablePadding(drawableRight);

        // TextViewRelativeLayout
        RelativeLayout rl = new RelativeLayout(context);
        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(-2, -2);
        rlParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl.addView(tv, rlParams);
        rl.setId(pos);

        // RelativeLayoutLinearLayout
        LayoutParams params = new LayoutParams(-1, -1);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        rl.setLayoutParams(params);

        rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                switchTab(v.getId());
            }
        });

        return rl;
    }

    /**
     *
     */
    public void highLightPos(int pos) {
        TextView tv = getChildAtCurPos(pos);
        tv.setTextColor(mTabSelectedColor);
        tv.getCompoundDrawables()[2].setLevel(1);
    }

    public int getCurrentIndicatorPosition() {
        return mCurrentIndicatorPosition;
    }

    public void setCurrentText(String text) {
        setPositionText(mCurrentIndicatorPosition, text);
    }

    public void setPositionText(int position, String text) {
        if (position < 0 || position > mTabCount - 1) {
            throw new IllegalArgumentException("position 越界");
        }
        TextView tv = getChildAtCurPos(position);
        tv.setTextColor(mTabDefaultColor);
        tv.setText(text);
        tv.getCompoundDrawables()[2].setLevel(0);
    }

    public int getLastIndicatorPosition() {
        return mLastIndicatorPosition;
    }
}
