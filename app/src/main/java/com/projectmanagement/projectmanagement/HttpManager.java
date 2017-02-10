package com.projectmanagement.projectmanagement;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by jespe on 28-11-2016.
 */

public class HttpManager{
    private Activity requestActivity;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    public HttpManager(Activity activity)
    {
        this.requestActivity = activity;
        this.requestQueue = Volley.newRequestQueue(requestActivity);
        requestQueue.start();
    }

    public void pulldata(final DeserializeCallback deserializeCallback, String[] parameters, String[] values)
    {
        if (parameters.length != values.length)
        {
            return;
        }

        String url = this.requestActivity.getResources().getString(R.string.http_url);

        for (int i = 0; i < parameters.length; i++)
        {
            try
            {
                url += parameters[i] + "=" + URLEncoder.encode(values[i], "UTF-8");
            }
            catch (Exception e)
            {

            }
            if (i != parameters.length - 1)
            {
                url += "&";
            }
        }

        Log.e("OKK", url);

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("OKK", "Pulldata Response:");
                Log.e("OKK", response);
                deserializeCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("OKK", "Pulldata Error:");
                if (error.getMessage() != null) {
                    Log.e("OKK", error.getMessage());
                }
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}
