package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.HkjhBean;
import com.ayd.rhcf.bean.TzjlBean;
import com.ayd.rhcf.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqy on 2016/2/29.
 */
public class TzjlAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
//    private List<String> dataList;
    private List<HkjhBean> tzjlBeans;
    public TzjlAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tzjlBeans == null ? 0 : tzjlBeans.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            viewHolder.tv_xm1.setText(context.getString(R.string.text_tbr));
            viewHolder.tv_xm2.setText(context.getString(R.string.text_tbje));
            viewHolder.tv_xm3.setText(context.getString(R.string.text_tbsj));
        } else {
            HkjhBean tzjlBean = tzjlBeans.get(position-1);
            viewHolder.tv_xm1.setText(tzjlBean.getUsername());
            viewHolder.tv_xm2.setText("￥"+tzjlBean.getAccount());
            viewHolder.tv_xm3.setText(tzjlBean.getAddtime());
        }

        return convertView;
    }

    class ViewHolder {
        public TextView tv_xm1;
        public TextView tv_xm2;
        public TextView tv_xm3;
    }

    public List<HkjhBean> getDataList() {
        return tzjlBeans;
    }

    public void appendDataList(List<HkjhBean> dataListTemp) {
        if (dataListTemp == null || dataListTemp.size() == 0) {
            return;
        }
        if (tzjlBeans == null) {
            tzjlBeans = new ArrayList<HkjhBean>();
        }
        tzjlBeans.addAll(dataListTemp);
    }

    public void clear() {
        if (tzjlBeans != null) {
            tzjlBeans.clear();
            notifyDataSetChanged();
        }
    }
}
