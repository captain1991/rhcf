package com.ayd.rhcf.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.OwnTextWatcher;

/**
 * Created by gqy on 2016/3/7.
 * 未开通汇付托管账户dialog；
 */
public class WktHftgDialog extends DialogFragment implements ClickEventCallBack {
    private Button mBtnCancel;
    private Button mBtnQkt;

    private EditText edTxxm;
    private EditText edTxsfz;
    private MyEventCallBack callBack;

    private String xm;
    private String sfz;

    public void setCallBack(MyEventCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wkt_hftg_dialog, null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = CommonUtil.getScreenWidth(getActivity()) - CommonUtil.dip2px(getActivity(), 50);
        window.setAttributes(wl);
        init(view);
        return dialog;
    }

    private void init(View view) {
        edTxxm = (EditText) view.findViewById(R.id.et_txxm);
        edTxsfz = (EditText) view.findViewById(R.id.et_txsfz);
        edTxxm.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence charSequence) {
                xm = charSequence.toString();
            }
        });
        edTxsfz.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence charSequence) {
                sfz = charSequence.toString();
            }
        });
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
        ClickListenerRegister.regist(mBtnCancel, this);

        mBtnQkt = (Button) view.findViewById(R.id.btn_kt);
        ClickListenerRegister.regist(mBtnQkt, this);
    }

    public String getXm() {

        return xm;
    }

    public String getSfz() {
        return sfz;
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mBtnCancel.getId()) {
            if (callBack != null) {
                callBack.adapterEventCallBack(0);
            }
        } else if (viewId == mBtnQkt.getId()) {
            if (callBack != null) {
                callBack.adapterEventCallBack(1);
            }
        }
    }
}
