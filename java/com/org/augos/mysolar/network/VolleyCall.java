package com.org.augos.mysolar.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyCall {

    private static final int MY_SOCKET_TIMEOUT_MS = 40000; // 60 seconds for Retry policy

    public static void getJSONResponse(final Context context, String url, int method, JSONObject payload, final VolleyJSONCallback volleyCallback) {

        JsonObjectRequest request = new JsonObjectRequest(method, url, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    volleyCallback.onSuccessResponse(response);
                } else {
                    volleyCallback.onError(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    volleyCallback.onVolleyError("Timeout Error");
                } else if (error instanceof AuthFailureError) {
                    volleyCallback.onVolleyError("AuthFailure Error");
                } else if (error instanceof ServerError) {
                    volleyCallback.onVolleyError("Server Error");
                } else if (error instanceof NetworkError) {
                    volleyCallback.onVolleyError("Network Error");
                } else if (error instanceof ParseError) {
                    volleyCallback.onVolleyError("Parse Error");
                } else{
                    volleyCallback.onVolleyError("Unexpected error");
                }
            }

        });
        request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }

    public static void getResponse(final Context context, String url, int method, final HashMap<String,
            String> payload, final VolleyCallback volleyCallback) {

        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    volleyCallback.onSuccessResponse(response);
                } else {
                    volleyCallback.onVolleyError(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    volleyCallback.onVolleyError("Timeout Error");
                } else if (error instanceof AuthFailureError) {
                    volleyCallback.onVolleyError("AuthFailure Error");
                } else if (error instanceof ServerError) {
                    volleyCallback.onVolleyError("Server Error");
                } else if (error instanceof NetworkError) {
                    volleyCallback.onVolleyError("Network Error");
                } else if (error instanceof ParseError) {
                    volleyCallback.onVolleyError("Parse Error");
                }else{
                    volleyCallback.onVolleyError("Unexpected error");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return payload;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Accept", "application/json");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }
}
