package com.ayd.rhcf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayd.rhcf.R;

import java.util.List;

/**
 * Created by yxd on 2016/5/5.
 */
public class ShxxAdapter extends BaseAdapter {
    Context context;
    List<String> strings;
//    String[] strings;

    public ShxxAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return strings==null ? 0:strings.size();
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
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_shxx_item,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_zhengjian);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_fcz);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String text = strings.get(position);
        if(text.equals("sfz")){
            holder.textView.setText("身份证");
            holder.imageView.setImageResource(R.drawable.sfz);
        }else if (text.equals("hkb")){
            holder.textView.setText("户口本");
            holder.imageView.setImageResource(R.drawable.hkb);
        }else if (text.equals("jhz")){
            holder.textView.setText("结婚证");
            holder.imageView.setImageResource(R.drawable.jhz);
        }else if (text.equals("srzm")){
            holder.textView.setText("收入证明");
            holder.imageView.setImageResource(R.drawable.srzm);
        }else if (text.equals("zxbg")){
            holder.textView.setText("征信报告");
            holder.imageView.setImageResource(R.drawable.zxbg);
        }else if (text.equals("qsdc")){
            holder.textView.setText("亲属调查");
            holder.imageView.setImageResource(R.drawable.qsdc);
        }else if (text.equals("xsz")){
            holder.textView.setText("行驶证");
            holder.imageView.setImageResource(R.drawable.xsz);
        }else if (text.equals("cldj")){
            holder.textView.setText("车辆登记");
            holder.imageView.setImageResource(R.drawable.cldj);
        }else if (text.equals("cldjfp")){
            holder.textView.setText("车辆登记发票");
            holder.imageView.setImageResource(R.drawable.cldefp);
        }else if (text.equals("cljq")){
            holder.textView.setText("车辆交强险");
            holder.imageView.setImageResource(R.drawable.cljq);
        }else if (text.equals("clsy")){
            holder.textView.setText("车辆商业保险");
            holder.imageView.setImageResource(R.drawable.clsy);
        }else if (text.equals("clpg")){
            holder.textView.setText("车辆评估");
            holder.imageView.setImageResource(R.drawable.clpg);
        }else if (text.equals("yhls")){
            holder.textView.setText("银行流水");
            holder.imageView.setImageResource(R.drawable.yhls);
        }else if (text.equals("gtz")){
            holder.textView.setText("国土证");
            holder.imageView.setImageResource(R.drawable.gtz);
        }else if (text.equals("fcz")){
            holder.textView.setText("房产证");
            holder.imageView.setImageResource(R.drawable.fcz);
        }else if (text.equals("fwsd")){
            holder.textView.setText("房屋实地认证");
            holder.imageView.setImageResource(R.drawable.fwsd);
        }else if (text.equals("gzzm")){
            holder.textView.setText("工作证明");
            holder.imageView.setImageResource(R.drawable.gzzm);
        }

//        if(position==2){
//            holder.textView.setText("车辆商业保险");
//        }else if(position==3){
//            holder.textView.setText("车辆商业保险");
//        }
//        else if(position==4){
//            holder.textView.setText("车辆交强险");
//        }
//        else if(position==5){
//            holder.textView.setText("车辆商业保险");
//        }
//        holder.imageView.setImageResource();

        return convertView;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}
