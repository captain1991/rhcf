package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqy on 2016/2/29.
 */
public class XzyhkAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private List<String> dataList;

    public XzyhkAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        return dataList == null ? 0 : dataList.size();
        return 3;
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
            viewHolder.tv_xm1.setText(context.getString(R.string.text_kh));
            viewHolder.tv_xm2.setText(context.getString(R.string.text_yh));
            viewHolder.tv_xm3.setText(context.getString(R.string.text_cz_));
        } else {
            viewHolder.tv_xm1.setText("0000000");
            viewHolder.tv_xm2.setText("工商");
            viewHolder.tv_xm3.setText("删除");
        }

        return convertView;
    }

    class ViewHolder {
        public TextView tv_xm1;
        public TextView tv_xm2;
        public TextView tv_xm3;
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
    }

    public void clear() {
        if (dataList != null) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }
}
