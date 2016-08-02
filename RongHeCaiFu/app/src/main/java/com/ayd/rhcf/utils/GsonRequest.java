package com.ayd.rhcf.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by yxd on 2016/4/12.
 */
public class GsonRequest<T> extends Request<T> {

    private Response.Listener<T> mListener;
    private Gson mGson;
    private Class<T> mClazz;

    public GsonRequest(String url,Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errListener) {
        this(Method.GET,url,clazz,listener, errListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,Response.ErrorListener errListener) {
        super(method, url, errListener);
        mGson = new Gson();
        mClazz = clazz;
        mListener = listener;

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {

        try {
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(jsonString,mClazz),HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);
    }
}
