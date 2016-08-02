package com.ayd.rhcf.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by gqy on 2016/3/5.
 * TextWatcher;
 */
public abstract class OwnTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        textChange(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public abstract void textChange(CharSequence charSequence);

}
