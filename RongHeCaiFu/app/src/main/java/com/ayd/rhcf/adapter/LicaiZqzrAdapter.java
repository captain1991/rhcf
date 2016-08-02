package com.ayd.rhcf.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.TestBean;
import com.ayd.rhcf.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 债券转让；
 * Created by gqy on 2016/2/26.
 */
public class LicaiZqzrAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TestBean> dataList;
    private MyEventCallBack eventCallBack;


    public void setEventCallBack(MyEventCallBack eventCallBack) {
        this.eventCallBack = eventCallBack;
    }

    public LicaiZqzrAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public TestBean getItem(int position) {
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
            convertView = inflater.inflate(R.layout.fragment_licai_zqzr_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTvXmName = (TextView) convertView.findViewById(R.id.tv_xm_name);
            viewHolder.mTvZrjg = (TextView) convertView.findViewById(R.id.tv_zrjg);
            viewHolder.mTvYqnh = (TextView) convertView.findViewById(R.id.tv_yqnh);
            viewHolder.mTvSyts = (TextView) convertView.findViewById(R.id.tv_syts);
            viewHolder.mBtnGmzq = (Button) convertView.findViewById(R.id.btn_gmzq);
            viewHolder.mTvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TestBean bean = getItem(position);
        viewHolder.mTvZrjg.setText(Html.fromHtml("转让价格：<font color=\"#c3130f\">￥00.00" + "</font>"));

        CommonUtil.updateTimeLabel(viewHolder.mTvTime, bean.timeInMis);

        //数据初始化；
        viewHolder.mBtnGmzq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventCallBack != null) {
                    eventCallBack.adapterEventCallBack(position);
                }
            }
        });

        return convertView;
    }

    public void scheduleTime() {
        if (dataList != null && dataList.size() > 0) {
            for (TestBean bean : dataList) {
                bean.timeInMis = bean.timeInMis - 1;
            }

            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        public TextView mTvXmName;
        public TextView mTvZrjg;
        public TextView mTvYqnh;
        public TextView mTvSyts;
        public Button mBtnGmzq;
        public TextView mTvTime;
    }

    public List<TestBean> getDataList() {
        return dataList;
    }

    public void appendDataList(List<TestBean> dataListTemp) {
        if (dataListTemp == null || dataListTemp.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<TestBean>();
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
