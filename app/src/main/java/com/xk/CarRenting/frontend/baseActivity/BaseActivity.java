package com.xk.CarRenting.frontend.baseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xk.CarRenting.app.App;



public abstract class BaseActivity extends AppCompatActivity {

    protected App mApp;
    protected BaseActivity mContext;
    protected Intent mIntent;
    protected Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init data-members
        mApp = App.getInstance();
        mContext = this;
        mIntent = getIntent();
        mBundle = mIntent.getExtras();

        setLayout();
        findViews();
        setupViews(mBundle);
        setListener();
        fetchData();
    }

    protected abstract void setLayout();

    protected abstract void findViews();

    protected abstract void setupViews(Bundle bundle);

    protected abstract void setListener();

    protected abstract void fetchData();

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
        Intent intent = new Intent(this, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     *  Activity
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
        Intent intent = new Intent(this, targetActivity);
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
        Intent intent = new Intent(this, targetActivity);
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
     *  Toast,  Debug
     *
     * @param text Toast
     */
    public void toast(@NonNull final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
