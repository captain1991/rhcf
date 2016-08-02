package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.Friend;
import com.ayd.rhcf.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yxd on 2016/6/29.
 */
public class MyYqAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private List<Friend> dataList;

    public MyYqAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size()+1;
//        return 3;
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_with_3xm, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_xm1 = (TextView) convertView.findViewById(R.id.tv_xm1);
            viewHolder.tv_xm2 = (TextView) convertView.findViewById(R.id.tv_xm2);
            viewHolder.tv_xm3 = (TextView) convertView.findViewById(R.id.tv_xm3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.tv_xm1.setText(context.getString(R.string.text_cgyqhy));
            viewHolder.tv_xm2.setText(context.getString(R.string.text_zcsj));
            viewHolder.tv_xm3.setText(context.getString(R.string.text_sftz));
        } else {
            Friend friend = dataList.get(position-1);
            viewHolder.tv_xm1.setText("" + friend.getUsername());
            viewHolder.tv_xm2.setText(""+friend.getAddtime2());
            viewHolder.tv_xm3.setText(friend.getTender() == 0 ? "否":"是");
        }

        return convertView;
    }

    class ViewHolder {
        public TextView tv_xm1;
        public TextView tv_xm2;
        public TextView tv_xm3;
    }

    public List<Friend> getDataList() {
        return dataList;
    }

    public void appendDataList(List<Friend> dataListTemp) {
        if (dataListTemp == null || dataListTemp.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<Friend>();
        }
        dataList.addAll(dataListTemp);
        notifyDataSetChanged();
    }

    public void clear() {
        if (dataList != null) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }
}
