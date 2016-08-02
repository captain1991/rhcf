package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.JyjlBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqy on 2016/2/29.
 */
public class JyjlListAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private List<JyjlBean> dataList;

    public JyjlListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
//        return 25;
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
            convertView = inflater.inflate(R.layout.fragment_jyjl_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_sj = (TextView) convertView.findViewById(R.id.tv_sj);
            viewHolder.tv_xm = (TextView) convertView.findViewById(R.id.tv_xm);
            viewHolder.tv_je = (TextView) convertView.findViewById(R.id.tv_je);
            viewHolder.tv_ye = (TextView) convertView.findViewById(R.id.tv_ye);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JyjlBean jyjl = dataList.get(position);
        viewHolder.tv_sj.setText(jyjl.getAddtime());
        viewHolder.tv_xm.setText(jyjl.getType_name());
        viewHolder.tv_je.setText("￥" + jyjl.getMoney());
        viewHolder.tv_ye.setText("￥" + jyjl.getBalance());

        return convertView;
    }

    class ViewHolder {
        public TextView tv_sj;
        public TextView tv_xm;
        public TextView tv_je;
        public TextView tv_ye;
    }

    public List<JyjlBean> getDataList() {
        return dataList;
    }

    public void appendDataList(List<JyjlBean> dataListTemp) {
        if (dataListTemp == null || dataListTemp.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<JyjlBean>();
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
