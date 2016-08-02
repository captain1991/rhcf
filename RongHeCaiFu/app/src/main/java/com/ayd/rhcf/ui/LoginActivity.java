package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.OwnTextWatcher;
import com.ayd.rhcf.utils.ToastUtil;

/**
 * 登录页；
 * created by gqy on 2016/2/22
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_uname;
    private EditText et_pwd;
    private Button btn_login;
    private ImageView mIvClear;
    private ImageView mIvEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_login);
    }

    @Override
    protected String getTitleBarRightText() {
        return getString(R.string.text_regist);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected void titleBarRightClick() {
        readyGo(this, RegistrationActivity.class);
    }

    private void initViews() {
        et_uname = (EditText) findViewById(R.id.et_uname);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvClear.setVisibility(View.INVISIBLE);
        mIvClear.setOnClickListener(this);

        mIvEye = (ImageView) findViewById(R.id.iv_eye);
        mIvEye.setTag(R.drawable.eye1);
        mIvEye.setOnClickListener(this);

        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);

        et_uname.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence s) {
                if (s != null && CommonUtil.isNotEmpty(s.toString())) {
                    mIvClear.setVisibility(View.VISIBLE);
                } else {
                    mIvClear.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_login) {
            login();
        } else if (viewId == R.id.tv_forget_pwd) {
            readyGo(this, FindBackPwdActivity.class);

        } else if (viewId == R.id.iv_clear) {
            et_uname.setText("");
        } else if (viewId == R.id.iv_eye) {
            String pwd = et_pwd.getText().toString().trim();

            if (CommonUtil.isNotEmpty(pwd)) {
                updateEyeState();
            }
        }
    }

    /**
     * 登录；
     */
    private void login() {
        String uname = et_uname.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();

        LogUtil.i("uname==>" + uname + ",pwd=>" + pwd);

        if (CommonUtil.isEmpty(uname)) {
            ToastUtil.showToastShort(this, R.string.uname_not_null);
            return;
        }
        if (CommonUtil.isEmpty(pwd)) {
            ToastUtil.showToastShort(this, R.string.pwd_not_null);
            return;
        }

        // 执行登录；

    }

    /**
     * 更新眼睛的状态和密码的显示和隐藏；
     */
    private void updateEyeState() {
        int id = (Integer) mIvEye.getTag();
        if (id == R.drawable.eye1) {
            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mIvEye.setBackgroundResource(R.drawable.eye2);
            mIvEye.setTag(R.drawable.eye2);
        } else {
            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mIvEye.setBackgroundResource(R.drawable.eye1);
            mIvEye.setTag(R.drawable.eye1);
        }
//        et_pwd.postInvalidate();
//        切换后让光标置于末尾
        CharSequence charSequence = et_pwd.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Login_REQ_TAG};
    }

}
