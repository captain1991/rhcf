package com.ayd.rhcf.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.ayd.rhcf.AppConstants;

/**
 * Created by gqy on 2016/3/8.
 * 等待进度条；
 * <p/>
 * DialogFragment 持有 the  Dialog.setOnCancelListener and  Dialog.setOnDismissListener callbacks.
 */
public class WaitingDialog extends DialogFragment {

    private String title = "提示";
    private String msg = "正在获取银行卡信息...";
    private static WaitingDialog waitingDialog;

    public static WaitingDialog getInstance() {
        if (waitingDialog == null) {
            synchronized (WaitingDialog.class) {
                waitingDialog = new WaitingDialog();
            }
        }
        return waitingDialog;
    }

    public WaitingDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public WaitingDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog pDialog = ProgressDialog.show(getActivity(), null, msg, true);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(true);

        // DialogFragment持有Dialog的回调；此方的回调不会执行，
//        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                LogUtil.i("===================onCancel");
//            }
//        });
        return pDialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Fragment fragment = getTargetFragment();
//        if (fragment instanceof TxFragment) {
//            if (getTargetRequestCode() == AppConstants.TX_GETYHK_REQ_CODE) {
//                ((TxFragment) fragment).dialogCanceled(AppConstants.DIALOG_TX_GETYHK_RESULT_CODE);
//            } else if (getTargetRequestCode() == AppConstants.TX_LKTX_REQ_CODE) {
//                ((TxFragment) fragment).dialogCanceled(AppConstants.DIALOG_TX_LKTX_RESULT_CODE);
//            }
//        }
    }
}
