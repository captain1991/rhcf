package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayd.rhcf.R;

/**
 * Created by gqy on 2016/2/24.
 * 首页4个item适配器；
 */
public class ShouyeGvAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] textArray = null;
    private int[] imgResIds = {R.drawable.guanyuwomen, R.drawable.pingtaigonggao,
            R.drawable.yaoqinghaoyou, R.drawable.shouyijisuan};

    public ShouyeGvAdapter(Context context) {
        textArray = context.getResources().getStringArray(R.array.main_gv_items_4);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgResIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_shouye_gv_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(imgResIds[position]);
        viewHolder.textView.setText(textArray[position]);
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
