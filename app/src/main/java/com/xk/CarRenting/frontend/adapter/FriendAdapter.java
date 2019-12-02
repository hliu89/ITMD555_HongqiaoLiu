package com.xk.CarRenting.frontend.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.bean.User;
import com.xk.CarRenting.nohttpHelper.CallServer;
import com.xk.CarRenting.nohttpHelper.HttpListener;
import com.xk.CarRenting.frontend.tools.RoundView;
import com.xk.CarRenting.utils.AvatarProduceUtil;
import com.xk.CarRenting.utils.SharedPreferencesUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *  Adapter
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MViewHolder> {
    protected List<User> dataList = new ArrayList<User>();
    private Context context;
    private AvatarProduceUtil avatarProduceUtil;

    public FriendAdapter(Context context) {
        this.context = context;
        avatarProduceUtil = new AvatarProduceUtil(context);
    }

    /**
     *  Adapter
     *
     * @return
     */
    public List<User> getDataList() {
        return dataList;
    }

    /**
     * ，
     *
     * @param datas
     */
    public void setDataList(List<User> datas) {
        dataList.clear();
        if (null != datas) {
            dataList.addAll(datas);
        }
    }

    /**
     * ，，
     *
     * @param datas
     */
    public void addDataList(List<User> datas) {
        dataList.addAll(datas);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_friend, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        final User friendBean = dataList.get(position);
        holder.tv_nickname.setText(friendBean.getUsername());
        if (friendBean.getAvatarurl().equals("")) {
            holder.civ_avatar.setImageBitmap(avatarProduceUtil.getBitmapByText(friendBean.getUsername()));
        } else {
            holder.civ_avatar.setImageURI(Uri.parse(friendBean.getAvatarurl()));
        }
        holder.ll_cared.setVisibility(friendBean.getIsChecked() ? View.VISIBLE : View.GONE);
        holder.ll_cared_no.setVisibility(!friendBean.getIsChecked() ? View.VISIBLE : View.GONE);

        holder.ll_btn_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ll_cared.getVisibility()== View.VISIBLE) {
                    //
                    requestCancelCaredFriend(holder,SharedPreferencesUtil.getString(context, Constant.SPKEY_CURRENTUSERPHONENUMBER),friendBean.getPhone());
                }else{
                    //
                     requestCaredFriend(holder,SharedPreferencesUtil.getString(context, Constant.SPKEY_CURRENTUSERPHONENUMBER),friendBean.getPhone());

                }
            }
        });
    }

    private void requestCaredFriend(final MViewHolder holder, String mPhone, String caredPhone){
        Request<JSONObject> caredFriend = NoHttp.createJsonObjectRequest(Constant.url_my_friend, RequestMethod.POST);
        caredFriend.add("action","caredSomeBody");
        caredFriend.add("myPhone",mPhone);
        caredFriend.add("otherPhone",caredPhone);
        CallServer.getRequestInstance().add(context, 0, caredFriend, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    boolean result = response.get().getBoolean("result");
                        holder.ll_cared.setVisibility(result ? View.VISIBLE : View.GONE);
                        holder.ll_cared_no.setVisibility(!result ? View.VISIBLE : View.GONE);
                    if (!result) {
                        Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
            }
        },true,false,"");
    }

    private void requestCancelCaredFriend(final MViewHolder holder, String mPhone, String caredPhone){
        Request<JSONObject> caredFriend = NoHttp.createJsonObjectRequest(Constant.url_my_friend, RequestMethod.POST);
        caredFriend.add("action","cancelcaredSomeBody");
        caredFriend.add("myPhone",mPhone);
        caredFriend.add("otherPhone",caredPhone);
        CallServer.getRequestInstance().add(context, 0, caredFriend, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    boolean result = response.get().getBoolean("result");
                    holder.ll_cared_no.setVisibility(result ? View.VISIBLE : View.GONE);
                    holder.ll_cared.setVisibility(!result ? View.VISIBLE : View.GONE);
                    if (!result) {
                        Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
            }
        },true,false,"");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nickname;
        public RoundView civ_avatar;
        public LinearLayout ll_cared_no, ll_cared,ll_btn_care;
        public View root;


        public MViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            tv_nickname = (TextView) root.findViewById(R.id.tv_nickname);
            civ_avatar = (RoundView) root.findViewById(R.id.civ_avatar);
            ll_cared_no = (LinearLayout) root.findViewById(R.id.ll_cared_no);
            ll_cared = (LinearLayout) root.findViewById(R.id.ll_cared);
            ll_btn_care = (LinearLayout) root.findViewById(R.id.ll_btn_care);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
