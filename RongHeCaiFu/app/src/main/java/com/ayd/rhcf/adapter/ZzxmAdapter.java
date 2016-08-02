package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqy on 2016/2/29.
 */
public class ZzxmAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private List<String> dataList;
    private MyEventCallBack adapterEventCallBack;

    public ZzxmAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setAadapterEventCallBack(MyEventCallBack callBack) {
        this.adapterEventCallBack = callBack;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size()+1;
//        return 4;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_with_4xm, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_xm1 = (TextView) convertView.findViewById(R.id.tv_xm1);
            viewHolder.tv_xm2 = (TextView) convertView.findViewById(R.id.tv_xm2);
            viewHolder.tv_xm3 = (TextView) convertView.findViewById(R.id.tv_xm3);
            viewHolder.tv_xm4 = (TextView) convertView.findViewById(R.id.tv_xm4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.tv_xm1.setText(context.getString(R.string.text_tzsj));
            viewHolder.tv_xm2.setText(context.getString(R.string.text_xm));
            viewHolder.tv_xm3.setText(context.getString(R.string.text_tzje));
            viewHolder.tv_xm4.setText(context.getString(R.string.text_cz_));
        } else {
            viewHolder.tv_xm4.setTextColor(context.getResources().getColor(R.color.cz_text_color));
            viewHolder.tv_xm4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapterEventCallBack != null) {
                        adapterEventCallBack.adapterEventCallBack(position - 1);
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tv_xm1;
        public TextView tv_xm2;
        public TextView tv_xm3;
        public TextView tv_xm4;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void appendDataList(List<String> dataListTemp) {
        if (dataListTemp == null || dataListTemp.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<String>();
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
