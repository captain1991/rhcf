package com.ayd.rhcf.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yxd on 2016/7/15.
 */
public class JsonObjectCookieRequest extends JsonObjectRequest {
    public JsonObjectCookieRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);

    }

    public JsonObjectCookieRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Map<String,String> headers = response.headers;
        String cookie = headers.get("Set-Cookie");
        try {
            String dataString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(dataString),HttpHeaderParser.parseCacheHeaders(response));
        }  catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String,String> headHashmap = new HashMap<>();
        headHashmap.put("Cookie","");
        return headHashmap;
    }
}
