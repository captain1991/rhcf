package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.BankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqy on 2016/3/8.
 */
public class MyBdyhkAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private List<BankBean> dataList;
    private MyEventCallBack adapterEventCallBack;

    public MyBdyhkAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setAadapterEventCallBack(MyEventCallBack callBack) {
        this.adapterEventCallBack = callBack;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            viewHolder.tv_xm3.setTextColor(context.getResources().getColor(R.color.cz_text_color));
            viewHolder.tv_xm3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapterEventCallBack != null) {
                        adapterEventCallBack.adapterEventCallBack(position - 1);
                    }
                }
            });
            BankBean bank = dataList.get(position-1);
            viewHolder.tv_xm1.setText(bank.getAccount());
            viewHolder.tv_xm2.setText(""+bank.getBank());
            viewHolder.tv_xm3.setText("删除");
        }

        return convertView;
    }

    class ViewHolder {
        public TextView tv_xm1;
        public TextView tv_xm2;
        public TextView tv_xm3;
    }

    public List<BankBean> getDataList() {
        return dataList;
    }

    public void appendDataList(List<BankBean> dataListTemp) {
        if (dataListTemp == null || dataListTemp.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<BankBean>();
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
