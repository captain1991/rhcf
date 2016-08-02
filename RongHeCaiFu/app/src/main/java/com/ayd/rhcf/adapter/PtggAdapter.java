package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.PtggBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqy on 2016/3/14.
 */
public class PtggAdapter extends BaseAdapter {
    private Context context;
    private List<PtggBean> list;

    public PtggAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ptgg_item, null);
        }
        PtggBean bean = list.get(i);
        TextView mTv_ptgg = (TextView) view.findViewById(R.id.tv_ptgg);
        mTv_ptgg.setText(bean.getName());
        return view;
    }

    public void setList(List<PtggBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void appendList(List<PtggBean> ptggBeans) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(ptggBeans);
        notifyDataSetChanged();

    }

    public void clear() {
        if (list != null) {
            list.clear();
        }
        notifyDataSetChanged();
    }
}
