package com.xk.CarRenting.frontend.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.xk.CarRenting.R;
import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.bean.CarSourceBean;
import com.xk.CarRenting.frontend.ui.CarDetailActivity;
import com.xk.CarRenting.frontend.baseActivity.BaseActivity;
import com.xk.CarRenting.frontend.tools.RoundView;
import com.xk.CarRenting.utils.AvatarProduceUtil;
import com.xk.CarRenting.utils.RelativeDateFormat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xk on 2016/6/4 21:58.
 */
public class TruckInfoAdapter extends RecyclerView.Adapter<TruckInfoAdapter.MViewHolder> {
    private AvatarProduceUtil avatarProducUtil;
    private Context context;
    private List<CarSourceBean> data = new ArrayList<CarSourceBean>();
    private RecyclerView recyclerView;

    public TruckInfoAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        avatarProducUtil = new AvatarProduceUtil(context);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_car_info, null);
        return new MViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {
        holder.tv_owner_name.setText(data.get(position).getUserBean().getUsername().length() == 0 ? "no username" : data.get(position).getUserBean().getUsername());
        holder.tv_truckcardnumber.setText(data.get(position).getCarBean().getTruckCardNumber());
        holder.tv_stop_point.setText(data.get(position).getStop_place());
        holder.tv_start_point.setText(data.get(position).getStart_place());
        holder.tv_truck_length.setText("length" + data.get(position).getCarBean().getLength() + "m");
        holder.tv_truck_weight.setText("weight" + data.get(position).getCarBean().getWeight() + "t");
        holder.tv_truck_type.setText(data.get(position).getCarBean().getVariety());
        holder.tv_introduce.setText(data.get(position).getIntrocd().length() == 0 ? "no more information" : data.get(position).getIntrocd());
        Date date = new Date(Long.parseLong(data.get(position).getPublish_date()));
        holder.tv_publish_time.setText(RelativeDateFormat.format(date, new SimpleDateFormat("yyyy/MM/dd")));

//        avator = (CircleImageView) root.findViewById(R.id.civ_avatar);
        String start_place_point = data.get(position).getStart_place_point();
        String[] split = start_place_point.split(",");
        double distance = DistanceUtil.getDistance(new LatLng(Constant.latitude, Constant.lontitude), new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
        float fDistance = (float) (distance / 1000);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String format = decimalFormat.format(fDistance);
        holder.tv_distance.setText(format + "km");
        if (data.get(position).getUserBean().getAvatarurl().length() == 0) {
            Bitmap bitmap = avatarProducUtil.getBitmapByText(data.get(position).getUserBean().getUsername().length() == 0 ? "no username" : data.get(position).getUserBean().getUsername());
            holder.avator.setImageBitmap(bitmap);
        } else {
            holder.avator.setImageURL(data.get(position).getUserBean().getAvatarurl(),(Activity)context);
        }


        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("trucksource",data.get(position));
                ((BaseActivity)context).toActivity(CarDetailActivity.class,bundle);
            }
        });
        holder.ic_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.get(position).getUserBean().getPhone()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: by xk 2016/7/28 16:46
                    return;
                }
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private RoundView avator;
        private ImageView ic_call;
        private TextView tv_owner_name, tv_stop_point, tv_start_point, tv_introduce, tv_truckcardnumber, tv_truck_type, tv_truck_length, tv_truck_weight, tv_distance, tv_publish_time;


        public MViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            avator = (RoundView) root.findViewById(R.id.civ_owner_phone);
            ic_call = (ImageView) root.findViewById(R.id.ic_call);
            tv_owner_name = (TextView) root.findViewById(R.id.tv_owner_name);
            tv_start_point = (TextView) root.findViewById(R.id.tv_start_point);
            tv_stop_point = (TextView) root.findViewById(R.id.tv_stop_point);
            tv_introduce = (TextView) root.findViewById(R.id.tv_introduce);
            tv_truckcardnumber = (TextView) root.findViewById(R.id.tv_truckcardnumber);
            tv_truck_type = (TextView) root.findViewById(R.id.tv_truck_type);
            tv_truck_length = (TextView) root.findViewById(R.id.tv_truck_length);
            tv_truck_weight = (TextView) root.findViewById(R.id.tv_truck_weight);
            tv_distance = (TextView) root.findViewById(R.id.tv_distance);
            tv_publish_time = (TextView) root.findViewById(R.id.tv_publish_time);
        }
    }

    /**
     *  Adapter
     *
     * @return
     */
    public List<CarSourceBean> getData() {
        return data;
    }

    /**
     *
     *
     * @param datas
     */
    public void setData(List<CarSourceBean> datas) {
        data.clear();
        if (null != datas) {
            data.addAll(datas);
        }
        notifyDataSetChanged();
        if (datas.size() == 0) {
            if (onDataEmptyListener != null) {
                onDataEmptyListener.onDataEmpty();
            }
        } else {
            if (onDataEmptyListener != null) {
                onDataEmptyListener.onDataNotEmpty();
            }
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnDataEmptyListener onDataEmptyListener;

    public interface OnDataEmptyListener {
        void onDataEmpty();

        void onDataNotEmpty();
    }

    public void setOnDataEmptyListener(OnDataEmptyListener onDataEmptyListener) {
        this.onDataEmptyListener = onDataEmptyListener;
    }


}

