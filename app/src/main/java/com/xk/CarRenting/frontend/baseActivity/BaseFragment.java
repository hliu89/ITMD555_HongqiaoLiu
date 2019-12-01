package com.xk.CarRenting.frontend.baseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    @LayoutRes
    protected int layoutRes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();

        setLayoutRes();

        View v = LayoutInflater.from(getContext()).inflate(layoutRes, container, false);

        findViews(v);
        setupViews(v);
        setListener(v);
        fetchData(v);

        return v;
    }

    /**
     * ,  layoutRes
     */
    protected abstract void setLayoutRes();

    protected abstract void findViews(View v);

    protected abstract void setupViews(View v);

    protected abstract void setListener(View v);

    protected abstract void fetchData(View v);

    /**
     *  Activity
     *
     * @param targetActivity  Activity
     */
    public void toActivity(@NonNull Class<?> targetActivity) {
        toActivity(targetActivity, null);
    }

    /**
     *  Activity,  Bundle
     *
     * @param targetActivity  Activity
     * @param bundle          Bundle
     */
    public void toActivity(@NonNull Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(getContext(), targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     *  Activity
     * <p/>
     * :  Fragment  onActivityResult,
     *  Fragment  Activity
     *  onActivityResult  super.onActivityResult.
     *
     * @param targetActivity  Activity
     * @param requestCode
     */
    public void toActivityForResult(@NonNull Class<?> targetActivity, int requestCode) {
        toActivityForResult(targetActivity, requestCode, null);
    }

    /**
     *  Activity ,  Bundle
     *
     * @param targetActivity  Activity
     * @param requestCode
     * @param bundle          Bundle
     */
    public void toActivityForResult(@NonNull Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     *  Activity， Activity
     *
     * @param targetActivity  Activity
     */
    public void toActivityWithClearTop(@NonNull Class<?> targetActivity) {
        Intent intent = new Intent(getContext(), targetActivity);
        //  Activity,  Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     *  Toast,  Debug
     *
     * @param resId Toast  id
     */
    public void toast(@StringRes int resId) {
        toast(getString(resId));
    }

    /**
     *  Toast,  Debug,
     *  BaseActivity  toast
     *
     * @param text Toast
     */
    public void toast(@NonNull CharSequence text) {
        if (mActivity != null)
            mActivity.toast(text);
    }

}
