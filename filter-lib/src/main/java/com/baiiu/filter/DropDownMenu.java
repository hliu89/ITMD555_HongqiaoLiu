package com.baiiu.filter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.util.SimpleAnimationListener;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FixedTabIndicator;


/**
 * Created by baiiu.
 *
 */
public class DropDownMenu extends RelativeLayout implements View.OnClickListener, FixedTabIndicator.OnItemClickListener {

    private FixedTabIndicator fixedTabIndicator;
    private FrameLayout frameLayoutContainer;

    private View currentView;

    private Animation dismissAnimation;
    private Animation occurAnimation;
    private Animation alphaDismissAnimation;
    private Animation alphaOccurAnimation;

    private MenuAdapter mMenuAdapter;

    public DropDownMenu(Context context) {
        this(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setContentView(findViewById(R.id.mFilterContentView));
    }

    public void setContentView(View contentView) {
        removeAllViews();

        /*
         * 1.
         */
        fixedTabIndicator = new FixedTabIndicator(getContext());
        fixedTabIndicator.setId(R.id.fixedTabIndicator);
        addView(fixedTabIndicator, -1, UIUtil.dp(getContext(), 50));

        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(BELOW, R.id.fixedTabIndicator);

        /*
         * 2.contentView,
         */
        addView(contentView, params);

        /*
         * 3.,list
         */
        frameLayoutContainer = new FrameLayout(getContext());
        frameLayoutContainer.setBackgroundColor(getResources().getColor(R.color.black_p50));
        addView(frameLayoutContainer, params);

        frameLayoutContainer.setVisibility(GONE);

        initListener();
        initAnimation();
    }

    public void setMenuAdapter(MenuAdapter adapter) {
        verifyContainer();

        mMenuAdapter = adapter;
        verifyMenuAdapter();

        //1.title
        fixedTabIndicator.setTitles(mMenuAdapter);

        //2.view
        setPositionView();
    }

    /**
     * :
     * 1.view,
     * 2.view
     * <p/>
     *
     */
    public void setPositionView() {
        int count = mMenuAdapter.getMenuCount();
        for (int position = 0; position < count; ++position) {
            setPositionView(position, findViewAtPosition(position), mMenuAdapter.getBottomMargin(position));
        }
    }

    public View findViewAtPosition(int position) {
        verifyContainer();

        View view = frameLayoutContainer.getChildAt(position);
        if (view == null) {
            view = mMenuAdapter.getView(position, frameLayoutContainer);
        }

        return view;
    }

    private void setPositionView(int position, View view, int bottomMargin) {
        verifyContainer();
        if (view == null || position > mMenuAdapter.getMenuCount() || position < 0) {
            throw new IllegalStateException("the view at " + position + " cannot be null");
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2);
        params.bottomMargin = bottomMargin;//
        frameLayoutContainer.addView(view, position, params);
        view.setVisibility(GONE);
    }


    public boolean isShowing() {
        verifyContainer();
        return frameLayoutContainer.isShown();
    }

    public boolean isClosed() {
        return !isShowing();
    }

    public void close() {
        if (isClosed()) {
            return;
        }

        frameLayoutContainer.startAnimation(alphaDismissAnimation);
        fixedTabIndicator.resetCurrentPos();

        if (currentView != null) {
            currentView.startAnimation(dismissAnimation);
        }
    }


    public void setPositionIndicatorText(int position, String text) {
        verifyContainer();
        fixedTabIndicator.setPositionText(position, text);
    }

    public void setCurrentIndicatorText(String text) {
        verifyContainer();
        fixedTabIndicator.setCurrentText(text);
    }

    //==============================================================
    private void initListener() {
        frameLayoutContainer.setOnClickListener(this);
        fixedTabIndicator.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isShowing()) {
            close();
        }
    }

    @Override
    public void onItemClick(View v, int position, boolean open) {
        if (open) {
            close();
        } else {


            currentView = frameLayoutContainer.getChildAt(position);

            if (currentView == null) {
                return;
            }


            frameLayoutContainer.getChildAt(fixedTabIndicator.getLastIndicatorPosition()).setVisibility(View.GONE);
            frameLayoutContainer.getChildAt(position).setVisibility(View.VISIBLE);


            if (isClosed()) {
                frameLayoutContainer.setVisibility(VISIBLE);
                frameLayoutContainer.startAnimation(alphaOccurAnimation);

                //,
                currentView.startAnimation(occurAnimation);
            }


        }
    }


    private void initAnimation() {
        occurAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.top_in);

        SimpleAnimationListener listener = new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                frameLayoutContainer.setVisibility(GONE);
            }
        };

        dismissAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.top_out);
        dismissAnimation.setAnimationListener(listener);


        alphaDismissAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_to_zero);
        alphaDismissAnimation.setDuration(300);
        alphaDismissAnimation.setAnimationListener(listener);

        alphaOccurAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_to_one);
        alphaOccurAnimation.setDuration(300);
    }

    private void verifyMenuAdapter() {
        if (mMenuAdapter == null) {
            throw new IllegalStateException("the menuAdapter is null");
        }
    }

    private void verifyContainer() {
        if (frameLayoutContainer == null) {
            throw new IllegalStateException("you must initiation setContentView() before");
        }
    }


}
