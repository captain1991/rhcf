package com.ayd.rhcf.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.BorrowBean;
import com.ayd.rhcf.ui.LccpDetailActivity;
import com.ayd.rhcf.ui.LoginActivityNew;
import com.ayd.rhcf.ui.QrtzActivity;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.sina.weibo.sdk.call.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxd on 2016/7/14.
 */
public class LicaiCpAdapter extends BaseAdapter {
    Context context;
    private List<BorrowBean> borrowList = new ArrayList<>();

    public LicaiCpAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return borrowList == null?0:borrowList.size();
    }

    @Override
    public BorrowBean getItem(int position) {
        return borrowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_licai_chanpin_item,null);
            holder = new ViewHolder();
            holder.tv_rzjd = (TextView) convertView.findViewById(R.id.licai_item_right_progress);
            holder.tv_left_logo = (TextView) convertView.findViewById(R.id.licai_item_left);
            holder.tv_name = (TextView) convertView.findViewById(R.id.licai_item_top);
            holder.tv_nhsy = (TextView) convertView.findViewById(R.id.item_tv_nhsy);
            holder.tv_xmqx = (TextView) convertView.findViewById(R.id.item_tv_xmqx);
            holder.tv_rzje = (TextView) convertView.findViewById(R.id.item_tv_rzje);
            holder.tv_qtje = (TextView) convertView.findViewById(R.id.item_tv_qtje);
            holder.btn_ljtz = (Button) convertView.findViewById(R.id.item_btn_ljtz);
            holder.tv_state = (TextView) convertView.findViewById(R.id.item_tv_state);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BorrowBean borrowBean = getItem(position);
        String s = String.format(context.getResources().getString(R.string.licai_item_progress), borrowBean.getBorrow_account_scale());
        String type = borrowBean.getBorrow_type();
        if (type.length()==1){
            holder.tv_left_logo.setTextSize(14);
        }else if (type.length()==4){
            holder.tv_left_logo.setTextSize(8);
        }
        holder.tv_rzjd.setText(s);
        holder.tv_left_logo.setText(type);
        holder.tv_name.setText(borrowBean.getName());
        holder.tv_nhsy.setText(borrowBean.getBorrow_apr());
        holder.tv_xmqx.setText(borrowBean.getBorrow_period_name());
        holder.tv_rzje.setText(borrowBean.getAccount());
        holder.tv_qtje.setText(borrowBean.getTender_account_min()+"元起投");
        holder.btn_ljtz.setVisibility(View.GONE);
        holder.tv_state.setVisibility(View.VISIBLE);

        switch (borrowBean.getBorrow_status_nid()){
            //还款中
            case "repay":
                holder.tv_state.setText("还款中");
                break;
            //已还完
            case "repay_yes":
                holder.tv_state.setText("已还完");
                break;
//            已满标
            case "full":
                holder.tv_state.setText("已满标");
                break;
//            马上投标
            case "loan":
                holder.btn_ljtz.setVisibility(View.VISIBLE);
                holder.tv_state.setVisibility(View.GONE);
                break;
//            已过期
            case "late":
                holder.tv_state.setText("已过期");
                break;
//            流转中
            case "roam_now":
                holder.btn_ljtz.setVisibility(View.VISIBLE);
                holder.tv_state.setVisibility(View.GONE);
                break;
//            已回购
            case "roam_yes":
                holder.tv_state.setText("已回购");
                break;
//            回购中
            case "roam_no":
                holder.tv_state.setText("回购中");
                break;
            default:
                holder.btn_ljtz.setVisibility(View.VISIBLE);
                holder.tv_state.setVisibility(View.GONE);
                break;
        }
        ClickListenerRegister.regist(holder.btn_ljtz, new ClickEventCallBack() {
            @Override
            public void clickEventCallBack(int viewId) {

                if(!SpUtil.getSpBooleanValueByKey(context, AppConstants.ISLOGIN, false)){
                    Intent intent = new Intent(context,LoginActivityNew.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra(AppConstants.INTENT_BUNDLE, bundle);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, QrtzActivity.class);
                    Bundle bundle = new Bundle();
                    String borrow_nid = borrowBean.getBorrow_nid();
                    bundle.putString("borrow_nid", borrow_nid);
                    intent.putExtra(AppConstants.INTENT_BUNDLE, bundle);
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    public void appendBorrowList(List<BorrowBean> beans) {
        if(borrowList==null){
            borrowList = new ArrayList<>();
        }
        borrowList.addAll(beans);
        notifyDataSetChanged();
    }

    public void clear(){
        if (borrowList != null) {
            borrowList.clear();
            notifyDataSetChanged();
        }
    }

    class ViewHolder{
        TextView tv_rzjd;
        TextView tv_left_logo;
        TextView tv_name;
        TextView tv_nhsy;
        TextView tv_xmqx;
        TextView tv_rzje;
        TextView tv_qtje;
        Button btn_ljtz;
        TextView tv_state;
    }
}
