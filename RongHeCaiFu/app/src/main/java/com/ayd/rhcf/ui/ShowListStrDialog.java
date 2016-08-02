package com.ayd.rhcf.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ayd.rhcf.R;

/**
 * Created by gqy on 2016/3/8.
 * 自定义的String列表展示dialog；
 */
public class ShowListStrDialog extends DialogFragment implements AdapterView.OnItemClickListener {
    private String[] items;
    private DialogItemClick itemClick;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (itemClick != null) {
            this.dismiss();
            itemClick.itemClick(i);
        }
    }

    public interface DialogItemClick {
        public void itemClick(int position);
    }

    public ShowListStrDialog setItems(String[] items, final DialogItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_yhk_style);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.listview, null);
        ListView listView = (ListView) dialogView.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items == null ? 0 : items.length;
            }

            @Override
            public String getItem(int i) {
                return items == null ? "" : items[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_item_yhk, viewGroup, false);
                TextView tv_yhkh = (TextView) view.findViewById(R.id.tv_yhkh);

                if (position == 0) {
                    view.setBackgroundResource(R.drawable.listview_yhk_header);
                } else if (position == items.length - 1) {
                    view.setBackgroundResource(R.drawable.listview_yhk_footer);
                } else {
                    view.setBackgroundResource(R.drawable.gengduo_item_selector);
                }

                tv_yhkh.setText(items[position]);
                return view;
            }
        });
        dialog.setContentView(dialogView);
        return dialog;
    }

}
