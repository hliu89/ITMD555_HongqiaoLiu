package com.infinet.leviathan.myapplication.ui.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.infinet.leviathan.myapplication.R;
import com.infinet.leviathan.myapplication.ui.activity.FindFriendActivity;
import com.infinet.leviathan.myapplication.ui.base.BaseFragment;
import com.infinet.leviathan.myapplication.utils.ViewUtils;


/**
 * Created by xk on 2016/6/2 20:05.
 */
public class MessageFragment extends BaseFragment {
    private RelativeLayout rl_find_friend;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_message;

    }

    @Override
    protected void findViews(View v) {
        rl_find_friend = ViewUtils.findViewById(v, R.id.rl_find_friend);
    }

    @Override
    protected void setupViews(View v) {


//        LCChatKit.getInstance().open(AVUser.getCurrentUser().getObjectId(), new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (null == e) {
//                    Intent intent = new Intent(getContext(), LCIMConversationActivity.class);
//                    intent.putExtra(LCIMConstants.PEER_ID, "575a8ca56be3ff0069475d91");
//                    startActivity(intent);
//                } else {
//                    toast(e.toString());
//                }
//            }
//        });
    }

    @Override
    protected void setListener(View v) {
        rl_find_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(FindFriendActivity.class);
            }
        });
    }

    @Override
    protected void fetchData(View v) {
//        LCIMConversationItemCache.getInstance().initDB(AVOSCloud.applicationContext, AVUser.getCurrentUser().getObjectId(), new AVCallback() {
//            @Override
//            protected void internalDone0(Object o, AVException e) {
//
//            }
//        });


    }
}
