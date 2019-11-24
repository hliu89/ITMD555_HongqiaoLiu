package com.infinet.leviathan.myapplication.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.infinet.leviathan.myapplication.R;
import com.infinet.leviathan.myapplication.ui.base.BaseActivity;
import com.infinet.leviathan.myapplication.ui.custom.MToolbar;
import com.infinet.leviathan.myapplication.utils.ViewUtils;


/**
 * Created by xk on 2016/6/26 16:12.
 */
public class IntroduceActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_INTRODUCE_SAVE = 0;
    private static final int RESULT_INTRODUCE_BACK = 1;
    private MToolbar toolbar;
    private EditText et_introduce;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_introduce);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        et_introduce = ViewUtils.findViewById(this, R.id.et_introduce);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle(" special remarks");
        toolbar.setRightTextView("save");
    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                setResult(RESULT_INTRODUCE_BACK);
                onBackPressed();
            }

            @Override
            public void onRightClick() {
            }
        });
        toolbar.setOnTextViewClickListener(new MToolbar.OnTextViewClickListener() {
            @Override
            public void onLeftClick() {
            }

            @Override
            public void onRightClick() {
                String s = et_introduce.getText().toString();
                getIntent().putExtra("introduce",s);
                setResult(RESULT_INTRODUCE_SAVE,getIntent());
                onBackPressed();
            }
        });
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onClick(View v) {
    }
}


