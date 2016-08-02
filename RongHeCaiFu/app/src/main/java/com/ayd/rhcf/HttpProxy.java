package com.ayd.rhcf;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.EncodeUtil;
import com.ayd.rhcf.utils.GsonRequest;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.VoliCodeImgRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gqy on 2016/3/4.
 * Http请求代理类；
 */
public class HttpProxy {
    private static final int RESPONSE_SUCCESS_CODE = 101; // success交易码；
    private static final int RESPONSE_ERROR_CODE = 102;  // fail交易码；
    private static RequestQueue requestQueue; //Volley请求队列；
    private static final int TIMEOUT_MS = 10000;//超时时间；
    private static final int MAX_NUM_RETRIES = 1;//最大的请求重试次数；

    /**
     * GET请求；
     *
     * @param context
     * @param url
     * @param reqTag           请求的tag；(取消请求使用)
     * @param params           GET请求的参数；
     * @param responseCallBack 结果回调接口；
     * @param resultCode       结果码；
     */
    public static void getDataByGet(final Context context, String url, String reqTag, Map<String, String> params, final HRCallBack responseCallBack, int resultCode) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);

        if (params != null && params.size() > 0) {
            urlBuilder.append("?");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());

                if (iterator.hasNext()) {
                    urlBuilder.append("&");
                }
            }
        }

        String lastUrl = urlBuilder.toString();
        LogUtil.i("Get请求的url==\n" + lastUrl);

        if (TextUtils.isEmpty(lastUrl) || !lastUrl.startsWith(AppConstants.HTTP_PREFIX)) {
            return;
        }

        //参数2为空，默认是GET请求；
        JsonObjectRequest jsonRequest = new JsonObjectRequest(lastUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                //响应成功；
                LogUtil.i("响应====\n" + jsonObject.toString());
                if (responseCallBack != null) {
                    responseCallBack.httpResponse(RESPONSE_SUCCESS_CODE,new Object[]{jsonObject});
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseCallBack != null) {
                    responseCallBack.httpResponse(RESPONSE_ERROR_CODE);
                }
                notifyResult(context, error,responseCallBack);
            }
        }){

            // 重写此方法获取cookie
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    String sever = responseHeaders.get("Sever");
                    if(rawCookies!=null&&!rawCookies.equals("")) {
                        LogUtil.i("cookie>>>>>>"+rawCookies);
                        LogUtil.i("sever>>>>>>"+sever);
                        SpUtil.save2SpString(context, "Cookie", rawCookies);
                    }
                    String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(je), HttpHeaderParser.parseCacheHeaders(response));
                }catch (UnsupportedEncodingException var3) {
                    return Response.error(new ParseError(var3));
                } catch (JSONException var4) {
                    return Response.error(new ParseError(var4));
                }

            }
//           重写提交cookie
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap localHashMap = new HashMap();
                String cookie = SpUtil.getSpStringValueByKey(context,"Cookie","");
                if (!cookie.equals("")&&cookie!=null) {
                    localHashMap.put("Cookie", cookie);
                }
                return localHashMap;

            }
        };
        jsonRequest.setTag(reqTag);

        //超时；
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_NUM_RETRIES, 1.0f));
        getAppRequestQueue(context).add(jsonRequest);
    }

    /**
     * GET请求直接返回一个对象（可能会有bug）；
     *
     * @param context
     * @param url
     * @param reqTag           请求的tag；(取消请求使用)
     * @param params           GET请求的参数；
     * @param responseCallBack 结果回调接口；
     * @param resultCode       结果码；
     */
    public static void getObjectByGet(final Context context, String url, String reqTag, Map<String, String> params, final HRCallBack responseCallBack, int resultCode, Class clazz) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);

        if (params != null && params.size() > 0) {
            urlBuilder.append("?");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());

                if (iterator.hasNext()) {
                    urlBuilder.append("&");
                }
            }
        }

        String lastUrl = urlBuilder.toString();
        LogUtil.i("Get请求的url==\n" + lastUrl);

        if (TextUtils.isEmpty(lastUrl) || !lastUrl.startsWith(AppConstants.HTTP_PREFIX)) {
            return;
        }

        GsonRequest gsonRequest = new GsonRequest(lastUrl, clazz, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                if (responseCallBack !=null){
                    responseCallBack.httpResponse(RESPONSE_SUCCESS_CODE,new Object[]{o});
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (responseCallBack !=null){
                    responseCallBack.httpResponse(RESPONSE_ERROR_CODE);
                }
                notifyResult(context, volleyError,responseCallBack);
            }
        });

        gsonRequest.setTag(reqTag);

        //超时；
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_NUM_RETRIES, 1.0f));
        getAppRequestQueue(context).add(gsonRequest);
    }

        /**
         * POST请求；
         *
         * @param context
         * @param url
         * @param reqTag           请求的tag；(取消请求使用)
         * @param params           POST请求的参数；
         * @param responseCallBack 结果回调接口；
         * @param resultCode       结果码；
         */
    public static void getDataByPost(final Context context, String url,String reqTag,
                                     Map<String, String> params, final HRCallBack responseCallBack, int resultCode) {

        StringBuilder argBuilder = new StringBuilder();
        argBuilder.append("{\"device\" :\"android1\",\n" +
                "\"version\":\"1.0\",\n" +
                "\"data\":\"");
        StringBuilder dataBuilder = new StringBuilder();
        JSONObject argJson = null;
        if (params != null && params.size() > 0) {
            dataBuilder.append("{");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

            //构造post提交的json格式参数；
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                dataBuilder.append("\""+entry.getKey()+"\":\"" + entry.getValue()+"\"");

                if (iterator.hasNext()) {
                    dataBuilder.append(",");
                } else {
                    dataBuilder.append("}");
                }
            }
        }
        String str = dataBuilder.toString();
        String destr = null;
        try {
            destr = EncodeUtil.get3DES(str).replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String deco = null;
//        try {
//            deco = EncodeUtil.decode3DES(destr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        LogUtil.d("data===\n"+str);
        argBuilder.append(destr);
        argBuilder.append("\"}");
        String argJsonStr = argBuilder.toString();

        if (TextUtils.isEmpty(url) || !url.startsWith(AppConstants.HTTP_PREFIX)) {
            return;
        }

        try {
            argJson = new JSONObject(argJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        LogUtil.i("Post请求参数的json==\n" + argJson.toString());
        //参数2不为空，是POST请求；
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(url, argJson, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                //响应成功；
                LogUtil.i("响应====\n" + jsonObject.toString());
                responseCallBack.httpResponse(RESPONSE_SUCCESS_CODE,jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notifyResult(context, error,responseCallBack);
            }
        }){

            // 重写此方法获取cookie
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    String sever = responseHeaders.get("Server");
                    if(rawCookies!=null&&!rawCookies.equals("")) {
                        LogUtil.i("cookie>>>>>>"+rawCookies);
                        LogUtil.i("sever>>>>>>"+sever);
//                        SpUtil.save2SpString(context, "Cookie", rawCookies);
                    }
                    String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(je), HttpHeaderParser.parseCacheHeaders(response));
                }catch (UnsupportedEncodingException var3) {
                    return Response.error(new ParseError(var3));
                } catch (JSONException var4) {
                    return Response.error(new ParseError(var4));
                }

            }
        };
        jsonRequest.setTag(reqTag);

        //设置超时；
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_NUM_RETRIES, 1.0f));
        getAppRequestQueue(context).add(jsonRequest);

    }

    /**
     * POST请求；
     *
     * @param context
     * @param url
     * @param reqTag           请求的tag；(取消请求使用)
     * @param params           POST请求的参数；
     * @param responseCallBack 结果回调接口；
     * @param resultCode       结果码；
     */
    public static void getImageByPost(final Context context, String url,String reqTag,
                                     Map<String, String> params, final HRCallBack responseCallBack, int resultCode) {

        StringBuilder argBuilder = new StringBuilder();
        argBuilder.append("{\"device\" :\"android\",\n" +
                "\"version\":\"1.0\",\n" +
                "\"data\":");
        JSONObject argJson = null;
        if (params != null && params.size() > 0) {
            argBuilder.append("{");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

            //构造post提交的json格式参数；
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                argBuilder.append("\""+entry.getKey()+"\":\"" + entry.getValue()+"\"");

                if (iterator.hasNext()) {
                    argBuilder.append(",");
                } else {
                    argBuilder.append("}");
                }
            }
        }
        argBuilder.append("}");
        String argJsonStr = argBuilder.toString();

        if (TextUtils.isEmpty(url) || !url.startsWith(AppConstants.HTTP_PREFIX)) {
            return;
        }

        try {
            argJson = new JSONObject(argJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        LogUtil.i("Post请求参数的json==\n" + argJson.toString());
//String url,String requestBody, Config mDecodeConfig, int mMaxWidth, int mMaxHeight, ErrorListener listener, Listener<Bitmap> mListener
        final VoliCodeImgRequest request = new VoliCodeImgRequest(url,argJsonStr, Bitmap.Config.RGB_565, 0, 0,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                notifyResult(context,volleyError,responseCallBack);
            }
        },new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap bitmap) {
                //响应成功；
                LogUtil.i("响应====\n" );
                responseCallBack.httpResponse(RESPONSE_SUCCESS_CODE,bitmap);
            }
        });
        request.setTag(reqTag);
        getAppRequestQueue(context).add(request);

    }

    /**
     * 取消特定的请求；
     *
     * @param context
     * @param requestTag
     */
    public static void cancelSpecificRequest(Context context, String requestTag) {
        if (CommonUtil.isNotEmpty(requestTag)) {
            getAppRequestQueue(context).cancelAll(requestTag);
        }
    }

    public static RequestQueue getAppRequestQueue(Context context) {
        if (requestQueue == null) {
            synchronized (HttpProxy.class) {
                requestQueue = Volley.newRequestQueue(context);
            }
        }
        return requestQueue;
    }

    private static void notifyResult(Context context, VolleyError error,HRCallBack responseCallBack) {
        responseCallBack.httpResponse(RESPONSE_ERROR_CODE,context.getString(R.string.network_error));
        ToastUtil.showToastShort(context, context.getString(R.string.network_error));
        if (error != null) {
            if (error instanceof TimeoutError) {
                LogUtil.i("VolleyError1====" + context.getString(R.string.time_out));
            } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                LogUtil.i("VolleyError2====" + context.getString(R.string.network_error));
            } else if (error instanceof ServerError || error instanceof AuthFailureError) {
                LogUtil.i("VolleyError3====" + context.getString(R.string.server_error));
            } else if (error instanceof ParseError) {
                LogUtil.i("VolleyError4====" + "json解析失败");
            } else {
                LogUtil.i("VolleyError5====" + context.getString(R.string.general_error));
            }
        } else {
            //为空时，提示失败；
            LogUtil.i("VolleyError====6" + context.getString(R.string.general_error));
        }
    }
}
