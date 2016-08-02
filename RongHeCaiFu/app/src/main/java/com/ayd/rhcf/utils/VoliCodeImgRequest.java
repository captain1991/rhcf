//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ayd.rhcf.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Priority;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
/**
 * post请求获得图片
 * 用于获取验证码
 * created by yxd on 2016/7/21
 * **/
public class VoliCodeImgRequest extends Request<Bitmap> {

    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", new Object[]{"utf-8"});
    private String mRequestBody;
    private static final int IMAGE_TIMEOUT_MS = 1000;
    private static final int IMAGE_MAX_RETRIES = 2;
    private static final float IMAGE_BACKOFF_MULT = 2.0F;
    private final Listener<Bitmap> mListener;
    private final Config mDecodeConfig;
    private final int mMaxWidth;
    private final int mMaxHeight;
    private static final Object sDecodeLock = new Object();

    public VoliCodeImgRequest(String url, Listener<Bitmap> listener, int maxWidth, int maxHeight, Config decodeConfig, ErrorListener errorListener) {
        super(0, url, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(1000, 2, 2.0F));
        this.mListener = listener;
        this.mDecodeConfig = decodeConfig;
        this.mMaxWidth = maxWidth;
        this.mMaxHeight = maxHeight;
    }

    public VoliCodeImgRequest( String url,String requestBody, Config mDecodeConfig, int mMaxWidth, int mMaxHeight, ErrorListener listener, Listener<Bitmap> mListener) {
        super(1, url, listener);
//        1000, 2, 2.0F
        this.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0F));
        this.mListener = mListener;
        this.mDecodeConfig = mDecodeConfig;
        this.mMaxWidth = mMaxWidth;
        this.mMaxHeight = mMaxHeight;
        mRequestBody = requestBody;
    }

//    protected void deliverResponse(Bitmap response) {
//        this.mListener.onResponse(response);
//    }


    /** @deprecated */
    public String getPostBodyContentType() {
        return this.getBodyContentType();
    }

    /** @deprecated */
    public byte[] getPostBody() {
        return this.getBody();
    }

    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    public byte[] getBody() {
        try {
            return this.mRequestBody == null?null:this.mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException var2) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", new Object[]{this.mRequestBody, "utf-8"});
            return null;
        }
    }

    public Priority getPriority() {
        return Priority.LOW;
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
        if(maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        } else {
            double ratio;
            if(maxPrimary == 0) {
                ratio = (double)maxSecondary / (double)actualSecondary;
                return (int)((double)actualPrimary * ratio);
            } else if(maxSecondary == 0) {
                return maxPrimary;
            } else {
                ratio = (double)actualSecondary / (double)actualPrimary;
                int resized = maxPrimary;
                if((double)maxPrimary * ratio > (double)maxSecondary) {
                    resized = (int)((double)maxSecondary / ratio);
                }

                return resized;
            }
        }
    }

    protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
        Object var2 = sDecodeLock;
        synchronized(sDecodeLock) {
            Response var10000;
            try {
                var10000 = this.doParse(response);
            } catch (OutOfMemoryError var4) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", new Object[]{Integer.valueOf(response.data.length), this.getUrl()});
                return Response.error(new ParseError(var4));
            }

            return var10000;
        }
    }

    private Response<? extends Object> doParse(NetworkResponse response) {
        byte[] data = response.data;
        Options decodeOptions = new Options();
        Bitmap bitmap = null;
        if(this.mMaxWidth == 0 && this.mMaxHeight == 0) {
            decodeOptions.inPreferredConfig = this.mDecodeConfig;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        } else {
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;
            int desiredWidth = getResizedDimension(this.mMaxWidth, this.mMaxHeight, actualWidth, actualHeight);
            int desiredHeight = getResizedDimension(this.mMaxHeight, this.mMaxWidth, actualHeight, actualWidth);
            decodeOptions.inJustDecodeBounds = false;
            decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            if(tempBitmap == null || tempBitmap.getWidth() <= desiredWidth && tempBitmap.getHeight() <= desiredHeight) {
                bitmap = tempBitmap;
            } else {
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            }
        }

        return bitmap == null?Response.error(new ParseError(response)):Response.success(bitmap, HttpHeaderParser.parseCacheHeaders(response));
    }

    protected void deliverResponse(Bitmap response) {
        this.mListener.onResponse(response);
    }

    static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double)actualWidth / (double)desiredWidth;
        double hr = (double)actualHeight / (double)desiredHeight;
        double ratio = Math.min(wr, hr);

        float n;
        for(n = 1.0F; (double)(n * 2.0F) <= ratio; n *= 2.0F) {
            ;
        }

        return (int)n;
    }
}
